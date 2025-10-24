package com.example.loginapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Layout principal
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#FCE4EC")) // fondo rosa claro
            setPadding(60, 100, 60, 100)
        }

        // Título bonito como mi perrito
        val titulo = TextView(this).apply {
            text = "🌸 gigiLogin 🌸"
            textSize = 28f
            setTextColor(Color.parseColor("#EC407A"))
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 60)
        }

        // Campo usuario
        val etUsuario = EditText(this).apply {
            hint = "Usuario"
            setHintTextColor(Color.parseColor("#AD1457"))
            setTextColor(Color.parseColor("#212121"))
            textSize = 16f
            setBackgroundColor(Color.WHITE)
            setPadding(30, 20, 30, 20)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 20, 0, 20) }
        }

        // Campo contraseña
        val etPassword = EditText(this).apply {
            hint = "Contraseña"
            setHintTextColor(Color.parseColor("#AD1457"))
            setTextColor(Color.parseColor("#212121"))
            textSize = 16f
            setBackgroundColor(Color.WHITE)
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setPadding(30, 20, 30, 20)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 20, 0, 10) }
        }

        // CheckBox para mostrar contraseña
        val cbMostrar = CheckBox(this).apply {
            text = "Mostrar contraseña"
            setTextColor(Color.parseColor("#AD1457"))
            textSize = 14f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(10, 0, 0, 30) }
        }

        cbMostrar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // Botón aceptar
        val btnAceptar = Button(this).apply {
            text = "Aceptar"
            textSize = 16f
            setBackgroundColor(Color.parseColor("#EC407A"))
            setTextColor(Color.WHITE)
            setPadding(40, 20, 40, 20)
        }

        // Botón cancelar
        val btnCancelar = Button(this).apply {
            text = "Cancelar"
            textSize = 16f
            setBackgroundColor(Color.parseColor("#F48FB1"))
            setTextColor(Color.WHITE)
            setPadding(40, 20, 40, 20)
        }

        // Agregar vistas al layout principal
        layout.addView(titulo)
        layout.addView(etUsuario)
        layout.addView(etPassword)
        layout.addView(cbMostrar)

        val botonesLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 0)
        }

        botonesLayout.addView(btnAceptar)
        botonesLayout.addView(btnCancelar)

        layout.addView(botonesLayout)

        setContentView(layout)

        // Lógica de botones
        btnAceptar.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            when {
                usuario.isEmpty() -> Toast.makeText(this, "Ingrese un usuario", Toast.LENGTH_SHORT).show()
                password.isEmpty() -> Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show()
                usuario == "gigiyoyopo" && password == "12345" -> {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, BienvenidaActivity::class.java)
                    startActivity(intent)
                }
                else -> Toast.makeText(this, "Datos incorrectos, inténtelo de nuevo", Toast.LENGTH_SHORT).show()
            }
        }


        btnCancelar.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Seguro que deseas cancelar y borrar los datos?")
                .setPositiveButton("Sí") { _, _ ->
                    etUsuario.text.clear()
                    etPassword.text.clear()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}