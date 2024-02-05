package com.example.semestraka__
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.*

@Dao
interface Dao {
    @Insert
    fun insert(finance: Entita)

    @Update
    fun update(finance: Entita)

    @Delete
    fun delete(finance: Entita)

    @Query("DELETE FROM finance_table WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM finance_table")
    fun getAllFinances(): LiveData<List<Entita>>




}