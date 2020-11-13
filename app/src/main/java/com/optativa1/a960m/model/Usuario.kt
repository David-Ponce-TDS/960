package com.optativa1.a960m.model

import java.util.*

class Usuario(var nombre:String, var apellido:String) {

    fun toMap(): Map<String, Any?>{
       return hashMapOf(
            "uid" to UUID.randomUUID().toString(),
            "nombre" to nombre,
            "apellido" to apellido
        )
    }
}