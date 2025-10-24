package com.example.loginapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import android.widget.LinearLayout
import android.view.Gravity
import android.widget.TextView

class BienvenidaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            setBackgroundColor(Color.parseColor("#FCE4EC"))
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            setPadding(40, 200, 40, 0)
        }

        val texto = TextView(this).apply {
            text = "Bienvenido al sida"
            textSize = 24f
            setTextColor(Color.parseColor("#AD1457"))
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 40)
        }

        val viewPager = ViewPager2(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                0.25f
            ).apply {
                setMargins(0, 10, 0, 1000)
            }
        }

        val imagenes = listOf(
            R.drawable.imagen1,
            R.drawable.imagen2,
            R.drawable.imagen3
        )

        val imagenesCiclicas = mutableListOf<Int>()
        imagenesCiclicas.add(imagenes.last())
        imagenesCiclicas.addAll(imagenes)
        imagenesCiclicas.add(imagenes.first())

        val adapter = CarruselAdapter(imagenesCiclicas)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(1, false)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    val itemCount = adapter.itemCount
                    val currentItem = viewPager.currentItem
                    when (currentItem) {
                        0 -> viewPager.setCurrentItem(itemCount - 2, false)
                        itemCount - 1 -> viewPager.setCurrentItem(1, false)
                    }
                }
            }
        })

        layout.addView(texto)
        layout.addView(viewPager)

        setContentView(layout)
    }
}