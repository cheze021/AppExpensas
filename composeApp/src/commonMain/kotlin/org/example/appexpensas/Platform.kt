package org.example.appexpensas

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform