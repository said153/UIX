package com.example.uix.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uix.MainActivity
import com.example.uix.R
import com.example.uix.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private var progressHandler: Handler? = null
    private var progressRunnable: Runnable? = null
    private var currentProgress = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        // Actualizar texto compartido
        updateSharedText()

        // TextView dinámico que cambia con el texto compartido
        binding.tvDynamic.text = "Texto actual: ${MainActivity.sharedText}"

        // ImageView con click listener
        binding.ivClickable.setOnClickListener {
            // Cambiar imagen al hacer click
            val currentDrawable = binding.ivClickable.drawable
            if (currentDrawable.constantState == resources.getDrawable(R.drawable.ic_star_outline).constantState) {
                binding.ivClickable.setImageResource(R.drawable.ic_star_filled)
                binding.tvImageStatus.text = "⭐ Estrella llena - ¡Te gusta!"
                binding.tvImageStatus.setTextColor(resources.getColor(R.color.colorAccent))
                Toast.makeText(context, "¡Estrella activada!", Toast.LENGTH_SHORT).show()
            } else {
                binding.ivClickable.setImageResource(R.drawable.ic_star_outline)
                binding.tvImageStatus.text = "☆ Estrella vacía - Toca para llenar"
                binding.tvImageStatus.setTextColor(resources.getColor(R.color.textSecondary))
                Toast.makeText(context, "Estrella desactivada", Toast.LENGTH_SHORT).show()
            }
        }

        // ProgressBar circular - animación automática
        startCircularProgress()

        // ProgressBar horizontal con controles
        setupHorizontalProgressBar()

        // TextView con información del estado global
        updateGlobalStatus()

        // Botón para actualizar información
        binding.btnUpdateInfo.setOnClickListener {
            updateAllInformation()
            Toast.makeText(context, "Información actualizada", Toast.LENGTH_SHORT).show()
        }

        // Botón para reset de la imagen
        binding.btnResetImage.setOnClickListener {
            binding.ivClickable.setImageResource(R.drawable.ic_star_outline)
            binding.tvImageStatus.text = "☆ Estrella vacía - Toca para llenar"
            binding.tvImageStatus.setTextColor(resources.getColor(R.color.textSecondary))
            Toast.makeText(context, "Imagen reseteada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupHorizontalProgressBar() {
        binding.progressHorizontal.max = 100
        binding.progressHorizontal.progress = 0

        binding.btnStartProgress.setOnClickListener {
            startHorizontalProgress()
        }

        binding.btnResetProgress.setOnClickListener {
            resetProgress()
        }
    }

    private fun startHorizontalProgress() {
        binding.btnStartProgress.isEnabled = false
        currentProgress = 0

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (currentProgress <= 100) {
                    binding.progressHorizontal.progress = currentProgress
                    binding.tvProgressStatus.text = "Progreso: $currentProgress%"

                    when {
                        currentProgress < 30 -> {
                            binding.tvProgressStatus.setTextColor(resources.getColor(R.color.colorPrimary))
                        }
                        currentProgress < 70 -> {
                            binding.tvProgressStatus.setTextColor(resources.getColor(R.color.colorAccent))
                        }
                        else -> {
                            binding.tvProgressStatus.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                        }
                    }

                    if (currentProgress == 100) {
                        binding.btnStartProgress.isEnabled = true
                        binding.tvProgressStatus.text = "¡Progreso completado! 🎉"
                        Toast.makeText(context, "¡Proceso completado!", Toast.LENGTH_SHORT).show()
                    } else {
                        currentProgress += 2
                        handler.postDelayed(this, 50)
                    }
                }
            }
        }
        handler.post(runnable)
    }

    private fun resetProgress() {
        currentProgress = 0
        binding.progressHorizontal.progress = 0
        binding.tvProgressStatus.text = "Progreso: 0%"
        binding.tvProgressStatus.setTextColor(resources.getColor(R.color.textSecondary))
        binding.btnStartProgress.isEnabled = true
    }

    private fun startCircularProgress() {
        progressHandler = Handler(Looper.getMainLooper())
        progressRunnable = object : Runnable {
            override fun run() {
                // Cambiar el texto cada 2 segundos
                val messages = listOf(
                    "Procesando datos...",
                    "Conectando con servidor...",
                    "Sincronizando información...",
                    "Finalizando proceso..."
                )
                val currentText = binding.tvCircularStatus.text.toString()
                val currentIndex = messages.indexOf(currentText)
                val nextIndex = (currentIndex + 1) % messages.size
                binding.tvCircularStatus.text = messages[nextIndex]

                progressHandler?.postDelayed(this, 2000)
            }
        }
        progressHandler?.post(progressRunnable!!)
    }

    private fun updateSharedText() {
        binding.tvSharedContent.text = MainActivity.sharedText
        binding.tvDynamic.text = "Texto actual: ${MainActivity.sharedText}"
    }

    private fun updateGlobalStatus() {
        val status = if (MainActivity.isFeatureEnabled) {
            "✅ Función especial ACTIVADA"
        } else {
            "❌ Función especial DESACTIVADA"
        }

        binding.tvGlobalStatus.text = status

        // Cambiar color del estado
        val color = if (MainActivity.isFeatureEnabled) {
            resources.getColor(android.R.color.holo_green_dark)
        } else {
            resources.getColor(android.R.color.holo_red_dark)
        }
        binding.tvGlobalStatus.setTextColor(color)
    }

    private fun updateAllInformation() {
        updateSharedText()
        updateGlobalStatus()

        // Actualizar contador de actualizaciones
        val currentCount = binding.tvUpdateCounter.text.toString()
            .substringAfterLast(": ")
            .toIntOrNull() ?: 0

        binding.tvUpdateCounter.text = "Actualizaciones: ${currentCount + 1}"
    }

    override fun onResume() {
        super.onResume()
        // Reanudar animaciones y actualizar información cuando regresamos al fragment
        if (progressRunnable != null) {
            startCircularProgress()
        }
        updateAllInformation()
    }

    override fun onPause() {
        super.onPause()
        // Pausar animaciones cuando salimos del fragment
        progressHandler?.removeCallbacks(progressRunnable!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressHandler?.removeCallbacks(progressRunnable!!)
        progressHandler = null
        progressRunnable = null
        _binding = null
    }
}