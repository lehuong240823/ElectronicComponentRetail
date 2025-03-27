package org.example.project

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.example.project.core.enums.AccountRoleType
import org.example.project.domain.model.Account
import org.example.project.domain.model.AccountRole
import org.example.project.domain.model.Administrator
import org.example.project.domain.model.User

object SessionData {
    private var _currentAccount: Account? = null
    private var _currentAdmin: Administrator? = null
    private var _currentUser: User? = null
    private var _token: String? = null
    private var _tokenExpire: String? = null
    private var _loginTime: Instant? = null

    fun setCurrentAccount(account: Account?) {
        _currentAccount = account
        if (account == null) {
            _tokenExpire == null
            _loginTime == null
            _currentAdmin == null
            _currentUser == null
        }
    }

    fun getCurrentAccount(): Account? {
        return _currentAccount
    }

    fun setCurrenUser(user: User?) {
        _currentUser = user
    }

    fun getCurrentUser(): User? {
        return _currentUser
    }

    fun setCurrentAdmin(admin: Administrator?) {
        _currentAdmin = admin
    }

    fun getCurrentAdmin(): Administrator? {
        return _currentAdmin
    }

    fun setToken(refreshToken: String?) {
        _token = refreshToken
        _loginTime = Clock.System.now()
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

    fun getLoginTime(): Instant? {
        return _loginTime
    }
}