package com.example.cryptovault

import androidx.compose.ui.window.ComposeUIViewController
import com.example.cryptovault.app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
