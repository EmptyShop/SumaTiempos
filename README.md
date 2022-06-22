# SumaTiempos
App Android para sumar tiempos.
## De qué se trata esta app
Hice mi primera app operativa para practicar lo poco que sabía de Android, hace algunos años. Me gusta correr y como no me gusta descargar apps y llenar el teléfono de cosas que nunca voy a usar preferí hacer mi propia aplicación para llevar el registro de mis tiempos cuando voy a correr.

La operación es así: tengo 2 widgets de texto para capturar minutos y segundos, un botón para sumar los tiempos capturados, etiquetas de texto para el histórico y para el total acumulado.

## Cómo lo hice
Programé mi app para Android, usando Kotlin. Específicamente, quedó terminada con las siguientes versiones:
  * SDK target 29
  * SDK mínimo 15
  * Kotlin 1.3.50
  * Gradle 3.5.2

Construí una clase para que se haga cargo de los cálculos y formateo:
```sh
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
```
En la clase MainActivity sólo haga validaciones de captura y actualización de resultados. Lo que puedo destacar de esta parte es la validación que resultó en código muy compacto pero notablemente eficiente:
```sh
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
```
## Finalmente
Resultó muy útil y es una app muy ligera, a diferencia de las apps comerciales que tienen docenas de característica que no necesito. Y eso es todo con respecto a mi app Suma Tiempos.

*EmptyShop*
