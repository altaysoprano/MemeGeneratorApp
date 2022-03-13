package com.plcoding.jetpackcomposepokedex.repository

import com.plcoding.jetpackcomposepokedex.data.remote.MemeApi
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.MemeList
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MemeRepository @Inject constructor(
    private val api: MemeApi
){

    suspend fun getMemeList(limit: Int, offset: Int): Resource<MemeList> {
        val response = try {
            api.getMemeList(limit, offset)
        }catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}