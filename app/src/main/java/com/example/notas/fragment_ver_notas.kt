package com.example.notas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.data.daoNota

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_ver_notas.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_ver_notas : Fragment(),
    PopupMenu.OnMenuItemClickListener {
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
        val vista = inflater.inflate(R.layout.fragment_ver_notas, container, false)
        floating_button = vista.findViewById(R.id.float_ver_notas)
        popupMenu()
        recyclerView = vista.findViewById(R.id.recycle_notas)
        layoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = layoutManager
        val adapter = context?.let { daoNota(it).getAll()?.let { it1 -> adaptador_notas(it, it1) } }
        adapter?.setOnclickListener(View.OnClickListener {
            val id: Int = recyclerView.getChildLayoutPosition(it) + 1
            val bundle = Bundle()
            bundle.putInt("idNota",id)
            val viewNota = ver_nota_seleccionada()
            viewNota.arguments = bundle
            mainActivity.changeFragmentViewNote(viewNota)
        })
        recyclerView.adapter = adapter
        return vista
    }

    private fun popupMenu(): Unit {
        floating_button.setOnClickListener {
            val popup: PopupMenu = PopupMenu(activity, floating_button)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.popup_menu_agregar)
            popup.show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_ver_notas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_ver_notas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_agregar_nota -> {
                mainActivity.changeFragmentAddNote(fragment_agregar_nota())
                return true
            }
            R.id.menu_agregar_tarea -> {
                mainActivity.changeFragmentAddTask(fragment_agregar_tarea())
                return true
            }
        }
        return false
    }
}