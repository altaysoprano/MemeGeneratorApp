package com.plcoding.jetpackcomposepokedex.memelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.plcoding.jetpackcomposepokedex.R

@Composable
fun NoResultsFoundScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.no_results_found),
                contentDescription = "No Results Found",
                modifier = Modifier.fillMaxSize(0.25f)
            )
            Text(text = "No Results Found", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
    }
}