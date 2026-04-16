package com.example.catalogo.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogo.myapplication.Preferencias.guardarProductos
import com.example.catalogo.myapplication.Preferencias.obtenerProductos

class MainActivity : AppCompatActivity() {

    private lateinit var lista: MutableList<Producto>
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCantidad = findViewById<EditText>(R.id.etCantidad)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)

        lista = Preferencias.obtenerProductos(this).toMutableList()

        adapter = ProductoAdapter(lista) { position ->
            lista.removeAt(position)
            adapter.notifyDataSetChanged()
            Preferencias.guardarProductos(this, lista)
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnAgregar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val cantidad = etCantidad.text.toString().toIntOrNull()
            val precio = etPrecio.text.toString().toDoubleOrNull()

            if (nombre.isNotEmpty() && cantidad != null && precio != null) {
                val nuevo = Producto(nombre, cantidad, precio)
                lista.add(nuevo)

                adapter.notifyDataSetChanged()
                Preferencias.guardarProductos(this, lista)

                etNombre.text.clear()
                etCantidad.text.clear()
                etPrecio.text.clear()
            }
        }
    }
}