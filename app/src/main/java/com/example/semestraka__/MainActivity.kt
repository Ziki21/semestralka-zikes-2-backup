package com.example.semestraka__

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.LiveData
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestraka__.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TransactionHelper
    private lateinit var notesAdapter: TransactionAdapter

    private lateinit var zustatekTextView: TextView
    private lateinit var prijmyTextView: TextView
    private lateinit var vydajeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        zustatekTextView = findViewById(R.id.zustatek)
        prijmyTextView = findViewById(R.id.prijmy)
        vydajeTextView = findViewById(R.id.vydaje)

        db = TransactionHelper(this)
        notesAdapter = TransactionAdapter(db.getAllNotes(),this)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = notesAdapter

        //updateUI()
        //button pro přidání transakce
        binding.btnAdd.setOnClickListener(){
            val intent = Intent(this, AddTransaction::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        val transactions = db.getAllNotes()
        notesAdapter.refreshData(transactions)
        updateUI(transactions)
    }

    private fun updateUI(transactions: List<Transaction>) {
        val totalIncome = calculateTotalIncome(transactions)
        val totalExpenses = calculateTotalExpenses(transactions)
        val balance = totalIncome - totalExpenses

        zustatekTextView.text = "$balance Kč"
        prijmyTextView.text = "$totalIncome Kč"
        vydajeTextView.text = "$totalExpenses Kč"
    }

    private fun calculateTotalIncome(transactions: List<Transaction>): Double {
        return transactions.filter { it.description.contains("prijem", ignoreCase = true) }
            .sumOf { it.amount }
    }

    private fun calculateTotalExpenses(transactions: List<Transaction>): Double {
        return transactions.filter { it.description.contains("vydaj", ignoreCase = true) }
            .sumOf { it.amount }
    }

}
