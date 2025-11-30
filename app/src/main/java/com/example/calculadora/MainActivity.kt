package com.example.calculadora
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {


    private var tvPantalla: TextView? = null
    private var operacionPendiente = ""
    private var numeroAnterior = 0.0
    private var esNuevaOperacion = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPantalla = findViewById(R.id.tvPantalla)

        val botonesNumeros = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in botonesNumeros) {
            findViewById<Button>(id).setOnClickListener { v ->
                val boton = v as Button
                agregarNumero(boton.text.toString())
            }
        }

        val botonesOperaciones = listOf(
            R.id.btnSuma, R.id.btnResta, R.id.btnMulti, R.id.btnDiv
        )

        for (id in botonesOperaciones) {
            findViewById<Button>(id).setOnClickListener { v ->
                val boton = v as Button
                prepararOperacion(boton.text.toString())
            }
        }

        findViewById<Button>(R.id.btnIgual).setOnClickListener {
            calcularResultado()
        }


        findViewById<Button>(R.id.btnC).setOnClickListener {
            tvPantalla?.text = "0"
            numeroAnterior = 0.0
            operacionPendiente = ""
            esNuevaOperacion = true
        }
        val btnSin = findViewById<Button>(R.id.btnSin)
        btnSin?.setOnClickListener { calcularCientifica("SIN") }

        val btnCos = findViewById<Button>(R.id.btnCos)
        btnCos?.setOnClickListener { calcularCientifica("COS") }

        val btnTan = findViewById<Button>(R.id.btnTan)
        btnTan?.setOnClickListener { calcularCientifica("TAN") }

        val btnRaiz = findViewById<Button>(R.id.btnRaiz)
        btnRaiz?.setOnClickListener { calcularCientifica("SQRT") }
    }

    private fun agregarNumero(numero: String) {
        if (esNuevaOperacion) {
            tvPantalla?.text = numero
            esNuevaOperacion = false
        } else {
            tvPantalla?.append(numero)
        }
    }

    private fun prepararOperacion(operacion: String) {
        numeroAnterior = tvPantalla?.text.toString().toDouble()
        operacionPendiente = operacion
        esNuevaOperacion = true
    }

    private fun calcularResultado() {
        val numeroActual = tvPantalla?.text.toString().toDouble()
        var resultado = 0.0

        when (operacionPendiente) {
            "+" -> resultado = numeroAnterior + numeroActual
            "-" -> resultado = numeroAnterior - numeroActual
            "X" -> resultado = numeroAnterior * numeroActual
            "/" -> resultado = numeroAnterior / numeroActual
            else -> resultado = numeroActual
        }

        tvPantalla?.text = resultado.toString()
        esNuevaOperacion = true
    }
    private fun calcularCientifica(funcion: String) {
        val numeroActual = tvPantalla?.text.toString().toDouble()
        var resultado = 0.0

        when (funcion) {
            "SIN" -> resultado = sin(numeroActual)
            "COS" -> resultado = cos(numeroActual)
            "TAN" -> resultado = tan(numeroActual)
            "SQRT" -> resultado = sqrt(numeroActual)
        }

        tvPantalla?.text = resultado.toString()
        esNuevaOperacion = true
    }
}