package org.example.project.model

import kotlinx.datetime.LocalDate

data class Review (val title: String, val body: String, val rate: Int, val avatarUrl: String, val name: String, val date: LocalDate)