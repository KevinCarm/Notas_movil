package com.example.notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [mostrar_imagenes.newInstance] factory method to
 * create an instance of this fragment.
 */
class mostrar_imagenes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var vista: View
    lateinit var recyclerView: RecyclerView
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val vista: View = inflater.inflate(R.layout.fragment_mostrar_imagenes, container, false)
        recyclerView = vista.findViewById(R.id.recycle_mostrar_imagenes)
        layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        if(arguments != null){
            val sal = arguments!!.getParcelableArrayList<Foto>("list") as ArrayList<Foto>
            val adapter = context?.let { adaptador_imagenes(it,sal) }
            recyclerView.adapter = adapter
        }
        else{
            Toast.makeText(context,"Vacio",Toast.LENGTH_LONG).show()
        }
        // Inflate the layout for this fragment
        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment mostrar_imagenes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            mostrar_imagenes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}