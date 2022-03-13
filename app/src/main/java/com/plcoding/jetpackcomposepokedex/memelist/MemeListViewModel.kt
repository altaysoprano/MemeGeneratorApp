package com.plcoding.jetpackcomposepokedex.memelist

import android.util.Log
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemeListViewModel @Inject constructor(
    private val repository: MemeRepository
) : ViewModel() {
    private var curPage = 0

    var memeList = mutableStateOf<List<MemeListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadMemePaginated()
    }

    fun loadMemePaginated() {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getMemeList(PAGE_SIZE, curPage * PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= (result.data!!.data.memes.size - 1)
                    val memeEntries = result.data.data.memes
                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    memeList.value += memeEntries.map {it.memeToMemeListEntry()}
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}