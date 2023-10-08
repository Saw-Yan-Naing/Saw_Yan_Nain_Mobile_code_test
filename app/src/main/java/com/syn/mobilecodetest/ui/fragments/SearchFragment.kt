package com.syn.mobilecodetest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.syn.mobilecodetest.R
import com.syn.mobilecodetest.adapter.CustomAdapter
import com.syn.mobilecodetest.databinding.FragmentSearchBinding
import com.syn.mobilecodetest.model.Cart
import com.syn.mobilecodetest.model.Random
import java.util.Locale


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var list: ArrayList<Random>? = null

    private lateinit var customAdapter: CustomAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backImgBtn.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_mainFragment)
        }

        getList()

        binding.searchView.clearFocus()

        binding.rvSearch.apply {
            customAdapter = CustomAdapter(list!!)
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = customAdapter
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.textView2.text = p0
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
               filter(p0!!)
                return true
            }

        })
    }

    private fun filter(text :String?) {

        if (text != null ){
            val filterList  = ArrayList<Random>()
            for (i in list!!){
                if (i.categories?.lowercase(Locale.ROOT)!!.contains(text)){
                    filterList.add(i)
                }
            }

            if (filterList.isEmpty()){
                Toast.makeText(requireContext(), "No Data", Toast.LENGTH_SHORT).show()
            }else{
                customAdapter.setFilterList(filterList)
            }
        }
    }

    private fun getList() {
        list = arrayListOf()
        list?.add(Random("Carrot", 15.25, R.drawable.carrot,"Fresh Fruits"))
        list?.add(Random("Broccoli", 7.40, R.drawable.broccoli, "Fresh Fruits"))
        list?.add(Random("Spinach", 8.78, R.drawable.spinach, "Fresh Fruits"))
        list?.add(Random("Brinjal", 12.50, R.drawable.brinjel, "Fresh Fruits"))
        list?.add(Random("Potato", 12.50, R.drawable.potato, "Fresh Fruits"))
        list?.add(Random("Tomatoes", 7.40, R.drawable.tomatoes,"Fresh Fruits"))
        list?.add(Random("Oranges", 9.5, R.drawable.orange, "Fresh Fruits"))
        list?.add(Random("Apricot", 18.38, R.drawable.apricot, "Fresh Fruits"))
        list?.add(Random("Capsicum", 24.45, R.drawable.capsicum, "Fresh Fruits"))
        list?.add(Random("Iphone X", 665.00, R.drawable.iphone_x, "Technologies"))
        list?.add(
            Random(
                "Apple watch 3",
                229.00,
                R.drawable.apple_watch3,
                "Technologies"
            )
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}