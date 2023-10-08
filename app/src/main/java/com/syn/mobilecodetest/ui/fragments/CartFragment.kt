package com.syn.mobilecodetest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.syn.mobilecodetest.adapter.CartAdapter
import com.syn.mobilecodetest.databinding.FragmentCartBinding
import com.syn.mobilecodetest.model.Cart

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private var list: ArrayList<Cart> = arrayListOf()
    private var cartAdapter: CartAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()


    }

    private fun getData() {

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        val parentReference: DatabaseReference = database.getReference("Cart")

        parentReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val cart = snapshot.getValue(Cart::class.java)
                    if (snapshot != null) {
                        list.add(cart!!)
                    }

                    _binding?.checkoutBtn?.setOnClickListener {
                        var total : Double = 0.0
                        if (list.isNotEmpty()){
                          for (i in list.indices){
                              total += list[i].value!!
                          }
                            Toast.makeText(requireContext(), "Your Total Cost is $total.", Toast.LENGTH_SHORT).show()
                      }
                    }
                    _binding?.rvCart?.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(requireContext())
                        cartAdapter = CartAdapter(list)
                        adapter = cartAdapter
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "${databaseError.details}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}