package com.example.homework14todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflater<VB>) : Fragment() {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        setListeners()
        observeData()
        refresher()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setUp()
    abstract fun setListeners()
    abstract fun observeData()
    abstract fun refresher()
}