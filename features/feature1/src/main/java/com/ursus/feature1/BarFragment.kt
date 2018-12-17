package com.ursus.feature1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ursus.core.di.AppComponentProvider
import com.ursus.feature1.databinding.FragmentBarBinding

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 04.12.2018.
 */
class BarFragment : Fragment() {

    private var binding: FragmentBarBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (context!!.applicationContext as AppComponentProvider).appComponent<com.ursus.sharedlib1.di.AppComponent>()
        appComponent.callManager
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