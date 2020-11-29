package com.example.notas.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQL(
    context: Context?
) : SQLiteOpenHelper(context, "notasTareas", null, 1) {
    override fun onCreate(baseDatos: SQLiteDatabase?) {
        val query_nota: String = "CREATE TABLE ${Tabla_nota.nombre_tabla} (" +
                "${Tabla_nota.campo_id} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Tabla_nota.campo_nombre} VARCHAR(30)," +
                "${Tabla_nota.campo_descripcion} VARCHAR(200) );"
        baseDatos?.execSQL(query_nota)
        val query_foto: String = "CREATE TABLE ${Tabla_foto().nombre_tabla} (" +
                "${Tabla_foto().campo_id} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Tabla_foto().campo_idNota} INTEGER," +
                "${Tabla_foto().campo_descripcion} VARCHAR(200)," +
                "${Tabla_foto().campo_foto} BLOB," +
                "FOREIGN KEY(${Tabla_foto().campo_idNota}) REFERENCES ${Tabla_nota.nombre_tabla} (${Tabla_nota.campo_id}) );"
        baseDatos?.execSQL(query_foto)
        val queryTabla_RecursosNota: String = "CREATE TABLE ${Tabla_RecursosNota.nombre_tabla} ( " +
                "${Tabla_RecursosNota.campo_idNota} INTEGER PRIMARY KEY," +
                "${Tabla_RecursosNota.campo_uri} TEXT NOT NULL," +
                "${Tabla_RecursosNota.campo_tipo} VARCHAR(10)," +
                "foreign key(${Tabla_RecursosNota.campo_idNota}) references ${Tabla_nota.nombre_tabla}(${Tabla_nota.campo_id}) );"
        baseDatos?.execSQL(queryTabla_RecursosNota)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Tabla_foto().nombre_tabla}");
        db?.execSQL("DROP TABLE IF EXISTS ${Tabla_nota.nombre_tabla}");
        onCreate(db);
    }
}