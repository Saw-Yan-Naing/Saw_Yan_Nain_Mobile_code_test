package com.syn.mobilecodetest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.syn.mobilecodetest.R
import com.syn.mobilecodetest.model.Cart
import com.syn.mobilecodetest.model.Random

class CustomAdapter(private var list: ArrayList<Random>?) :
    RecyclerView.Adapter<CustomAdapter.MainViewHolder>() {


    fun setFilterList(list: ArrayList<Random>) {
        this.list = list
        notifyDataSetChanged()
    }


    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name_custom)
        val cate = itemView.findViewById<TextView>(R.id.categories_custom)
        val price = itemView.findViewById<TextView>(R.id.price_custom)
        val image = itemView.findViewById<ImageView>(R.id.iv_custom_)
        val add = itemView.findViewById<ImageButton>(R.id.addtocart_btn)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_custom, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val pos = list!![position]

        holder.name.text = pos.name
        holder.image.setImageResource(pos.photo!!)
        holder.cate.text = pos.categories
        holder.price.text = pos.price.toString()

        holder.add.setOnClickListener {

            val database: FirebaseDatabase = FirebaseDatabase.getInstance()

            val reference: DatabaseReference = database.getReference("Cart")

            val cart = Cart(pos.name,pos.photo,pos.price)

            val newKey = reference.push().key

            if (newKey != null) {
                reference.child(newKey).setValue(cart)
            }
        }
    }


}
