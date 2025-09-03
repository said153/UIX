package com.example.uix.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uix.MainActivity
import com.example.uix.adapters.CustomRecyclerAdapter
import com.example.uix.databinding.FragmentListsBinding
import com.example.uix.models.ListItem

class ListsFragment : Fragment() {

    private var _binding: FragmentListsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerAdapter: CustomRecyclerAdapter
    private val sampleData = mutableListOf<ListItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRecyclerView()
        setupListView()
    }

    private fun setupViews() {
        updateFeatureStatus()

        binding.btnAddItem.setOnClickListener {
            val newItem = "Elemento ${sampleData.size + 1}"
            val description = if (MainActivity.isFeatureEnabled) {
                "Descripción mejorada con función especial activada"
            } else {
                "Descripción básica del elemento"
            }

            sampleData.add(ListItem(newItem, description, sampleData.size + 1))
            recyclerAdapter.notifyItemInserted(sampleData.size - 1)

            Toast.makeText(context, "Elemento añadido: $newItem", Toast.LENGTH_SHORT).show()
        }

        binding.btnClearList.setOnClickListener {
            val itemCount = sampleData.size
            sampleData.clear()
            recyclerAdapter.notifyDataSetChanged()
            Toast.makeText(context, "Lista limpiada ($itemCount elementos eliminados)", Toast.LENGTH_SHORT).show()
        }

        binding.btnAddSpecial.setOnClickListener {
            if (MainActivity.isFeatureEnabled) {
                val specialItem = ListItem(
                    "⭐ Elemento Especial",
                    "Este elemento solo aparece cuando la función especial está activada",
                    999
                )
                sampleData.add(0, specialItem)
                recyclerAdapter.notifyItemInserted(0)
                binding.recyclerView.scrollToPosition(0)
                Toast.makeText(context, "Elemento especial añadido!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Activa la función especial en la pestaña de Selección", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnAddSpecial.visibility = if (MainActivity.isFeatureEnabled) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        sampleData.addAll(
            listOf(
                ListItem("Primer elemento", "Esta es la descripción del primer elemento", 1),
                ListItem("Segundo elemento", "Descripción del segundo elemento con más texto", 2),
                ListItem("Tercer elemento", "Una descripción más larga para mostrar cómo se ve el texto cuando es extenso en una lista", 3),
                ListItem("Cuarto elemento", "Elemento con descripción simple", 4),
                ListItem("Quinto elemento", "Último elemento de la lista inicial", 5)
            )
        )

        recyclerAdapter = CustomRecyclerAdapter(sampleData) { item, position ->
            Toast.makeText(context, "Clicked: ${item.title} (posición $position)", Toast.LENGTH_SHORT).show()
            MainActivity.sharedText = "Último elemento seleccionado: ${item.title}"
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }

    private fun setupListView() {
        val simpleItems = arrayOf(
            "🍎 Manzana - Fruta roja y crujiente",
            "🍌 Plátano - Rico en potasio",
            "🍊 Naranja - Cítrico con vitamina C",
            "🍇 Uvas - Perfectas para snacks",
            "🥝 Kiwi - Exótico y nutritivo",
            "🍓 Fresa - Dulce y deliciosa",
            "🥭 Mango - Tropical y jugoso",
            "🍑 Cereza - Pequeña y sabrosa"
        )

        val listAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            simpleItems
        )

        binding.listView.adapter = listAdapter
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = simpleItems[position]
            Toast.makeText(context, "ListView - Seleccionaste: $selectedItem", Toast.LENGTH_SHORT).show()
            MainActivity.sharedText =
                "Fruta seleccionada desde ListView: ${selectedItem.substring(0, selectedItem.indexOf(" - "))}"
        }
    }

    private fun updateFeatureStatus() {
        binding.tvFeatureStatus.text = if (MainActivity.isFeatureEnabled) {
            "✅ Función especial ACTIVADA - Botón especial disponible"
        } else {
            "❌ Función especial DESACTIVADA - Ve a Selección para activarla"
        }
        binding.btnAddSpecial.visibility = if (MainActivity.isFeatureEnabled) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        updateFeatureStatus()
        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
