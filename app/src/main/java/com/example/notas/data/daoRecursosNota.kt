package com.example.notas.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import java.util.ArrayList

class daoRecursosNota(
        val contexto: Context
) {
    private val database: AdminSQL = AdminSQL(contexto)
    var base: SQLiteDatabase = database.writableDatabase

    fun insert(recursosNota: RecursosNota): Boolean {
        val query: String = "INSERT INTO ${Tabla_RecursosNota.nombre_tabla} (" +
                "${Tabla_RecursosNota.campo_idNota},${Tabla_RecursosNota.campo_uri}," +
                "${Tabla_RecursosNota.campo_tipo} ) VALUES(" +
                "'${daoNota(contexto).getLastId()}','${recursosNota.uri}','${recursosNota.tipo}' );"
        return try {
            base.execSQL(query)
            Toast.makeText(contexto, "Agregado correctamente", Toast.LENGTH_SHORT).show()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAll(): ArrayList<RecursosNota> {
        base = database.readableDatabase
        val list: ArrayList<RecursosNota> = ArrayList()
        try {
            val query: String = "SELECT * FROM ${Tabla_RecursosNota.nombre_tabla}"
            val cursor: Cursor = base.rawQuery(query, null)
            while (cursor.moveToNext()) {
                list.add(RecursosNota(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ))
            }
            cursor.close()
        } catch (e: Exception) {
            Toast.makeText(contexto,
                    e.message, Toast.LENGTH_SHORT).show()
        }
        return list
    }
}