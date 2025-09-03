package com.example.uix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.uix.databinding.ActivityMainBinding
import com.example.uix.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Variables compartidas entre fragments
    companion object {
        var sharedText: String = "Texto inicial compartido"
        var isFeatureEnabled: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        // Cargar el primer fragment por defecto
        if (savedInstanceState == null) {
            loadFragment(TextFieldFragment())
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_textfield -> { loadFragment(TextFieldFragment()); true }
                R.id.nav_buttons -> { loadFragment(ButtonsFragment()); true }
                R.id.nav_selection -> { loadFragment(SelectionFragment()); true }
                R.id.nav_lists -> { loadFragment(ListsFragment()); true }
                R.id.nav_info -> { loadFragment(InfoFragment()); true }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
