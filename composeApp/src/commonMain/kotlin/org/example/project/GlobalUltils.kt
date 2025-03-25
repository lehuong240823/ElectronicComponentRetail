package org.example.project

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.datetime.Clock
import org.example.project.core.enums.AlertType
import kotlin.time.Duration.Companion.seconds

fun checkError(
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType> = mutableStateOf(AlertType.Default),
    operationStatus: State<String>,
    onSuccess: () -> Unit = {},
    onFailure: () -> Unit = {},
) {
    if (SessionData.getCurrentAccount() != null && SessionData.getLoginTime() != null && SessionData.getTokenExpire() != null) {
        val expireTime = SessionData.getTokenExpire()?.toInt()?.let { SessionData.getLoginTime()?.plus(it.seconds) }
        if(expireTime == Clock.System.now()) {
            showErrorDialog.value = true
            alertType.value = AlertType.TokenExpired
        }
    }
    if(operationStatus.value.contains("Success")) {
        showErrorDialog.value = false
        onSuccess()
    } else {
        alertType.value = AlertType.Default
        showErrorDialog.value = true
        onFailure()
    }
}

suspend fun executeSuspendFunction(
    showLoadingOverlay: MutableState<Boolean>,
    function: suspend () -> Unit
) {
    showLoadingOverlay.value = true
    function()
    showLoadingOverlay.value = false
}


fun pushWithLimitScreen(navigator: Navigator?, screen: Screen) {
    navigator?.size?.let {
        if (it >= 3) {
            var screens = navigator.items.drop(1)
            navigator.replaceAll(screens)
        }
    }
    navigator?.push(screen)
}