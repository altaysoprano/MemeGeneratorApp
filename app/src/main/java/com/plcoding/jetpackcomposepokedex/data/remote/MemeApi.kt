package com.plcoding.jetpackcomposepokedex.data.remote

import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.MemeList
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import kotlin.jvm.Throws

interface MemeApi {

    @GET("get_memes")
    suspend fun getMemeList(): MemeList

    @POST("caption_image")
    suspend fun getMemeDetail(
        @Query("username") userName: String,
        @Query("password") password: String,
        @Query("text0") text0: String,
        @Query("text1") text1: String,
        @Query("text2") text2: String,
        @Query("text3") text3: String,
        @Query("template_id") id: String
    ): Meme

}