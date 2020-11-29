package com.example.notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import com.example.notas.data.daoNota
import kotlinx.android.synthetic.main.fragment_ver_nota_seleccionada.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ver_nota_seleccionada.newInstance] factory method to
 * create an instance of this fragment.
 */
class ver_nota_seleccionada : Fragment(),
    PopupMenu.OnMenuItemClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var btnAccept: Button
    private lateinit var floating: com.google.android.material.floatingactionbutton.FloatingActionButton

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
        val bundle: Bundle? = arguments
        val id: Int? = bundle?.getInt("idNota")
        val vista = inflater.inflate(R.layout.fragment_ver_nota_seleccionada, container, false)
        if (id != null) {
            initialize(vista,id)
        }
        return vista
    }

    private fun initialize(root: View, id: Int){
        floating = root.findViewById(R.id.floatingShowResourcesNote)
        title = root.findViewById(R.id.txtViewTitleNote)
        description = root.findViewById(R.id.txtViewDescriptionNote)
        btnAccept = root.findViewById(R.id.btnViewAcceptNote)
        btnAccept.setOnClickListener {

        }
        floating.setOnClickListener {
            val popup: PopupMenu = PopupMenu(activity, floating)
            popup.menuInflater.inflate(R.menu.popup_menu_vista, popup.menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_view_images -> {
                        Toast.makeText(context,"Ver imagen",Toast.LENGTH_SHORT).show()
                        return@OnMenuItemClickListener true
                    }
                    R.id.item_add_from_camera -> {

                        return@OnMenuItemClickListener true
                    }
                    R.id.item_save_photo -> {

                        return@OnMenuItemClickListener true
                    }
                }
                true
            })
            popup.show()
        }
        val nota = context?.let { daoNota(it).getOneById(id) }
        title.setText(nota?.titulo)
        description.setText(nota?.descripcion)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ver_nota_seleccionada.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ver_nota_seleccionada().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }
}