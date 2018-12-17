package com.ursus.feature2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ursus.feature2.databinding.FragmentFooBinding

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 04.12.2018.
 */
class FooFragment : Fragment() {

    private var binding: FragmentFooBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_foo, container, false)
        binding = FragmentFooBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = binding!!
        binding.button.text = "Login"
//        binding.toolbar.apply {
//            title = "Foo"
//            subtitle = "Lorem ipsum"
//            setNavigationIcon(R.drawable.ic_up)
//            setNavigationOnClickListener {
//
//            }
//            inflateMenu(R.menu.menu_foo)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
        binding = null
    }
}