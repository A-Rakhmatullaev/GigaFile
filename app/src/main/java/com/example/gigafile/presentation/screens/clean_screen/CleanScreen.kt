package com.example.gigafile.presentation.screens.clean_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.databinding.FragmentCleanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CleanScreen: Fragment(), BaseScreen {
    private val viewModel by viewModels<CleanScreenViewModel>()
    private lateinit var binding: FragmentCleanBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCleanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun initViews() {
        //TODO("Not yet implemented")
    }

    override fun initObservers() {
        //TODO("Not yet implemented")
    }
}