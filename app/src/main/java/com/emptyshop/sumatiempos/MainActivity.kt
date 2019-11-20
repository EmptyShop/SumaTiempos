package com.emptyshop.sumatiempos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {
    // tiempoTotal almacena la suma de los tiempos agregados por el usuario
    private var tiempoTotal = Tiempo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AppBar personalizada
        setSupportActionBar(findViewById(R.id.laAppBar))

        /* Validadores para minutos y segundos: advierten cuando se ingresan
        * valores mayores o igual a 60. */

        val editTextMinutos = findViewById<EditText>(R.id.editTextMinutos)
        editTextMinutos.validate(getResources().getString(R.string.error_minutos)) {
                s -> s.isEmpty() || (s.isDigitsOnly() && s.toInt() < 60)
        }

        val editTextSegundos = findViewById<EditText>(R.id.editTextSegundos)
        editTextSegundos.validate(getResources().getString(R.string.error_segundos)) {
                s -> s.isEmpty() || (s.isDigitsOnly() && s.toInt() < 60)
        }
    }

    /* suma los valores capturados por el usuario al objeto tiempoTotal.
    * Este método es llamado por el botón "+". */
    fun sumaTiempo(view: View) : Boolean {
        val tiempo = Tiempo()
        val editTextHoras = findViewById<EditText>(R.id.editTextHoras)
        val editTextMinutos = findViewById<EditText>(R.id.editTextMinutos)
        val editTextSegundos = findViewById<EditText>(R.id.editTextSegundos)
        val editTextMilisegundos = findViewById<EditText>(R.id.editTextMilisegundos)

        /* Antes de sumar, validamos cada campo para asegurarnos de que sean
        * datos correctos */
        if (editTextHoras.text.isEmpty()) {
            editTextHoras.setText(editTextHoras.hint)
        }
        if (editTextMinutos.text.isEmpty()) {
            editTextMinutos.setText(editTextMinutos.hint)
        }
        if (editTextSegundos.text.isEmpty()) {
            editTextSegundos.setText(editTextSegundos.hint)
        }
        if (editTextMilisegundos.text.isEmpty()) {
            editTextMilisegundos.setText(editTextMilisegundos.hint)
        }

        if (
            !editTextMinutos.error.isNullOrEmpty() ||
            !editTextSegundos.error.isNullOrEmpty()
        ) {
            val toast = Toast.makeText(
                applicationContext,
                R.string.error_sumando, Toast.LENGTH_SHORT
            )
            toast.show()
            return false
        } else {
            // los datos son válidos, procedemos a sumar
            tiempo.horas = editTextHoras.text.toString().toInt()
            tiempo.minutos = editTextMinutos.text.toString().toInt()
            tiempo.segundos = editTextSegundos.text.toString().toInt()
            tiempo.milisegundos = editTextMilisegundos.text.toString().toInt()

            tiempoTotal.suma(tiempo)

            // actualizamos el resultado en la vista
            findViewById<TextView>(R.id.textViewTotal).text = tiempoTotal.toString()
            return true
        }
    }

    /* Realiza la suma y vacía los campos de texto.
    * Este método es llamado por el botón "TOTAL". */
    fun total(view: View) {
        if (sumaTiempo(view)) {
            findViewById<EditText>(R.id.editTextHoras).setText("")
            findViewById<EditText>(R.id.editTextMinutos).setText("")
            findViewById<EditText>(R.id.editTextSegundos).setText("")
            findViewById<EditText>(R.id.editTextMilisegundos).setText("")

            // reinicia el objeto de suma acumulada
            tiempoTotal = Tiempo()
        }

    }
}

/* Agrega un listener para validar campos de texto. */
fun EditText.afterTextChanged(afterTextChanged : (String) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

/* Validador para campos de texto. */
fun EditText.validate(message : String, validator : (String) -> Boolean){
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}