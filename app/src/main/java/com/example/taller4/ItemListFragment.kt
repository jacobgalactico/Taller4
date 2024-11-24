package com.example.taller4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemListFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        val adapter = ItemAdapter(items) { selectedItem ->
            sharedViewModel.selectItem(selectedItem)
            (activity as MainAppActivity).navigateToDetailFragment()
        }
        recyclerView.adapter = adapter

        return view
    }
}

class ItemAdapter(
    private val items: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = items[position]
        holder.itemView.setOnClickListener { onClick(items[position]) }
    }

    override fun getItemCount(): Int = items.size
}
