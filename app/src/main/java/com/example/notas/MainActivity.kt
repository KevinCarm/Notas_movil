package com.example.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener  {
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
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()
        // CARGAR FRAGMENT PRINCIPAL
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container,fragment_agregar_nota())
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.agregar_nota ->{
//                fragmentManager = supportFragmentManager
//                fragmentTransaction = fragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.container,fragment_registro())
//                fragmentTransaction.commit()
                Toast.makeText(this,"Agregando nota",Toast.LENGTH_SHORT).show()
            }
            R.id.agregar_tarea ->{
                Toast.makeText(this,"Agregando tarea",Toast.LENGTH_SHORT).show()
            }
           
        }
        return false
    }
}