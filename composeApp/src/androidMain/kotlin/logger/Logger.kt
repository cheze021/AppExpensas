package logger

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initLogger() {
    Napier.base(DebugAntilog()) // Permite ver logs en Logcat
}