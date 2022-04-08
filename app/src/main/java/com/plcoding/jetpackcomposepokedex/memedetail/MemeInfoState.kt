package com.plcoding.jetpackcomposepokedex.memedetail

import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme

data class MemeInfoState(
    val isLoading: Boolean = false,
    val data: Meme?  = null,
    val error: String = ""
)
