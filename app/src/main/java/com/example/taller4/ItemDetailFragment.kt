package com.example.taller4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class ItemDetailFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_item_detail, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val detailTextView = view.findViewById<TextView>(R.id.detailTextView)
        sharedViewModel.selectedItem.observe(viewLifecycleOwner) { selectedItem ->
            detailTextView.text = "Detalles de: $selectedItem"
        }

        return view
    }
}
