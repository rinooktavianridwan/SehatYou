package com.example.sehatyou.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatyou.roomdb.SehatYouRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SehatYouRoomModel(private val sehatYouRepository: SehatYouRepository) : ViewModel() {
    val getAllTasks: Flow<List<SuggestEntity>> = sehatYouRepository.getAll()

    fun add(suggestEntity: SuggestEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sehatYouRepository.insert(suggestEntity)
        }
    }

    fun update(suggestEntity: SuggestEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sehatYouRepository.update(suggestEntity)
        }
    }

    fun delete(suggestEntity: SuggestEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sehatYouRepository.delete(suggestEntity)
        }
    }

    val getAllDiary: Flow<List<DiaryEntity>> = sehatYouRepository.getAllDiaries()
    fun addDiary(diaryEntity: DiaryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sehatYouRepository.insertDiary(diaryEntity)
        }
    }

    fun getDiaryById(id: Int): LiveData<DiaryEntity?> {
        val diary = MutableLiveData<DiaryEntity?>()
        viewModelScope.launch(Dispatchers.IO) {
            diary.postValue(sehatYouRepository.getDiaryById(id) ?: DiaryEntity(
                title = "",
                description = "",
                category = "Neutral",
                date = "",
                time = ""
            ))
        }
        return diary
    }

    fun updateDiary(diaryEntity: DiaryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sehatYouRepository.updateDiary(diaryEntity)
        }
    }

    fun deleteDiary(diaryEntity: DiaryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sehatYouRepository.deleteDiary(diaryEntity)
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    val searchResults: Flow<List<DiaryEntity>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) sehatYouRepository.getAllDiaries()
            else sehatYouRepository.searchDiaries(query)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    suspend fun getLatestDiaries(limit: Int): List<DiaryEntity> {
        return sehatYouRepository.getLatestDiaries(limit)
    }
    
    suspend fun getLatestSuggestions(limit: Int): List<SuggestEntity> {
        return sehatYouRepository.getLatestSuggestions(limit)
    }
}