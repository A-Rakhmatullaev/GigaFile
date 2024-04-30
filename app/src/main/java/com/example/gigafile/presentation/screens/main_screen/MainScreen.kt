package com.example.gigafile.presentation.screens.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.core.extensions.log
import com.example.gigafile.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen: Fragment(), BaseScreen {
    private val viewModel by viewModels<MainScreenViewModel>()
    private lateinit var binding: FragmentMainBinding
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun initViews() {
        navHost = childFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHost.findNavController()
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener {
                controller, destination, arguments ->
            log("MyLog", "Pressed: $destination")
        }
    }

    override fun initObservers() {
        //TODO("Not yet implemented")
    }
}