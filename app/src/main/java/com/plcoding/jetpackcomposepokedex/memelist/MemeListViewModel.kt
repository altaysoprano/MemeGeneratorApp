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
import com.plcoding.jetpackcomposepokedex.repository.MemeRepository
import com.plcoding.jetpackcomposepokedex.util.Constants.PAGE_SIZE
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class MemeListViewModel @Inject constructor(
    private val repository: MemeRepository
) : ViewModel() {

    var memeList = mutableStateOf<List<MemeListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private val _searchText: MutableState<String> = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _isFocused: MutableState<Boolean> = mutableStateOf(false)
    val isFocused: State<Boolean> = _isFocused

    private var searchedList = listOf<MemeListEntry>()

    init {
        loadMemePaginated()
    }

    fun searchMemeList(query: String) {
        viewModelScope.launch {
            val result = searchedList.filter {
                it.memeName.contains(query.trim(), ignoreCase = true)
            }
            memeList.value = result
        }
    }

    fun updateText(text: String) {
        _searchText.value = text
    }

    fun updateFocused(focused: Boolean) {
        _isFocused.value = focused
    }

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
}