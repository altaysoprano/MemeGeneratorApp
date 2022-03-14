package com.plcoding.jetpackcomposepokedex.data.remote

import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.MemeList
import retrofit2.http.GET
import retrofit2.http.Query

interface MemeApi {

    @GET("get_memes")
    suspend fun getMemeList(): MemeList
}