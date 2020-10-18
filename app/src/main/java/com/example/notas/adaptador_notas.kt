package com.example.notas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adaptador_notas(
    var contexto: Context,
    val listaNotas: ArrayList<Nota>
): RecyclerView.Adapter<adaptador_notas.ViewHolder>() {


    var inflador: LayoutInflater =
        contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var titulo: TextView
        var descripcion: TextView
        init {
            this.titulo = itemView.findViewById<View>(R.id.titulo_mostrar) as TextView
            this.descripcion = itemView.findViewById<View>(R.id.descripcion_mostrar) as TextView
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptador_notas.ViewHolder {
        val view: View = inflador.inflate(R.layout.item_recycle, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: adaptador_notas.ViewHolder, position: Int) {
        val nota: Nota = listaNotas[position]
        holder.titulo.setText(nota.titulo)
        holder.descripcion.setText(nota.descripcion)
    }

    override fun getItemCount(): Int {
        return listaNotas.size
    }

}