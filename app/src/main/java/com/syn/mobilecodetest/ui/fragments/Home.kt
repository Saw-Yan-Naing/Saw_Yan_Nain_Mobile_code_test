package com.syn.mobilecodetest.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.syn.mobilecodetest.R
import com.syn.mobilecodetest.adapter.CustomAdapter
import com.syn.mobilecodetest.databinding.FragmentHomeBinding
import com.syn.mobilecodetest.model.Cart
import com.syn.mobilecodetest.model.Random


class Home : Fragment() {

    private var _binding :FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var list: ArrayList<Random> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList()
        binding.rvHome.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            val customAdapter = CustomAdapter(list)
            adapter = customAdapter
        }
        binding.linearHome.setOnClickListener {
            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.main_container) as NavHostFragment
            val navController = navHostFragment.navController

            navController.navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setList(){
        list = arrayListOf()
        list.add(Random("Carrot", 15.25, R.drawable.carrot,"Fresh Fruits"))
        list.add(Random("Broccoli", 7.40, R.drawable.broccoli, "Fresh Fruits"))
        list.add(Random("Spinach", 8.78, R.drawable.spinach, "Fresh Fruits"))
        list.add(Random("Brinjal", 12.50, R.drawable.brinjel, "Fresh Fruits"))
        list.add(Random("Potato", 12.50, R.drawable.potato, "Fresh Fruits"))
        list.add(Random("Tomatoes", 7.40, R.drawable.tomatoes,"Fresh Fruits"))
        list.add(Random("Oranges", 9.5, R.drawable.orange, "Fresh Fruits"))
        list.add(Random("Apricot", 18.38, R.drawable.apricot, "Fresh Fruits"))
        list.add(Random("Capsicum", 24.45, R.drawable.capsicum, "Fresh Fruits"))
        list.add(Random("Iphone X", 665.00, R.drawable.iphone_x, "Technologies"))
        list.add(
            Random(
                "Apple watch 3",
                229.00,
                R.drawable.apple_watch3,
                "Technologies"
            )
        )
    }
}