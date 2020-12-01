package com.example.notas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.data.daoNota
import com.example.notas.data.daoTarea
import com.example.notas_001.datos.Tarea

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_ver_tareas.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_ver_tareas : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainActivity: MainActivity
    private lateinit var floating_button: com.google.android.material.floatingactionbutton.FloatingActionButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
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
        val vista: View = inflater.inflate(R.layout.fragment_ver_tareas, container, false)
        recyclerView = vista.findViewById(R.id.recycle_tareas)
        layoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = layoutManager
        val adapter = context?.let { daoTarea(it).getAll()?.let { it1 -> adaptador_tarea(it, it1) } }
        adapter?.setOnclickListener(View.OnClickListener {
            val id = recyclerView.getChildAdapterPosition(it) + 1
            val bundle: Bundle = Bundle()
            bundle.putInt("idTarea",id)
            val tarea = fragment_ver_tarea_selecionada()
            tarea.arguments = bundle
            mainActivity.changeFragmentViewTask(tarea)
        })
        recyclerView.adapter = adapter
        return vista
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_ver_tareas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_ver_tareas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}