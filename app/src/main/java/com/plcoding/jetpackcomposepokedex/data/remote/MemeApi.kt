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
        @Query("template_id") id: String,
        @Query("boxes[][text]") vararg text0: String
        ): Meme

}