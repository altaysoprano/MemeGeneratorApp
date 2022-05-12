package com.plcoding.jetpackcomposepokedex.memelist

import com.plcoding.jetpackcomposepokedex.data.models.MemeListEntry
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme.Meme
import com.plcoding.jetpackcomposepokedex.data.remote.responses.meme_list.MemeList

data class MemeListState(
    val isLoading: Boolean = false,
    var data: List<MemeListEntry>?  = null,
    val error: String = ""
)
