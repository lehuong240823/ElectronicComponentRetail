package org.example.project.core.enums

enum class AccountStatusType(val ID: Byte, val value: String) {
    Active(1, "Active"),
    Inactive(2, "Inactive"),
    Deleted(3, "Deleted"),
    Suspended(4, "Suspended");

    override fun toString(): String {
        return value
    }

    fun getId(): Byte {
        return ID
    }
}