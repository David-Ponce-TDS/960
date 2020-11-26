package com.optativa1.a960m.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.optativa1.a960m.R
import com.optativa1.a960m.model.Tarea


class NuevaTareaFragment : Fragment() {
    companion object {
        fun newInstance(): NuevaTareaFragment = NuevaTareaFragment()
    }

    private lateinit var et_nombre: EditText
    private lateinit var et_duracion: EditText
    private lateinit var et_descripcion: EditText
    private lateinit var et_variacion: EditText
    private lateinit var sw_atencion_completa: Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.nueva_tarea, container, false)
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_nombre = view.findViewById(R.id.et_nombre)
        et_duracion = view.findViewById(R.id.et_duracion)
        et_variacion = view.findViewById(R.id.et_variacion)
        et_descripcion = view.findViewById(R.id.et_descripcion)
        sw_atencion_completa = view.findViewById(R.id.sw_atencion_completa)
    }
    public fun crear(context: Context,database: FirebaseFirestore): Boolean {
        val nombre = et_nombre.text.toString()
        val duracion = et_duracion.text.toString()
        val variacion = et_variacion.text.toString()
        val descripcion = et_descripcion.text.toString()
        val atencion_completa = true
        if (!validar()) {
            return false
        } else {
            val t = Tarea(nombre, duracion, variacion, descripcion, atencion_completa)
            var DB = database.collection("tareas").document()
            DB.set(t.toMap()).addOnFailureListener { exception ->
                Log.d("Error", "No se pudo guardar tarea, exception: " + exception.message)
            }
            Toast.makeText(context, "Creado", Toast.LENGTH_SHORT).show()
            limpiar()
            return true
        }
    }

    private fun validar(): Boolean {
        var res: Boolean = true
        val nombre = et_nombre.text.toString()
        if (nombre == "") {
            et_nombre.setError("Obligatorio")
            res = false
        }
        val duracion = et_duracion.text.toString()
        if (duracion == "") {
            et_duracion.setError("Obligatorio")
            res = false
        }
        return res
    }

    private fun limpiar() {
        et_nombre.setText("")
        et_duracion.setText("")
        et_variacion.setText("")
        et_descripcion.setText("")
        //sw_atencion_completa.setText("")
    }


}