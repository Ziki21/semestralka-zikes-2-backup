package com.example.semestraka__

import androidx.lifecycle.LiveData

class Repository (private val financeDao: Dao) {
    val allFinances: LiveData<List<Entita>> = financeDao.getAllFinances()
}