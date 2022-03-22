package com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list

import com.plcoding.jetpackcomposepokedex.data.models.MemeListEntry

data class Meme(
    val box_count: Int,
    val height: Int,
    val id: String,
    val name: String,
    val url: String,
    val width: Int
) {

    fun memeToMemeListEntry(): MemeListEntry{
        return MemeListEntry(
            memeName = name,
            imageUrl = url,
            memeId = id,
            height = height,
            width = width,
            boxCount = box_count
        )
    }
}