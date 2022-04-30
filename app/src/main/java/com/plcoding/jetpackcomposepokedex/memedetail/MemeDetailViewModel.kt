package com.plcoding.jetpackcomposepokedex.memedetail

import android.text.TextUtils.indexOf
import android.util.Log
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.repository.MemeRepository
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.jvm.Throws

@HiltViewModel
class MemeDetailViewModel @Inject constructor(
    private val repository: MemeRepository
) : ViewModel() {

    private val _textList: MutableState<MutableList<String>> = mutableStateOf(
        mutableListOf("", "", "", "", "", "")
    )
    val textList: MutableState<MutableList<String>> = _textList

    private val _memeTextList: MutableState<MutableList<String>> = mutableStateOf(
        mutableListOf("", "", "", "", "", "")
    )
    val memeTextList: MutableState<MutableList<String>> = _memeTextList

    private val _memeInfoState: MutableState<MemeInfoState> = mutableStateOf(
        MemeInfoState()
    )
    val memeInfoState: MutableState<MemeInfoState> = _memeInfoState

    val isTexting: MutableState<Boolean> = mutableStateOf(false)

    val alertDialogVisible: MutableState<Boolean> = mutableStateOf(false)

    fun getMemeInfo(textList: MutableList<String>, id: String) {
        val newList = mutableListOf<String>()
        newList.addAll(textList)
        for (i in 0 until newList.size) {
            if (newList[i] == "") newList[i] = " "
        }
        repository.getMemeInfo(newList, id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _memeInfoState.value = MemeInfoState(data = result.data)
                }
                is Resource.Error -> {
                    _memeInfoState.value =
                        MemeInfoState(error = result.message ?: "Unexpected Error")
                }
                is Resource.Loading -> {
                    _memeInfoState.value = MemeInfoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setText(text: String, textNumber: Int) {
        _textList.value[textNumber] = text
    }

    fun setTextList(boxCount: Int) {
        var list = mutableStateListOf<String>()
        for (i in 0 until boxCount) {
            list.add(i, "")
        }
        _textList.value = list
    }

    fun setMemeTextList(boxCount: Int) {
        var list = mutableStateListOf<String>()
        for (i in 0 until boxCount) {
            list.add(i, "Text ${i + 1}")
        }
        _memeTextList.value = list
    }

    fun onSaveDialogOpen() {
        alertDialogVisible.value = true
    }

    fun onSaveDialogDismiss() {
        alertDialogVisible.value = false
    }

    fun onSave() {
        alertDialogVisible.value = false
    }
}