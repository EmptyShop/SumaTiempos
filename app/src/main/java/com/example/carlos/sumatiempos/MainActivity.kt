package com.example.carlos.sumatiempos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var tiempoTotal = Tiempo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextMinutos = findViewById<EditText>(R.id.editTextMinutos)
        editTextMinutos.validate("Minutos < 60") {
                s -> s.isEmpty() || (s.isDigitsOnly() && s.toInt() < 60)
        }

        val editTextSegundos = findViewById<EditText>(R.id.editTextSegundos)
        editTextSegundos.validate("Segundos < 60") {
                s -> s.isEmpty() || (s.isDigitsOnly() && s.toInt() < 60)
        }
    }

    fun sumaTiempo(view : View) : Boolean{
        var tiempo = Tiempo()
        var editTextHoras = findViewById<EditText>(R.id.editTextHoras)
        var editTextMinutos = findViewById<EditText>(R.id.editTextMinutos)
        var editTextSegundos = findViewById<EditText>(R.id.editTextSegundos)
        var editTextMilisegundos = findViewById<EditText>(R.id.editTextMilisegundos)

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
        ){
            val toast = Toast.makeText(applicationContext, R.string.error_sumando, Toast.LENGTH_SHORT)
            toast.show()
            return false
        }
        else {
            tiempo.horas = editTextHoras.text.toString().toInt()
            tiempo.minutos = editTextMinutos.text.toString().toInt()
            tiempo.segundos = editTextSegundos.text.toString().toInt()
            tiempo.milisegundos = editTextMilisegundos.text.toString().toInt()

            tiempoTotal.suma(tiempo)

            findViewById<TextView>(R.id.textViewTotal).text = tiempoTotal.toString()
            return true
        }
    }

    fun total(view: View) {
        if (sumaTiempo(view)) {
            findViewById<EditText>(R.id.editTextHoras).setText("")
            findViewById<EditText>(R.id.editTextMinutos).setText("")
            findViewById<EditText>(R.id.editTextSegundos).setText("")
            findViewById<EditText>(R.id.editTextMilisegundos).setText("")

            tiempoTotal = Tiempo()
        }

    }
}

fun EditText.afterTextChanged(afterTextChanged : (String) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

fun EditText.validate(message : String, validator : (String) -> Boolean){
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}