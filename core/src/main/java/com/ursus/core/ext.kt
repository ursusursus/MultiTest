package com.ursus.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 08-Jun-18.
 */
fun <T> Observable<T>.throwingSubscribe(onNext: (T) -> Unit): Disposable {
    return subscribe(onNext) { t -> throw t }
}

fun <T> Observable<T>.throwingSubscribe(onNext: Consumer<T>): Disposable {
    return subscribe(onNext, Consumer { t -> throw t })
}

fun Completable.throwingSubscribe(onComplete: () -> Unit): Disposable {
    return subscribe(onComplete) { t -> throw t }
}

inline fun View.doOnPreDraw(crossinline block: () -> Unit) {
    val viewTreeObserver = this.viewTreeObserver
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.removeOnPreDrawListener(this)
                block()
            }
            return true
        }
    })
}

fun Context.broadcastObservable(action: String): Observable<Intent> {
    return Observable.create<Intent> { emitter ->
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (!emitter.isDisposed) emitter.onNext(intent)
            }
        }
        emitter.setCancellable {
            unregisterReceiver(receiver)
        }
        registerReceiver(receiver, IntentFilter(action))
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, R> Observable<T>.forceableScan(forceSignal: Observable<R>, initialValue: R, accumulator: (R, T) -> R): Observable<R> {
    return map<Force> { Force.Dont(it) }
            .mergeWith(forceSignal.map { Force.Do(it) })
            .scan(Force.Dont(initialValue)) { last, new ->
                if (new is Force.Do<*>) {
                    last.value = new.value as R
                } else {
                    last.value = accumulator(last.value, (new as Force.Dont<T>).value)
                }
                last

            }
            .map { it.value }
}

private sealed class Force {
    data class Dont<T>(var value: T) : Force()
    data class Do<T>(var value: T) : Force()
}

/**
 * Right-most value is latest
 */
fun <T> Observable<T>.withCachedValues(cache: Array<T?>, skip: Long = 1): Observable<Array<T?>> {
    return scan(cache) { accumulator, new ->
        val size = accumulator.size
        System.arraycopy(accumulator, 1, accumulator, 0, size - 1); // shift array to left by 1
        accumulator[size - 1] = new // add latest value to end
        accumulator
    }.skip(skip)
}

data class Size(val width: Int, val height: Int)
data class MirrorableSize(val width: Int, val height: Int, val mirror: Boolean)

fun <T> Observable<T>.replayingShare(): Observable<T> {
    return replay(1)
            .refCount()
}

fun <T> Observable<T>.skipFirstIf(predicate: (T) -> Boolean): Observable<T> {
    return publish { o ->
        Observable.merge(
                o.take(1).filter { !predicate(it) },
                o.skip(1))
    }
}

fun View.setGravity(gravity: Int): Boolean {
    val prevGravity = (layoutParams as FrameLayout.LayoutParams).gravity
    if (prevGravity != gravity) {
        (layoutParams as FrameLayout.LayoutParams).gravity = gravity
        requestLayout()
        return true
    }
    return false
}