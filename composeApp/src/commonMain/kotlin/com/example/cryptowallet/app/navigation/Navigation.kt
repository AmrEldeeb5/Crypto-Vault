package com.example.cryptowallet.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cryptowallet.app.coins.presentation.CoinsListScreen
import com.example.cryptowallet.app.portfolio.presentation.PortfolioScreen


@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = Screens.Coins
    ){
        composable<Screens.Coins>{
            CoinsListScreen(
                onCoinClicked = {}
            )
        }
        composable<Screens.Portfolio>{
            PortfolioScreen(
                onCoinItemClicked = {},
                onDiscoverCoinsClicked = {}
            )
        }
    }
}