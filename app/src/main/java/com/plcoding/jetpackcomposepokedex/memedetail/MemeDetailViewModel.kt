package com.plcoding.jetpackcomposepokedex.memedetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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
) : ViewModel() {

    private val _textList: SnapshotStateList<String> = mutableStateListOf<String>("Text1", "Text2", "Text3", "Text 4")
    val textList: SnapshotStateList<String> = _textList

    suspend fun getMemeInfo(textList: List<String>, id: String): Resource<Meme> {
        return repository.getMemeInfo(textList, id)
    }

    fun setText0(text: String) {
        _textList[0] = text
    }

    fun setText1(text: String) {
        _textList[1] = text
    }

    fun setText2(text: String) {
        _textList[2] = text
    }

    fun setText3(text: String) {
        _textList[3] = text
    }

}