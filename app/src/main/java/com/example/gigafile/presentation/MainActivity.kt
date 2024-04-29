package com.example.gigafile.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.gigafile.R
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.core.extensions.log
import com.example.gigafile.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BaseScreen {
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    override fun initViews() {
        mainActivityViewModel.loadData()
        TODO("Not yet implemented")
    }

    override fun initObservers() {
        mainActivityViewModel.directoryLiveData.observe(this) { data ->
            data.forEach {
                log("MyLog", "I have: ${it.name}")
            }
        }
    }
}