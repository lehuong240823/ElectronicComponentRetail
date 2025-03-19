package org.example.project

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

fun checkError(
    showErrorDialog: MutableState<Boolean>,
    operationStatus: State<String>,
    onSuccess: () -> Unit,
    onFailure: () -> Unit = {},
) {
    if(operationStatus.value.contains("Success")) {
        showErrorDialog.value = false
        onSuccess()
    } else {
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