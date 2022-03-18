package com.plcoding.jetpackcomposepokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.jetpackcomposepokedex.memedetail.MemeDetailScreen
import com.plcoding.jetpackcomposepokedex.memelist.MemeListScreen
import com.plcoding.jetpackcomposepokedex.ui.theme.JetpackComposePokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePokedexTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "meme_list_screen") {
                    composable("meme_list_screen")  {
                        MemeListScreen(navController = navController)
                    }
                    composable(
                        "meme_detail_screen/{memeId}",
                        arguments = listOf(
                            navArgument("memeId") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val memeId = remember {
                            it.arguments?.getString("memeId")
                        }
                        MemeDetailScreen(
                            navController = navController,
                            text0 = "",
                            text1 = "",
                            memeId = memeId ?: "")
                    }
                }
            }
        }
    }
}
