package com.example.notas

import android.widget.ImageView

class Foto(var description: String, var Photo: ImageView) {

    var array: ArrayList<Foto>  = ArrayList()
    fun obtenerImagenes(foto: Foto): ArrayList<Foto>{
        array.add(foto)
        return array
    }
}