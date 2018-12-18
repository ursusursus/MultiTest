package com.ursus.feature1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ursus.core.CallManager
import com.ursus.feature1.databinding.FragmentBarBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 04.12.2018.
 */
class BarFragment : Fragment() {

    @Inject lateinit var callManager: CallManager

    private var binding: FragmentBarBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bar, container, false)
        binding = FragmentBarBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.button.text = "barbarbar"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
        binding = null
    }
}