package com.ajeproduction.typesofflows

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajeproduction.MainViewModel
import com.ajeproduction.typesofflows.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        triggerObservables()
        subscribeToObservables()


    }


    private fun triggerObservables() {
        binding.btnLiveData.setOnClickListener {
            viewModel.updateLiveData()
        }

        binding.btnStateFlow.setOnClickListener {
            viewModel.updateStateFlow()
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateFlow().collectLatest {
                    binding.tvFlow.text = it
                }
            }
        }

        binding.btnSharedFlow.setOnClickListener {
            viewModel.updateShareFlow()
        }
    }


    private fun subscribeToObservables() {
        viewModel.liveData.observe(this) {
            binding.tvLiveData.text = it
        }

        lifecycleScope.launch {
            viewModel.stateFlow.collectLatest {
                binding.tvStateFlow.text = it
            }

        }



        lifecycleScope.launch {
            viewModel.sharedFlow.collectLatest {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }


    }
}