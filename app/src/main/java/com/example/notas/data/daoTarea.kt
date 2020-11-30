package com.example.notas.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.notas_001.datos.Tarea
import org.jetbrains.annotations.NotNull
import java.sql.SQLException
import kotlin.Exception

class daoTarea(
        val contexto: Context
) {
    private val database: AdminSQL = AdminSQL(contexto)
    var base: SQLiteDatabase = database.writableDatabase

    @NotNull
    fun insert(tarea: Tarea): Boolean{
        val query: String =
            "INSERT INTO ${Tabla_tarea.nombre_tabla} ( ${Tabla_tarea.campo_titulo}, " +
                    "${Tabla_tarea.campo_descripcion}, ${Tabla_tarea.campo_fecha}) VALUES(" +
                    "'${tarea.titulo}', '${tarea.descripcion}', '${tarea.fecha}');"
        return try {
            base.execSQL(query)
            Toast.makeText(contexto,"Tarea agregada correctamente",Toast.LENGTH_SHORT).show()
            Toast.makeText(contexto,getAll()?.size.toString(),Toast.LENGTH_SHORT).show()
            true
        } catch (e: Exception){
            Toast.makeText(contexto,e.message +" ERROR", Toast.LENGTH_SHORT).show()
            false
        }
    }
    fun getAll(): ArrayList<Tarea>?{
        base = database.readableDatabase
        val lista: ArrayList<Tarea> = ArrayList()
        try{
            val query = "SELECT * FROM ${Tabla_tarea.nombre_tabla}";
            val cursor: Cursor = base.rawQuery(query,null)
            while (cursor.moveToNext()){
                lista.add(Tarea(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3)))
            }
            cursor.close()
        }catch (e: Exception){
            Toast.makeText(contexto,e.message,Toast.LENGTH_SHORT).show()
        }
        return lista
    }

    fun getOneById(id: Int): Tarea?{
        base = database.readableDatabase
        var cursor:Cursor? = null
        return try{
            val query = "SELECT * FROM ${Tabla_tarea.nombre_tabla} " +
                    "WHERE ${Tabla_tarea.campo_id} = '${id}'"
            cursor = base.rawQuery(query,null)
            Tarea(cursor.getInt(0),cursor.getString(2),
                    cursor.getString(3), cursor.getString(4))
        }catch (e: Exception){
            null
        }finally {
            cursor?.close()
        }
    }
}