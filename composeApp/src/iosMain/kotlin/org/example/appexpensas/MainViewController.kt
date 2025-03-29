package org.example.appexpensas

import androidx.compose.ui.window.ComposeUIViewController
import di.appModule
import org.koin.core.context.startKoin
import logger.initLogger

fun MainViewController() = ComposeUIViewController {
    App()
    initLogger()
}

fun initKoin() {
    startKoin {
        modules(appModule())
    }.koin
}