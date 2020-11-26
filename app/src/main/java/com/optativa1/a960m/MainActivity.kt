package com.optativa1.a960m

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.optativa1.a960m.fragment.CalendarioFragment
import com.optativa1.a960m.fragment.InicioFragment
import com.optativa1.a960m.fragment.NuevaTareaFragment
import com.optativa1.a960m.fragment.TareasFragment
import com.optativa1.a960m.model.Tarea
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_nueva_tarea: FloatingActionButton
    private lateinit var database: FirebaseFirestore
    private lateinit var bottom_nav: BottomNavigationView
    private lateinit var modo: String
    private lateinit var NuevaTareaF: NuevaTareaFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView.menu.getItem(3).isEnabled = false

        btn_nueva_tarea = findViewById(R.id.btn_nueva_tarea)
        modo_nueva_tarea()
        btn_nueva_tarea.setOnClickListener(View.OnClickListener {
            crear_tarea()
        })
        openFragment(InicioFragment.newInstance())
        //BOTTOM NAV
        bottom_nav = findViewById(R.id.bottomNavigationView)
        bottom_nav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
				R.id.inicio -> {
					openFragment(InicioFragment.newInstance())
					true
				}
				R.id.calendario -> {
					openFragment(CalendarioFragment.newInstance())
					true
				}
				R.id.tareas -> {
					openFragment(TareasFragment.newInstance())
					true
				}
                else -> false
            }
        }
        iniciarDB()//DATABASE*/
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        modo_nueva_tarea()
        transaction.commit()
    }

    private fun iniciarDB() {
        FirebaseApp.initializeApp(this)
        database = FirebaseFirestore.getInstance()
    }
    private fun crear_tarea(){
        val fragment = NuevaTareaFragment.newInstance()
        NuevaTareaF = fragment
        openFragment(fragment)
        modo_crear_tarea()
    }
    private fun modo_crear_tarea() {
        modo = "crear_tarea"
        btn_nueva_tarea.setImageResource(R.drawable.ic_add_task)
        btn_nueva_tarea.setOnClickListener(View.OnClickListener {
            if(NuevaTareaF.crear(this,database)){
                openFragment(TareasFragment.newInstance())
            }
        })
    }

    private fun modo_nueva_tarea() {
        modo = "nueva_tarea"
        btn_nueva_tarea.setImageResource(R.drawable.ic_add)
        btn_nueva_tarea.setOnClickListener(View.OnClickListener {
            crear_tarea()
        })
    }

    override fun onClick(v: View?) {
        if (v != null) when (v.id) {
			R.id.btn_nueva_tarea ->  if (modo == "crear_tarea") NuevaTareaF.crear(this,database) else modo_crear_tarea()
        }
    }

}