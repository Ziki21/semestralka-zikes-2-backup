package com.example.semestraka__

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finance_table")
data class  Entita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: Double,
    val description: String)
