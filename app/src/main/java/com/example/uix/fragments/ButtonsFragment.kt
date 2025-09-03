package com.example.uix.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uix.R
import com.example.uix.databinding.FragmentButtonsBinding
import com.google.android.material.snackbar.Snackbar

class ButtonsFragment : Fragment() {

    private var _binding: FragmentButtonsBinding? = null
    private val binding get() = _binding!!
    private var clickCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentButtonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        // Button simple
        binding.btnSimple.setOnClickListener {
            clickCounter++
            binding.tvClickCounter.text = "Clicks: $clickCounter"
            Toast.makeText(context, "¡Botón presionado! Click #$clickCounter", Toast.LENGTH_SHORT).show()
        }

        // ImageButton
        binding.btnImage.setOnClickListener {
            Snackbar.make(binding.root, "¡ImageButton presionado!", Snackbar.LENGTH_SHORT)
                .setAction("OK") { }
                .show()
        }

        // FloatingActionButton
        binding.fab.setOnClickListener {
            if (binding.fab.drawable.constantState == resources.getDrawable(R.drawable.ic_add).constantState) {
                binding.fab.setImageResource(R.drawable.ic_remove)
                binding.tvFabStatus.text = "Estado FAB: Modo quitar"
            } else {
                binding.fab.setImageResource(R.drawable.ic_add)
                binding.tvFabStatus.text = "Estado FAB: Modo agregar"
            }
        }

        // ToggleButton
        binding.btnToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.btnToggle.text = "ACTIVADO"
                binding.tvToggleStatus.text = "El toggle está activado"
                binding.tvToggleStatus.setTextColor(resources.getColor(R.color.colorPrimary, null))
            } else {
                binding.btnToggle.text = "DESACTIVADO"
                binding.tvToggleStatus.text = "El toggle está desactivado"
                binding.tvToggleStatus.setTextColor(resources.getColor(R.color.textSecondary, null))
            }
        }

        // Botón de reset
        binding.btnReset.setOnClickListener {
            clickCounter = 0
            binding.tvClickCounter.text = "Clicks: 0"
            binding.btnToggle.isChecked = false
            binding.fab.setImageResource(R.drawable.ic_add)
            binding.tvFabStatus.text = "Estado FAB: Modo agregar"
            Toast.makeText(context, "Todo reseteado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
