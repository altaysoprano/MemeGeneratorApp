package com.plcoding.jetpackcomposepokedex.repository

import android.util.Log
import com.plcoding.jetpackcomposepokedex.data.remote.MemeApi
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.MemeList
import com.plcoding.jetpackcomposepokedex.util.Constants
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MemeRepository @Inject constructor(
    private val api: MemeApi
) {

    suspend fun getMemeList(): Resource<MemeList> {
        val response = try {
            api.getMemeList()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getMemeInfo(textList: List<String>, memeId: String): Resource<Meme> {
        val response = try {
            api.getMemeDetail(
                Constants.USER_NAME,
                Constants.PASSWORD,
                memeId,
                *textList.toTypedArray()
            )
        }
        catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        if (!response.success) {
            return Resource.Error("Request failed")
        }
        return Resource.Success(response)
    }
}