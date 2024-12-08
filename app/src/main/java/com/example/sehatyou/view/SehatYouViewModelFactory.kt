package com.example.sehatyou.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sehatyou.model.SehatYouRoomModel
import com.example.sehatyou.roomdb.SehatYouRepository

class SehatYouViewModelFactory(
    private val repository: SehatYouRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SehatYouRoomModel::class.java)) {
            return SehatYouRoomModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
