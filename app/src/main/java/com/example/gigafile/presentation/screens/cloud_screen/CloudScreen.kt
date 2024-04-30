package com.example.gigafile.presentation.screens.cloud_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.databinding.FragmentCloudBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloudScreen: Fragment(), BaseScreen {
    private val viewModel by viewModels<CloudScreenViewModel>()
    private lateinit var binding: FragmentCloudBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCloudBinding.inflate(inflater, container, false)
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