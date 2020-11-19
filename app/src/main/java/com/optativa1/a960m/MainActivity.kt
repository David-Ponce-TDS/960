package com.optativa1.a960m

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.optativa1.a960m.model.Tarea
import com.optativa1.a960m.model.Usuario
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
	private lateinit var btn_crear: MaterialButton
	private lateinit var et_nombre: EditText
	private lateinit var et_duracion: EditText
	private lateinit var et_descripcion: EditText
	private lateinit var et_variacion: EditText
	private lateinit var sw_atencion_completa: Switch
	private lateinit var database: FirebaseFirestore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//setContentView(R.layout.activity_main)
		setContentView(R.layout.nueva_tarea)
		instanciar()
		escuchar()
		iniciarDB()//DATABASE
	}

	private fun iniciarDB() {
		FirebaseApp.initializeApp(this)
		database = FirebaseFirestore.getInstance()
	}

	private fun instanciar() {
		btn_crear = findViewById(R.id.btn_crear)
		et_nombre = findViewById(R.id.et_nombre)
		et_duracion = findViewById(R.id.et_duracion)
		et_variacion = findViewById(R.id.et_variacion)
		et_descripcion = findViewById(R.id.et_descripcion)
		sw_atencion_completa = findViewById(R.id.sw_atencion_completa)
	}

	private fun escuchar() {
		btn_crear.setOnClickListener(this)
	}

	private fun validar():Boolean {
		var res:Boolean = true
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
				val variacion = et_variacion.text.toString()
		if (variacion == "") {
			et_variacion.setError("Obligatorio")
			res = false
		}
		val descripcion = et_descripcion.text.toString()
		if (descripcion == "") {
			et_descripcion.setError("Obligatorio")
			res = false
		}

		/*val sw_atencion_completa = sw_atencion_completa.text
		if (sw_atencion_completa == "") {
			sw_atencion_completa.setError("Obligatorio")
		}*/
		return res
	}

	private fun limpiar() {
		et_nombre.setText("")
		et_duracion.setText("")
		et_variacion.setText("")
		et_descripcion.setText("")
		//sw_atencion_completa.setText("")
	}

	override fun onClick(v: View?) {
		if (v != null) when (v.id) {
			R.id.btn_crear -> crear()
		}
	}

	private fun crear() {
		val nombre = et_nombre.text.toString()
		val duracion = et_duracion.text.toString()
		val variacion = et_variacion.text.toString()
		val descripcion = et_descripcion.text.toString()
		val atencion_completa = true
		if (!validar()) {
		} else {
			val t = Tarea(nombre,duracion,variacion,descripcion, atencion_completa)
			var DB = database.collection("tareas").document()
			DB.set(t.toMap()).addOnFailureListener { exception ->
				Log.d("Error", "No se pudo guardar tarea, exception: "+exception.message)
			}
			Toast.makeText(this, "Creado", Toast.LENGTH_SHORT).show()
			limpiar()
		}
	}
}