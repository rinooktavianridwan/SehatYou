package com.example.sehatyou.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatyou.roomdb.SehatYouRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SehatYouModel(private val sehatYouRepository: SehatYouRepository) : ViewModel() {
    val getAllTasks: Flow<List<SuggestEntity>> = sehatYouRepository.getAll()

    fun addTask(suggestEntity: SuggestEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sehatYouRepository.insert(suggestEntity)
        }
    }

    fun deleteTask(suggestEntity: SuggestEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sehatYouRepository.delete(suggestEntity)
        }
    }
}