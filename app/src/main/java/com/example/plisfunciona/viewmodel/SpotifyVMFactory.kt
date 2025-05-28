package com.example.plisfunciona.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plisfunciona.repositorio.SpotifyRepository // Assuming you have a repository
import com.example.plisfunciona.viewmodel.SpotifyVM

class SpotifyVMFactory(private val repository: SpotifyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpotifyVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SpotifyVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}