package com.example.cryptowallet.app.core

interface Error {
    val message: String
    val cause: Throwable?
}
