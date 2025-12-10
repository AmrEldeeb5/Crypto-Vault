package com.example.cryptowallet.app

import com.example.cryptowallet.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}