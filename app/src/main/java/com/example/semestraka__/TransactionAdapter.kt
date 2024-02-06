package com.example.semestraka__

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.Intent

class TransactionAdapter(private var transactions: List<Transaction>, context: Context) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val db: TransactionHelper = TransactionHelper(context)

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAmount: TextView = view.findViewById(R.id.amount)
        val textViewDescription: TextView = view.findViewById(R.id.description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.textViewAmount.text = "${transaction.amount} CZK"
        holder.textViewDescription.text = transaction.description

    }

    fun refreshData(newTransaction: List<Transaction>){
        transactions = newTransaction
        notifyDataSetChanged()
    }
}