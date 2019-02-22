package com.example.penguinscouts

object Urls {
    private const val hostname = "http://10.0.2.2:3000"

    const val login = "$hostname/users"
    const val logout = "$hostname/users/logout"
    const val authenticate = "$hostname/users"
    const val match = "$hostname/match"
}