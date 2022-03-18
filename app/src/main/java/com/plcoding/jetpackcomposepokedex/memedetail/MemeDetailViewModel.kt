package com.plcoding.jetpackcomposepokedex.memedetail

import androidx.lifecycle.ViewModel
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.repository.MemeRepository
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemeDetailViewModel @Inject constructor(
    private val repository: MemeRepository
): ViewModel(){

    suspend fun getMemeInfo(text0: String, text1: String, id: String): Resource<Meme> {
        return repository.getMemeInfo(text0, text1, id)
    }
}