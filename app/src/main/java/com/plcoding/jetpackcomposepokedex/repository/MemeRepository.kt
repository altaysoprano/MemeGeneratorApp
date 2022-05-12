package com.plcoding.jetpackcomposepokedex.repository

import android.util.Log
import com.plcoding.jetpackcomposepokedex.data.remote.MemeApi
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.MemeList
import com.plcoding.jetpackcomposepokedex.util.Constants
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityScoped
class MemeRepository @Inject constructor(
    private val api: MemeApi
) {

/*
    suspend fun getMemeList(): Resource<MemeList> {
        val response = try {
            api.getMemeList()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
*/

    fun getMemeList(): Flow<Resource<MemeList>> = flow {
        try {
            emit(Resource.Loading<MemeList>())
            val response = api.getMemeList()
            emit(Resource.Success<MemeList>(data = response))
        } catch (e: IOException) {
            emit(Resource.Error<MemeList>("An unknown error occured."))
        }
    }

    fun getMemeInfo(textList: List<String>, memeId: String): Flow<Resource<Meme>> = flow {
        try {
            emit(Resource.Loading<Meme>())
            val response = api.getMemeDetail(
                Constants.USER_NAME,
                Constants.PASSWORD,
                memeId,
                *textList.toTypedArray()
            )
            emit(Resource.Success<Meme>(data = response))
            if (!response.success) {
                emit(Resource.Error("Request failed"))
            }
        }
        catch (e: HttpException) {
            emit(Resource.Error<Meme>("An unknown error occured."))
        }
         catch (e: IOException) {
             emit(Resource.Error<Meme>("Please check your internet connection."))
         }
    }
}