package com.example.carlos.sumatiempos

import java.lang.StringBuilder

class Tiempo (_horas : Int = 0, _minutos : Int = 0, _segundos : Int = 0, _milisegundos : Int = 0){
    var horas : Int = _horas
    var minutos : Int = _minutos
    set (value){
        field = if (value < 60) value else 0
    }
    var segundos : Int = _segundos
    set(value){
        field = if (value < 60) value else 0
    }
    var milisegundos : Int = _milisegundos
    set(value){
        field = if (value < 1000) value else 0
    }

    fun suma(sumando : Tiempo) : Tiempo{
        val ms = this.milisegundos + sumando.milisegundos
        this.milisegundos = ms % 1000
        this.segundos += ms.div(1000)

        val s = this.segundos + sumando.segundos
        this.segundos = s % 60
        this.minutos += s.div(60)

        val m = this.minutos + sumando.minutos
        this.minutos = m % 60
        this.horas += m.div(60)

        val h = this.horas + sumando.horas
        this.horas = h

        return this
    }

    override fun toString() : String{
        val s = StringBuilder()
        s.append(this.horas.toString())
            .append("h ")
            .append(this.minutos.toString().padStart(2, '0'))
            .append("m ")
            .append(this.segundos.toString().padStart(2,'0'))
            .append(".")
            .append(this.milisegundos.toString().padStart(3,'0'))
            .append("s")

        return s.toString()
    }
}