package com.example.semestraka__

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TransactionHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private const val DATABASE_NAME = "transactionApp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allTransactions"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AMOUNT = "amount"
        private const val COLUMN_DESCRIPTION = "description"
        //private const val COLUMN_TYPE = "type"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_AMOUNT DOUBLE, $COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
    fun insertTransaction(note: Transaction){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, note.name)
            put(COLUMN_AMOUNT, note.amount)
            put(COLUMN_DESCRIPTION, note.description)
            //put(COLUMN_TYPE, note.type)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getAllNotes(): List<Transaction> {
        val transactionList = mutableListOf<Transaction>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            //val type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
            val note = Transaction(id, name, amount, description)
            transactionList.add(note)
        }
        cursor.close()
        db.close()
        return transactionList
    }
    fun editTransaction(transaction: Transaction){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, transaction.name)
            put(COLUMN_AMOUNT, transaction.amount)
            put(COLUMN_DESCRIPTION, transaction.description)
            //put(COLUMN_TYPE, transaction.type)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(transaction.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }
    fun getTransactionById(id: Int): Transaction{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        //val type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
        cursor.close()
        db.close()
        return Transaction(id, name, amount, description)
    }

    fun deleteTransaction(transaction: Transaction){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(transaction.id.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}