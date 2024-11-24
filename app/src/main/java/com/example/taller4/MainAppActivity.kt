package com.example.taller4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider

class MainAppActivity : FragmentActivity() {
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        // Cargar el fragmento de lista al iniciar
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragmentContainer, ItemListFragment())
            }
        }
    }

    fun navigateToDetailFragment() {
        // Cambiar al fragmento de detalles
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, ItemDetailFragment())
            addToBackStack(null)
        }
    }
}
