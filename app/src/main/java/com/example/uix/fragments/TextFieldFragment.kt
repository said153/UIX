package com.example.uix.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uix.MainActivity
import com.example.uix.databinding.FragmentTextfieldBinding

class TextFieldFragment : Fragment() {

    private var _binding: FragmentTextfieldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextfieldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        // Mostrar texto compartido actual
        binding.tvSharedText.text = "Texto compartido actual: ${MainActivity.sharedText}"

        // EditText simple
        binding.etSimple.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.tvSimpleResult.text = "Escribiste: ${s.toString()}"
            }
        })

        // EditText contraseña
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && binding.etPassword.text.toString().isNotEmpty()) {
                Toast.makeText(context, "Contraseña ingresada", Toast.LENGTH_SHORT).show()
            }
        }

        // EditText números
        binding.etNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val number = s.toString().toDoubleOrNull()
                binding.tvNumberResult.text = if (number != null) {
                    "Número × 2 = ${number * 2}"
                } else {
                    "Ingresa un número válido"
                }
            }
        })

        // Botón actualizar texto compartido
        binding.btnUpdateShared.setOnClickListener {
            val newText = binding.etSharedUpdate.text.toString()
            if (newText.isNotEmpty()) {
                MainActivity.sharedText = newText
                binding.tvSharedText.text = "Texto compartido actual: ${MainActivity.sharedText}"
                binding.etSharedUpdate.text.clear()
                Toast.makeText(context, "Texto compartido actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Ingresa un texto primero", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
