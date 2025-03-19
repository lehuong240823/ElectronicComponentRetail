package org.example.project.core.enums

enum class AccountRoleType(val ID: Byte, private val value: String) {
    Administrator(1, "Administrator"),
    User(2, "User");

    override fun toString(): String {
        return value
    }

    fun getId(): Byte {
        return ID
    }
}