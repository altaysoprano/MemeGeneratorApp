package com.plcoding.jetpackcomposepokedex.memedetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.repository.MemeRepository
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.jvm.Throws

@HiltViewModel
class MemeDetailViewModel @Inject constructor(
    private val repository: MemeRepository
): ViewModel(){

    private val _text0: MutableState<String> = mutableStateOf("")
    val text0: State<String> = _text0

    private val _text1: MutableState<String> = mutableStateOf("")
    val text1: State<String> = _text1

    suspend fun getMemeInfo(text0: String, text1: String, id: String): Resource<Meme> {
        return repository.getMemeInfo(text0, text1, id)
    }

    fun setText0(text: String) {
        _text0.value = text
    }

    fun setText1(text: String) {
        _text1.value = text
    }
}