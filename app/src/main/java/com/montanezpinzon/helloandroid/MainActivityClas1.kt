package com.montanezpinzon.helloandroid

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.montanezpinzon.helloandroid.databinding.ActivityMainBinding

class MainActivityClas1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main_clase1)
        // Referenciar elementos de la interfaz
        val textView = findViewById<TextView>(R.id.textView)
        val btnSaludar = findViewById<Button>(R.id.btnSaludar)
        // Variable contador
        var contador = 0
        // Asignar acción al botón
        btnSaludar.setOnClickListener {
            contador++
            textView.text = "Has hecho clic $contador veces"

            // Mostrar mensaje emergente
            Toast.makeText(
                this,
                "¡Botón presionado!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}