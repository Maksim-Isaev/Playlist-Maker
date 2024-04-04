package com.practicum.playlistmaker
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)

        // Реализация нажатия на первую кнопку через анонимный класс
        button1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Button 1 - Анонимный класс", Toast.LENGTH_SHORT).show()
            }
        })

        // Реализация нажатия на вторую кнопку через лямбда-выражение
        button2.setOnClickListener {
            Toast.makeText(this@MainActivity, "Button 2 - Лямбда-выражение", Toast.LENGTH_SHORT).show()
        }

        // Реализация нажатия на третью кнопку через анонимный класс
        button3.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Button 3 - Анонимный класс", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
