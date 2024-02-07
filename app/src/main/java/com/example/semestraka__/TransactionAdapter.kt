package com.example.semestraka__

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.core.content.ContextCompat

class TransactionAdapter(private var transactions: List<Transaction>, context: Context) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val db: TransactionHelper = TransactionHelper(context)

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.name)
        val textViewAmount: TextView = view.findViewById(R.id.amount)
        val textViewDescription: TextView = view.findViewById(R.id.description)
        //val textViewType: TextView = view.findViewById(R.id.type)
        val editButton: ImageView = view.findViewById(R.id.btnEdit)
        val deleteButton: ImageView = itemView.findViewById(R.id.btnDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]



        val context = holder.textViewDescription.context

        if(transaction.description == "prijem"){
            holder.textViewAmount.text = "+%.2f"
            holder.textViewAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
        }else{
            holder.textViewAmount.text = "-%.2f"
            holder.textViewAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }




        holder.textViewName.text = transaction.name
        holder.textViewAmount.text = "${transaction.amount} CZK"
        holder.textViewDescription.text = transaction.description
        //holder.textViewType.text = transaction.type.toString()

        holder.editButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("id", transaction.id)
            }
            holder.itemView.context.startActivity(intent)
        }



        holder.deleteButton.setOnClickListener{
            //val transactionId = transactions[position].id
            db.deleteTransaction(transaction)
            refreshData(db.getAllNotes())
        }


    }

    fun refreshData(newTransaction: List<Transaction>){
        transactions = newTransaction
        notifyDataSetChanged()
    }
}