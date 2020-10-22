package com.example.notas

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.view.drawToBitmap

class Foto(var idFoto: Int, var idNota: Int,var description: String, var Photo: Bitmap) {
    constructor(description: String,Photo: Bitmap):this(0,0,description,Photo)
}