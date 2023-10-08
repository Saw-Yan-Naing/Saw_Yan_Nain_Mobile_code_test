package com.syn.mobilecodetest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.syn.mobilecodetest.R
import com.syn.mobilecodetest.model.Cart

class CartAdapter(private var list: ArrayList<Cart>) :
    RecyclerView.Adapter<CartAdapter.MainViewHolder>() {

    private var count = 1

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.getReference("Cart")

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name_cart)
        val price = itemView.findViewById<TextView>(R.id.price_cart)
        val total = itemView.findViewById<TextView>(R.id.countiD)
        val plus = itemView.findViewById<ImageView>(R.id.plus_cart)
        val minus = itemView.findViewById<ImageView>(R.id.minus_cart)
        val photo = itemView.findViewById<ImageView>(R.id.iv_cartRV)
        val weight = itemView.findViewById<TextView>(R.id.weight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_rv, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val item = list[position]

        holder.name.text = item.title
        holder.photo.setImageResource(item.pic!!)
        holder.total.text = count.toString()
        holder.weight.text = "$count kg"
        holder.price.text = (item.value!! * count).toString()

        holder.plus.setOnClickListener {
            count++
            holder.total.text = count.toString()
            holder.price.text = (item.value!! * count).toString()
            holder.weight.text = "$count kg"
        }

        holder.minus.setOnClickListener {
            count--
            holder.total.text = count.toString()
            holder.price.text = (item.value!! * count).toString()
            holder.weight.text = "$count kg"

            if (count == 0) {

                val query: Query =
                    reference.orderByChild("title").equalTo(item.title.toString())

                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (childSnapshot in dataSnapshot.children) {
                            childSnapshot.ref.removeValue()
                            notifyDataSetChanged()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(
                            holder.itemView.context,
                            "Error removing data: ${databaseError.toException()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            }

        }

        holder.weight.text = "$count kg"
    }

}