package com.example.notas.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notas.Tabla_nota

class AdminSQL(
    context: Context?
) : SQLiteOpenHelper(context, "notasTareas", null, 1) {
    override fun onCreate(baseDatos: SQLiteDatabase?) {
        val query: String = "CREATE TABLE ${Tabla_nota().nombre_tabla} (" +
                "${Tabla_nota().campo_id} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Tabla_nota().campo_nombre} VARCHAR(30)," +
                "${Tabla_nota().campo_descripcion} VARCHAR(200) );"
        baseDatos?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}