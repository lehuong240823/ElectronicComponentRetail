package org.example.project

import org.example.project.domain.model.Account
import org.example.project.domain.model.AccountRole

object SessionData {
    private var _currentAccount: Account? = Account(
        username = "example1234",
        email = "example@gmail.com",
        phoneNumber = "09282828282",
        accountRole = AccountRole(
            name = org.example.project.core.enums.AccountRoleType.Administrator.name
        )
    )
    private var _token: String? = null
    private var _tokenExpire: String? = null

    fun setCurrentAccount(account: Account) {
        _currentAccount = account
    }

    fun getCurrentAccount(): Account? {
        return _currentAccount
    }

    fun setToken(refreshToken: String?) {
        _token = refreshToken
    }

    fun getToken(): String? {
        return _token
    }

    fun setTokenExpire(tokenExpire: String?) {
        _tokenExpire = tokenExpire
    }

    fun getTokenExpire(): String? {
        return _tokenExpire
    }
}