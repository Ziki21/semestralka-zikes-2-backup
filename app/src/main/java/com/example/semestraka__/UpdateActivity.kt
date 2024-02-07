package com.example.semestraka__

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.semestraka__.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db:TransactionHelper
    private var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TransactionHelper(this)

        id = intent.getIntExtra("id", -1)
        if (id == -1){
            finish()
            return
        }

        binding.btnClose.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val transaction = db.getTransactionById(id)
        binding.nazevedit.setText(transaction.name)
        binding.castkaedit.setText(transaction.amount.toString())
        binding.popisedit.setText(transaction.description)


        binding.btnEdit.setOnClickListener {
            val newName = binding.nazevedit.text.toString()
            val newDesc = binding.popisedit.text.toString()
            val newAmount = binding.castkaedit.text.toString()

            val amountDob = newAmount.toDouble()

            val updatedTransaction = Transaction(id, newName, amountDob, newDesc)
            db.editTransaction(updatedTransaction)
            finish()


        }


    }
}

