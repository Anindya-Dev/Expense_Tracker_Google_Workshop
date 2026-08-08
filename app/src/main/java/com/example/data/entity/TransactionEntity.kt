package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // "income" or "expense"
    val amount: Double,
    val category: String,
    val description: String,
    val timestamp: Long = System.currentTimeMillis()
)
