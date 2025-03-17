package org.example.project.core

val DESKTOP_PAGE_SIZE = 20
val WEB_PAGE_SIZE = 20
val ANDROID_PAGE_SIZE = 20
val CURRENCY = "$"
val API_KEY = "AIzaSyCnS08qKxWgFEKhF0Le5u4lT_EheqdE1TM"
val GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth"
val OAUTH_CLIENT_ID = "10594710747-8kdh7tfuuh4dh63g29jl6laa08pkb00e.apps.googleusercontent.com"
val DESKTOP_REDIRECT_URL = "http://localhost:8080/"
val WEB_REDIRECT_URL = "http://localhost:8080/"
val ANDROID_REDIRECT_URL = ""
val LOCALHOST_URL = "http://localhost:10000"
val RENDER_URL = "https://electroniccomponentretailserver-latest.onrender.com"
val BASE_URL = RENDER_URL

fun getGoogleAuthUrl(redirectUri: String): String {
    val authUrl = GOOGLE_AUTH_URL
        .plus("?client_id=$OAUTH_CLIENT_ID")
        .plus("&redirect_uri=$redirectUri" )
        .plus("&response_type=code")
        .plus("&scope=email%20profile%20openid")
    return authUrl
}
