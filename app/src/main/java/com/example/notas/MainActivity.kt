package com.example.notas
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toolBar: Toolbar
    private lateinit var navigationView: NavigationView
    //VARIABLES PARA CARGAR EL FRAGMENT
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Mostrar los componentes
        toolBar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)
        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigationView)
        //ESTABLECER EVENTO ONCLICK A NAVIGATIONVIEW
        navigationView.setNavigationItemSelectedListener(this)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()
        // CARGAR FRAGMENT PRINCIPAL
            fragmentManager = supportFragmentManager
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
            fragmentTransaction.add(R.id.contenedor_pequeño, fragment_ver_notas())
            fragmentTransaction.commit()


    }



    fun changeFragmentAddTask(obj: fragment_agregar_tarea){
        try {
            fragmentManager = supportFragmentManager
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
            fragmentTransaction.replace(R.id.contenedor_pequeño, obj)
            fragmentTransaction.commit()
        }catch (e: Exception){
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
        }
    }

    fun changeFragmentAddNote(obj: fragment_agregar_nota){
        try {
            fragmentManager = supportFragmentManager
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
            fragmentTransaction.replace(R.id.contenedor_pequeño, obj)
            fragmentTransaction.commit()
        }catch (e: Exception){
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    fun changeFragmentViewNote(obj: ver_nota_seleccionada){
        try{
            fragmentManager = supportFragmentManager
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
            fragmentTransaction.replace(R.id.contenedor_pequeño, obj)
            fragmentTransaction.commit()
        }catch (e: Exception){
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    fun changeFragmentViewImages(obj: fragment_ver_imagenes){
        try{
            fragmentManager = supportFragmentManager
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
            fragmentTransaction.replace(R.id.contenedor_pequeño, obj)
            fragmentTransaction.commit()
        }catch (e: Exception){
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.agregar_nota -> {
                if (findViewById<View?>(R.id.contenedor_pequeño) != null
                ) {
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
                    fragmentTransaction.replace(R.id.contenedor_pequeño, fragment_ver_notas())
                    fragmentTransaction.commit()
                }

            }
            R.id.agregar_tarea -> {
                fragmentManager = supportFragmentManager
                fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
                fragmentTransaction.replace(R.id.contenedor_pequeño, fragment_ver_tareas())
                fragmentTransaction.commit()
            }
        }
        return false
    }

}