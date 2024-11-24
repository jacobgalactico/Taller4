package com.example.taller4

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
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
    private lateinit var adapter: ItemAdapter
    private val items = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Cargar la lista guardada
        items.addAll(loadList(requireContext()))

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Configurar el adaptador
        adapter = ItemAdapter(items, { selectedItem ->
            sharedViewModel.selectItem(selectedItem)
            (activity as MainAppActivity).navigateToDetailFragment()
        }, { position ->
            // Eliminar el elemento
            items.removeAt(position)
            adapter.notifyItemRemoved(position)

            // Guardar la lista actualizada
            saveList(requireContext(), items)

            // Actualizar el widget
            MyAppWidgetProvider.saveItemCount(requireContext(), items.size)
            MyAppWidgetProvider.notifyWidgetUpdate(requireContext())
        })
        recyclerView.adapter = adapter

        // Configurar el botón de añadir
        val inputField = view.findViewById<EditText>(R.id.item_input)
        val addButton = view.findViewById<Button>(R.id.add_item_button)

        addButton.setOnClickListener {
            val newItem = inputField.text.toString()
            if (newItem.isNotBlank()) {
                // Agregar el nuevo elemento
                items.add(newItem)
                adapter.notifyItemInserted(items.size - 1)
                recyclerView.scrollToPosition(items.size - 1)

                // Limpiar el campo de texto
                inputField.text.clear()

                // Guardar la lista actualizada
                saveList(requireContext(), items)

                // Actualizar el widget
                MyAppWidgetProvider.saveItemCount(requireContext(), items.size)
                MyAppWidgetProvider.notifyWidgetUpdate(requireContext())
            }
        }

        return view
    }

    private fun saveList(context: Context, items: List<String>) {
        val prefs = context.getSharedPreferences("item_list_prefs", Context.MODE_PRIVATE)
        prefs.edit().putStringSet("item_list", items.toSet()).apply()
    }

    private fun loadList(context: Context): MutableList<String> {
        val prefs = context.getSharedPreferences("item_list_prefs", Context.MODE_PRIVATE)
        val itemSet = prefs.getStringSet("item_list", emptySet())
        return itemSet?.toMutableList() ?: mutableListOf()
    }
}


class ItemAdapter(
    private val items: List<String>,
    private val onClick: (String) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val deleteButton: Button = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = items[position]
        holder.itemView.setOnClickListener { onClick(items[position]) }
        holder.deleteButton.setOnClickListener { onDelete(position) }
    }

    override fun getItemCount(): Int = items.size
}
