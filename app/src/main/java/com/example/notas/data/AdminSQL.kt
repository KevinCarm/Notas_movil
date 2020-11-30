package com.example.notas.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQL(
    context: Context?
) : SQLiteOpenHelper(context, "notasTareas", null, 1) {
    override fun onCreate(baseDatos: SQLiteDatabase?) {
        val query_tarea: String? = "CREATE TABLE ${Tabla_tarea.nombre_tabla} (" +
                "${Tabla_tarea.campo_id} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Tabla_tarea.campo_titulo} VARCHAR(30)," +
                "${Tabla_tarea.campo_descripcion} VARCHAR(100)," +
                "${Tabla_tarea.campo_fecha} VARCHAR(30));"
        baseDatos?.execSQL(query_tarea)
        val query_nota: String = "CREATE TABLE ${Tabla_nota.nombre_tabla} (" +
                "${Tabla_nota.campo_id} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Tabla_nota.campo_nombre} VARCHAR(30)," +
                "${Tabla_nota.campo_descripcion} VARCHAR(200) );"
        baseDatos?.execSQL(query_nota)
        val queryTabla_RecursosNota: String = "CREATE TABLE ${Tabla_RecursosNota.nombre_tabla} ( " +
                "${Tabla_RecursosNota.campo_idNota} INTEGER," +
                "${Tabla_RecursosNota.campo_uri} TEXT NOT NULL," +
                "${Tabla_RecursosNota.campo_tipo} VARCHAR(10)," +
                "foreign key(${Tabla_RecursosNota.campo_idNota}) references ${Tabla_nota.nombre_tabla}(${Tabla_nota.campo_id}) );"
        baseDatos?.execSQL(queryTabla_RecursosNota)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Tabla_nota.nombre_tabla}");
        db?.execSQL("DROP TABLE IF EXISTS ${Tabla_tarea.nombre_tabla}");
        onCreate(db);
    }
}