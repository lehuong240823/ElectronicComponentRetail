package org.example.project.model

data class Product(val id: String, val name: String)  {
    override fun toString() : String {
        return "$id - $name"
    }
}