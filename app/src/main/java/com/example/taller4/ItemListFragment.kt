package com.example.taller4

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemListFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private val items = mutableListOf("Item 1", "Item 2", "Item 3", "Item 4")
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Configurar el adaptador con la lista inicial
        adapter = ItemAdapter(items) { selectedItem ->
            sharedViewModel.selectItem(selectedItem)
            (activity as MainAppActivity).navigateToDetailFragment()
        }
        recyclerView.adapter = adapter

        // Configurar el botón de añadir
        val inputField = view.findViewById<EditText>(R.id.item_input)
        val addButton = view.findViewById<Button>(R.id.add_item_button)

        addButton.setOnClickListener {
            val newItem = inputField.text.toString()
            if (newItem.isNotBlank()) {
                // Agregar el nuevo elemento a la lista
                items.add(newItem)
                adapter.notifyItemInserted(items.size - 1)
                recyclerView.scrollToPosition(items.size - 1)

                // Limpiar el campo de texto
                inputField.text.clear()

                // Actualizar el widget
                MyAppWidgetProvider.saveItemCount(requireContext(), items.size)
                AppWidgetManager.getInstance(requireContext()).updateAppWidget(
                    ComponentName(requireContext(), MyAppWidgetProvider::class.java),
                    MyAppWidgetProvider.updateAppWidgetViews(requireContext())
                )
            }
        }

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
