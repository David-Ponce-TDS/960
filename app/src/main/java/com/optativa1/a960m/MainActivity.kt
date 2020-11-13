package com.optativa1.a960m

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.optativa1.a960m.model.Usuario
import java.util.*


class MainActivity : AppCompatActivity(),  View.OnClickListener {
    private lateinit var btn_crear : MaterialButton
    private lateinit var et_nombre : EditText
    private lateinit var et_apellido : EditText
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instanciar()
        escuchar()
        iniciarDB()//DATABASE
    }
    private fun iniciarDB(){
        FirebaseApp.initializeApp(this)
        database = FirebaseFirestore.getInstance()
    }

    private fun instanciar() {
        btn_crear = findViewById(R.id.btn_crear)
        et_nombre = findViewById(R.id.et_nombre)
        et_apellido = findViewById(R.id.et_apellido)
    }
    private fun escuchar() {
        btn_crear.setOnClickListener(this)
    }

    private fun validar(): Boolean {
        val nombre = et_nombre.text.toString()
        val apellido = et_apellido.text.toString()
        if(nombre == ""){
            et_nombre.setError("Obligatorio")
            return false
        }
        if(apellido == ""){
            et_apellido.setError("Obligatorio")
            return false
        }
        return true
    }

    private fun limpiar(){
        et_nombre.setText("")
        et_apellido.setText("")
    }

    override fun onClick(v: View?) {
        if (v != null) when(v.id) {
            R.id.btn_crear -> crear()
        }
    }

    private fun crear(){
        val nombre = et_nombre.text.toString()
        val apellido = et_apellido.text.toString()
        val validationResult = validar()

        if(validationResult){
            val U =  Usuario(nombre, apellido)
            var DB = database.collection("usuarios").document()
            DB.set(U.toMap()).addOnFailureListener {
                exception -> Log.d("Gabablagblag",exception.message+"exception punto pito")
            }
            Toast.makeText(this,"Creado", Toast.LENGTH_SHORT).show()
            limpiar()
        }
    }
}