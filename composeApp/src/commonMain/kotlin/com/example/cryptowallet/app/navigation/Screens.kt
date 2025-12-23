package com.example.cryptowallet.app.navigation

import kotlinx.serialization.Serializable

interface Screens {

    @Serializable
    data object Coins : Screens

    @Serializable
    data object Portfolio : Screens
}