package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Alex Mercer",
    val email: String = "alex.mercer@example.com",
    val salary: Double = 45000.0,
    val fixedExpenses: Double = 15000.0,
    val currentSavings: Double = 10000.0,
    val primaryGoal: String = "Emergency Fund",
    val onboarded: Boolean = false
)
