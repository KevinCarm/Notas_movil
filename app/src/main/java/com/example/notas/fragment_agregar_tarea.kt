package com.example.notas

import android.app.Activity
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.notas.data.RecursosNota
import com.example.notas.data.RecursosTarea
import com.example.notas.data.daoRecursosTarea
import com.example.notas.data.daoTarea
import com.example.notas_001.datos.Tarea
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_agregar_tarea.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_agregar_tarea : Fragment(),
    PopupMenu.OnMenuItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var btnAccept: Button
    private lateinit var floating: com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var calendar: ImageView
    private lateinit var time: ImageView
    private var fechaActual = ""
    private var fecha_seleccionada = ""
    private var hora_seleccionada: String? = null
    private lateinit var listaRecursos: ArrayList<RecursosTarea>

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
        // Inflate the layout for this fragment
        actualTime()
        val vista: View = inflater.inflate(R.layout.fragment_agregar_tarea, container, false)
        initialize(vista)
        return vista
    }

    private fun initialize(root: View) {
        listaRecursos = ArrayList<RecursosTarea>()
        title = root.findViewById(R.id.txtTitleTask)
        description = root.findViewById(R.id.txtDescriptionTask)
        btnAccept = root.findViewById(R.id.btnAddTask)
        floating = root.findViewById(R.id.floatingTask)
        calendar = root.findViewById(R.id.imageCalendar)
        time = root.findViewById(R.id.imageTime)
        btnAccept.setOnClickListener {
            addTaskToDataBase()
            addResourcesToDB()
        }
        floating.setOnClickListener {
            val popup: PopupMenu = PopupMenu(
                context, floating
            )
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                try {
                    when (item.itemId) {
                        R.id.item_add_from_gallery -> {
                            takeImageFromGallery()
                            return@OnMenuItemClickListener true
                        }
                        R.id.item_add_from_camera -> {

                            return@OnMenuItemClickListener true
                        }
                        R.id.item_save_photo -> {

                            return@OnMenuItemClickListener true
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
                true
            })
            popup.show()
        }
        calendar.setOnClickListener {
            showDatePickerDialog()
        }
        time.setOnClickListener {
            showTimePicked()
        }
    }

    private fun addTaskToDataBase() {
        try {
            val fecha_final = "$fecha_seleccionada $hora_seleccionada"
            val tarea = Tarea(title.text.toString(), description.text.toString(), fecha_final)
            context?.let { daoTarea(it).insert(tarea) }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addResourcesToDB() {
        try {
            Toast.makeText(context, listaRecursos.size.toString(), Toast.LENGTH_SHORT).show()
            listaRecursos.forEach { item ->
                context?.let {
                    daoRecursosTarea(it)
                        .insert(item)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualTime() {
        val dfDate_day = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        val c = Calendar.getInstance()
        fechaActual = dfDate_day.format(c.time)
    }

    private fun showTimePicked() {
        val getDate = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            context,
            OnTimeSetListener { view, hourOfDay, minute ->
                getDate[Calendar.HOUR_OF_DAY] = hourOfDay
                getDate[Calendar.MINUTE] = minute
                val timeformat = SimpleDateFormat("hh:mm a")
                hora_seleccionada = timeformat.format(getDate.time)
            }, getDate[Calendar.HOUR_OF_DAY], getDate[Calendar.MINUTE], false
        )
        timePickerDialog.show()
    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance { datePicker, year, month, day -> // +1 because January is zero
                fecha_seleccionada = day.toString() + "/" + (month + 1) + "/" + year
            }
        activity?.supportFragmentManager?.let { newFragment.show(it, "datePicker") }
    }

    private fun takeImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Seleccione una imagen"),
            GALLERY_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            listaRecursos.add(RecursosTarea(uri.toString(), "image"))
            Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val CAMERA_REQUEST = 123
        private const val GALLERY_REQUEST = 124
        private const val PERMISSION_WRITTE_STORAGE = 125

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_agregar_tarea.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_agregar_tarea().apply {
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