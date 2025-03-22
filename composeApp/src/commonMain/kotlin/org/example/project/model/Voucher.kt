package org.example.project.model

import kotlinx.datetime.LocalDate

data class Voucher(val id: String, val name: String, val value: Double, val startDate: LocalDate, val endDate: LocalDate, val products: List<Product>)