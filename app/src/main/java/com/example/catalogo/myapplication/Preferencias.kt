package com.example.catalogo.myapplication

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import android.content.Context
import com.google.gson.Gson
object Preferencias {

    fun guardarProductos(context: Context, lista: List<Producto>) {
        val prefs = context.getSharedPreferences("productos", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val json = Gson().toJson(lista)
        editor.putString("lista_productos", json)
        editor.apply()
    }

    fun obtenerProductos(context: Context): List<Producto> {
        val prefs = context.getSharedPreferences("productos", Context.MODE_PRIVATE)
        val json = prefs.getString("lista_productos", null)

        return if (json != null) {
            val tipo = object : TypeToken<List<Producto>>() {}.type
            Gson().fromJson(json, tipo)
        } else {
            emptyList()
        }
    }
}