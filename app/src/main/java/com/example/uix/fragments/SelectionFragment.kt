package com.example.uix.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uix.MainActivity
import com.example.uix.databinding.FragmentSelectionBinding

class SelectionFragment : Fragment() {

    private var _binding: FragmentSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        // Actualizar estado inicial
        updateStatusDisplay()

        // CheckBox listeners
        binding.cbOption1.setOnCheckedChangeListener { _, isChecked ->
            updateStatusDisplay()
            if (isChecked) {
                Toast.makeText(context, "Opción 1 seleccionada", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cbOption2.setOnCheckedChangeListener { _, isChecked ->
            updateStatusDisplay()
            if (isChecked) {
                Toast.makeText(context, "Opción 2 seleccionada", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cbOption3.setOnCheckedChangeListener { _, isChecked ->
            updateStatusDisplay()
            if (isChecked) {
                Toast.makeText(context, "Opción 3 seleccionada", Toast.LENGTH_SHORT).show()
            }
        }

        // RadioButton listeners
        binding.rgColors.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbRed.id -> {
                    binding.tvSelectedColor.text = "Color seleccionado: Rojo"
                    binding.tvSelectedColor.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                }
                binding.rbGreen.id -> {
                    binding.tvSelectedColor.text = "Color seleccionado: Verde"
                    binding.tvSelectedColor.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                }
                binding.rbBlue.id -> {
                    binding.tvSelectedColor.text = "Color seleccionado: Azul"
                    binding.tvSelectedColor.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                }
            }
        }

        // Switch listeners
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvNotificationStatus.text = "Notificaciones: ACTIVADAS"
                binding.tvNotificationStatus.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                Toast.makeText(context, "Notificaciones activadas", Toast.LENGTH_SHORT).show()
            } else {
                binding.tvNotificationStatus.text = "Notificaciones: DESACTIVADAS"
                binding.tvNotificationStatus.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                Toast.makeText(context, "Notificaciones desactivadas", Toast.LENGTH_SHORT).show()
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvDarkModeStatus.text = "Modo oscuro: ACTIVADO"
                binding.tvDarkModeStatus.setTextColor(resources.getColor(android.R.color.white))
                binding.cardDarkMode.setCardBackgroundColor(resources.getColor(android.R.color.black))
            } else {
                binding.tvDarkModeStatus.text = "Modo oscuro: DESACTIVADO"
                binding.tvDarkModeStatus.setTextColor(resources.getColor(android.R.color.black))
                binding.cardDarkMode.setCardBackgroundColor(resources.getColor(android.R.color.white))
            }
        }

        // Switch conectado con funcionalidad global
        binding.switchFeature.setOnCheckedChangeListener { _, isChecked ->
            MainActivity.isFeatureEnabled = isChecked
            updateFeatureStatus()

            val message = if (isChecked) "Función especial activada globalmente" else "Función especial desactivada"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Inicializar switch con estado actual
        binding.switchFeature.isChecked = MainActivity.isFeatureEnabled

        // Botón de reset
        binding.btnResetSelection.setOnClickListener {
            // Reset checkboxes
            binding.cbOption1.isChecked = false
            binding.cbOption2.isChecked = false
            binding.cbOption3.isChecked = false

            // Reset radio buttons
            binding.rgColors.clearCheck()
            binding.tvSelectedColor.text = "Ningún color seleccionado"
            binding.tvSelectedColor.setTextColor(resources.getColor(com.example.uix.R.color.textSecondary))

            // Reset switches
            binding.switchNotifications.isChecked = false
            binding.switchDarkMode.isChecked = false
            binding.switchFeature.isChecked = false
            MainActivity.isFeatureEnabled = false

            updateStatusDisplay()
            updateFeatureStatus()

            Toast.makeText(context, "Todas las selecciones reseteadas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateStatusDisplay() {
        val selectedOptions = mutableListOf<String>()

        if (binding.cbOption1.isChecked) selectedOptions.add("Opción 1")
        if (binding.cbOption2.isChecked) selectedOptions.add("Opción 2")
        if (binding.cbOption3.isChecked) selectedOptions.add("Opción 3")

        binding.tvCheckboxStatus.text = if (selectedOptions.isEmpty()) {
            "Ninguna opción seleccionada"
        } else {
            "Seleccionadas: ${selectedOptions.joinToString(", ")}"
        }
    }

    private fun updateFeatureStatus() {
        binding.tvFeatureStatus.text = if (MainActivity.isFeatureEnabled) {
            "Estado global: Función ACTIVADA"
        } else {
            "Estado global: Función DESACTIVADA"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}