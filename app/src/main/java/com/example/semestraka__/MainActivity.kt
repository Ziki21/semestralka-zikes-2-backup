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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private lateinit var zustatekTextView: TextView
    private lateinit var prijmyTextView: TextView
    private lateinit var vydajeTextView: TextView
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var financeViewModel: FinanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        zustatekTextView = findViewById(R.id.zustatek)
        prijmyTextView = findViewById(R.id.prijmy)
        vydajeTextView = findViewById(R.id.vydaje)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TransactionAdapter(listOf<Transaction>()) // Inicializujte seznam transakcí
        recyclerView.adapter = adapter

        // Příklad aktualizace zobrazení
        //updateUI()
        //button pro přidání transakce
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddTransaction::class.java)
            startActivity(intent)
        }
        val financeDao = FinanceDatabase.getDatabase(application).financeDao()
        val repository = Repository(financeDao)
        val viewModelFactory = FinanceViewModelFactory(repository)
        /**
        financeViewModel = ViewModelProvider(this, viewModelFactory)
            .get(FinanceViewModel::class.java)
        */
        financeViewModel = ViewModelProvider(this, viewModelFactory).get(FinanceViewModel::class.java)

        financeViewModel.allFinances.observe(this, Observer { finances ->
            // Aktualizace UI

        })
    }

    private fun updateUI() {
        // Zde aktualizujte zůstatek, příjmy a výdaje na základě skutečných dat
        zustatekTextView.text = "1000 CZK"
        prijmyTextView.text = "600 CZK"
        vydajeTextView.text = "400 CZK"

        // Aktualizujte seznam transakcí
        val transactions = listOf<Transaction>()
        adapter = TransactionAdapter(transactions)
        recyclerView.adapter = adapter
    }
}
