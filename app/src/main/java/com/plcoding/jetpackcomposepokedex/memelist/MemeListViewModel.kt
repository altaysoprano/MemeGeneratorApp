package com.plcoding.jetpackcomposepokedex.memelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.jetpackcomposepokedex.data.models.MemeListEntry
import com.plcoding.jetpackcomposepokedex.data.remote.MemeApi
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.Meme
import com.plcoding.jetpackcomposepokedex.memedetail.MemeInfoState
import com.plcoding.jetpackcomposepokedex.repository.MemeRepository
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class MemeListViewModel @Inject constructor(
    private val repository: MemeRepository
) : ViewModel() {

    private val _memeListState: MutableState<MemeListState> = mutableStateOf(MemeListState())
    val memeListState: MutableState<MemeListState> = _memeListState

    private val _searchText: MutableState<String> = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _isFocused: MutableState<Boolean> = mutableStateOf(false)
    val isFocused: State<Boolean> = _isFocused

    private var searchedList = listOf<MemeListEntry>()

    init {
        loadMemeList()
    }

    fun searchMemeList(query: String) {
        viewModelScope.launch {
            val result = searchedList.filter {
                it.memeName.contains(query.trim(), ignoreCase = true)
            }
            _memeListState.value = memeListState.value.copy(data = result)
        }
    }

    fun updateText(text: String) {
        _searchText.value = text
    }

    fun updateFocused(focused: Boolean) {
        _isFocused.value = focused
    }

/*
    fun loadMemePaginated() {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getMemeList()
            when(result) {
                is Resource.Success -> {
                    val memeEntries = result.data!!.data.memes

                    loadError.value = ""
                    isLoading.value = false
                    searchedList = memeEntries.map { it.memeToMemeListEntry() }
                    memeList.value = memeEntries.map {it.memeToMemeListEntry()}
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
*/

    private fun loadMemeList() {
        repository.getMemeList().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _memeListState.value = MemeListState(isLoading = true)
                    Log.d("Mesaj", "${_memeListState.value.isLoading}")
                }
                is Resource.Success -> {
                    searchedList = result.data?.data?.memes?.map { it.memeToMemeListEntry() }!!
                    _memeListState.value = MemeListState(data = result.data.data.memes.map { it.memeToMemeListEntry() })
                }
                is Resource.Error -> {
                    _memeListState.value = MemeListState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }
}