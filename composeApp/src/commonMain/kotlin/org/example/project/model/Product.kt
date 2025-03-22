package org.example.project.model

data class Product(val id: String, val name: String, val tag: String, val price: Double, val description: String, val imageUrl: String)  {
    override fun toString() : String {
        return "$id - $name"
    }
}