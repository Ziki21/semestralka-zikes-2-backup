package com.example.semestraka__


import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.semestraka__.databinding.ActivityAddTransactionBinding

import com.google.android.material.snackbar.Snackbar

class AddTransaction : AppCompatActivity() {


    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var db: TransactionHelper
    private lateinit var dateEditStk: EditText
    private lateinit var dateEditReminder: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TransactionHelper(this)

        binding.btnClose.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnpridatTransakci.setOnClickListener {
            val name = binding.nazevvklad.text.toString()
            val amountString = binding.castkavklad.text.toString()
            val description = binding.popisvklad.text.toString()
            //val typeString = binding.nazevvklad.text.toString()

            if (name.isNotEmpty() && amountString.isNotBlank() && description.isNotEmpty()){
                val amount = amountString.toDouble()
                //val type = typeString.toInt()
                val note = Transaction(0, name, amount, description)

                db.insertTransaction(note)
                finish()
                Toast.makeText(this, "Transakce vložena.", Toast.LENGTH_SHORT).show()
            }
            else {
                Snackbar.make(it,"Všechny pole musí být vyplněna!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
