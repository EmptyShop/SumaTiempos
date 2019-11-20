package com.emptyshop.sumatiempos

import java.lang.StringBuilder

/* Tiempo : almacena un objeto de tiempo.
*
* Miembros:
*
* horas : Int
* minutos : Int
* segundos : Int
* milisegundos : Int
*
* Métodos:
*
* Tiempo() : Tiempo
* suma() : Tiempo
* toString() : String
* */
class Tiempo (_horas : Int = 0, _minutos : Int = 0, _segundos : Int = 0, _milisegundos : Int = 0){
    /* Constructor : Acepta 4 parámetros opcionales:
    _horas : Int, _minutos : Int, _segundos : Int, _milisegundos : Int */
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

    /* suma() : Suma 2 objetos de tipo Tiempo y devuelve el resultado.
    Parámetros:
    sumando : Tiempo
     */
    fun suma(sumando : Tiempo) : Tiempo {
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

    /* toString() : Despliega el valor del objeto en formato h mm ss .ms */
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