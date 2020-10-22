package com.example.notas.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.notas.Nota
import java.lang.Exception

class daoNota(
   val contexto: Context, private val database: AdminSQL = AdminSQL(contexto),
    var base: SQLiteDatabase = database.writableDatabase
) {
    fun insert(nota: Nota): Boolean{
        val query: String =
            "INSERT INTO ${Tabla_nota().nombre_tabla} (" +
                    "${Tabla_nota().campo_nombre},${Tabla_nota().campo_descripcion}) " +
                    "VALUES('${nota.titulo}', '${nota.descripcion}' );"
        try{
            base.execSQL(query)
            Toast.makeText(contexto, "Nota agregada ", Toast.LENGTH_SHORT).show()
            return true
        } catch (e: Exception){
            Toast.makeText(contexto, e.message, Toast.LENGTH_SHORT).show()
            return false
        }finally {
            base.close()
        }
    }

    fun getAll(): ArrayList<Nota>?{
        base = database.readableDatabase
        val listaNotas: ArrayList<Nota> = ArrayList()
        try{
            val readQuery: String = "SELECT * FROM ${Tabla_nota().nombre_tabla}"
            val cursor: Cursor =  base.rawQuery(readQuery,null)
            while(cursor.moveToNext()){
                listaNotas.add(Nota(cursor.getInt(0),cursor.getString(1),cursor.getString(2)))
            }
            cursor.close()
        }catch (e: Exception){
            Toast.makeText(contexto, e.message, Toast.LENGTH_SHORT).show()
        }

        return listaNotas;
    }
}