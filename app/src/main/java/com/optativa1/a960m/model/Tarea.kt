package com.optativa1.a960m.model

import java.util.*

class Tarea(var nombre:String, var duracion:String, var variacion:String, var descripcion:String, var atencion_completa:Boolean) {

    fun toMap(): Map<String, Any?>{
       return hashMapOf(
            "nombre" to nombre,
            "duracion" to duracion,
            "variacion" to variacion,
            "descripcion" to descripcion,
						"atencion_completa" to atencion_completa
        )
    }
}