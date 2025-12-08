package com.example.cryptowallet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform