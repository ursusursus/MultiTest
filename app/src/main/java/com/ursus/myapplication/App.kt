package com.ursus.myapplication

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import androidx.fragment.app.Fragment
import com.ursus.myapplication.di.AppComponent
import com.ursus.myapplication.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasBroadcastReceiverInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 16.12.2018.
 */
class App : Application(),
    HasActivityInjector,
    HasServiceInjector,
    HasBroadcastReceiverInjector,
    HasSupportFragmentInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var serviceInjector: DispatchingAndroidInjector<Service>
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var broadcastReceiver: DispatchingAndroidInjector<BroadcastReceiver>

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
            .apply {
                inject(this@App)
            }
    }

    override fun activityInjector() = activityInjector
    override fun serviceInjector() = serviceInjector
    override fun supportFragmentInjector() = fragmentInjector
    override fun broadcastReceiverInjector() = broadcastReceiver
}