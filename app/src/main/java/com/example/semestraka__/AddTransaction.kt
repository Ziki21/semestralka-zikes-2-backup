package com.example.semestraka__

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*

class AddTransaction : AppCompatActivity() {

    private lateinit var nazevvklad: TextInputEditText
    private lateinit var castkavklad: TextInputEditText
    private lateinit var popisvklad: TextInputEditText
    private lateinit var btnPridatTransakci: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        nazevvklad = findViewById(R.id.nazevvklad)
        castkavklad = findViewById(R.id.castkavklad)
        popisvklad = findViewById(R.id.popisvklad)
        btnPridatTransakci = findViewById(R.id.btnpridatTransakci)

        btnPridatTransakci.setOnClickListener {
            pridatTransakci()
        }

        val btnClose: ImageButton = findViewById(R.id.btnClose)
        btnClose.setOnClickListener {
            finish() // Zavře aktuální aktivitu a vrátí se zpět
        }
    }

    private fun pridatTransakci() {
        val nazev = nazevvklad.text.toString().trim()
        val castkaString = castkavklad.text.toString().trim()
        val popis = popisvklad.text.toString().trim()

        if (nazev.isEmpty() || castkaString.isEmpty() || popis.isEmpty()) {
            Toast.makeText(this, "Všechna pole musí být vyplněna!", Toast.LENGTH_SHORT).show()
            return
        }

        val castka = castkaString.toDoubleOrNull()
        if (castka == null) {
            Toast.makeText(this, "Částka musí být platné číslo!", Toast.LENGTH_SHORT).show()
            return
        }

        val newTransaction = Entita(name = nazev, amount = castka, description = popis)

        // Získání DAO a vložení transakce
        val dao = FinanceDatabase.getDatabase(context = this).financeDao()

        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(newTransaction)

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Transakce Přidána", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
