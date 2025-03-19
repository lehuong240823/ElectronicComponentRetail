package org.example.project

const val DESKTOP_PAGE_SIZE = 20
const val WEB_PAGE_SIZE = 20
const val ANDROID_PAGE_SIZE = 20
const val CURRENCY = "$"
const val GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth"
const val DESKTOP_REDIRECT_URL = "http://localhost:8080/"
const val WEB_REDIRECT_URL = "http://localhost:8080/"
val ANDROID_REDIRECT_URL = ""
const val LOCALHOST_URL = "http://localhost:10000"
const val RENDER_URL = "https://electroniccomponentretailserver-latest.onrender.com"
const val BASE_URL = LOCALHOST_URL

fun getGoogleAuthUrl(redirectUri: String): String {
    val authUrl = GOOGLE_AUTH_URL
        .plus("?client_id=$OAUTH_CLIENT_ID")
        .plus("&redirect_uri=$redirectUri" )
        .plus("&response_type=code")
        .plus("&scope=email%20profile%20openid")
    return authUrl
}
