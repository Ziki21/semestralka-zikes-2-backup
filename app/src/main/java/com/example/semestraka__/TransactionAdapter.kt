package com.example.semestraka__

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAmount: TextView = view.findViewById(R.id.amount)
        val textViewDescription: TextView = view.findViewById(R.id.description)
        // Přidejte další komponenty podle potřeby
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.textViewAmount.text = "${transaction.amount} CZK"
        holder.textViewDescription.text = transaction.description
        // Nastavte další hodnoty podle potřeby
    }

    override fun getItemCount() = transactions.size
}