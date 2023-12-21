package com.example.timerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null

    private lateinit var editText: EditText
    private lateinit var result: TextView
    private lateinit var buttonStart: Button
    private lateinit var buttonStop: Button
    private lateinit var buttonRedo: Button
    private lateinit var buttonRemove: Button

    private var milliseconds: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_value)
        result = findViewById(R.id.txt_result)
        buttonStart = findViewById(R.id.btn_start)
        buttonStop = findViewById(R.id.btn_stop)
        buttonRedo = findViewById(R.id.btn_redo)
        buttonRemove = findViewById(R.id.btn_remove)

        buttonStart.setOnClickListener {
            onTapStart()
        }

        buttonStop.setOnClickListener {
            onTapStop()
        }

        buttonRedo.setOnClickListener {
            onTapRedo()
        }

        buttonRemove.setOnClickListener {
            onTapRemove()
        }
    }

    // Hide Start Button
    // Hide Redo Button
    // Show Stop Button
    // Show Remove Button
    private fun onTapStart() {
        try {

            editText.isEnabled = false

            val number = editText.text.toString().toLong()

            val millisecondsTimer = (if(milliseconds != null) milliseconds else number * 60 * 1000)!!

            result.text = formatMinuteSecond(millisecondsTimer)

            if(milliseconds == null) {
                milliseconds = millisecondsTimer
            }

            // millisInFuture = o tempo total que vai levar || minutos -> (faz * 60) passa para segundos -> (faz * 1000) passa para milisegundos
            // countDownInterval = de quanto em quanto vai decrementando
            timer = object : CountDownTimer(
                millisecondsTimer,
                1000
            ) {
                // A cada segundo (no caso o countDownInterval) vai executar essa função. O valor em milesegundos de quanto falta para acabar a contagem
                override fun onTick(millisUntilFinished: Long) {

                    result.text = formatMinuteSecond(millisUntilFinished)

                    milliseconds = millisUntilFinished
                }

                override fun onFinish() {
                    result.text = "O tempo acabou"
                    milliseconds = null

                    buttonStart.visibility = View.VISIBLE
                    buttonRedo.visibility = View.GONE
                    buttonStop.visibility = View.GONE
                    buttonRemove.visibility = View.GONE

                    editText.isEnabled = true
                }

            }

            timer?.start()

            buttonStart.visibility = View.GONE
            buttonRedo.visibility = View.GONE
            buttonStop.visibility = View.VISIBLE
            buttonRemove.visibility = View.VISIBLE

        } catch(e: NumberFormatException) {
            Toast.makeText(this, "Digite um número no campo de texto!", Toast.LENGTH_SHORT).show()

            editText.isEnabled = true
        }
    }

    private fun formatMinuteSecond(milliseconds: Long) : String {
        var seconds = milliseconds / 1000 // -> preciso fazer o módulo disso pois se não vai pegar o valor original dos segundos pra mostrar
        // na tela, por exemplo, 2 minutos mostraria 2:120
        val minutes = seconds / 60

        seconds = seconds % 60

        return String.format("%02d:%02d", minutes, seconds)
    }

    // Hide Start Button
    // Show Redo Button
    // Hide Stop Button
    // Show Remove Button
    private fun onTapStop() {
        timer?.cancel()

        buttonStart.visibility = View.GONE
        buttonRedo.visibility = View.VISIBLE
        buttonStop.visibility = View.GONE
        buttonRemove.visibility = View.VISIBLE
    }

    // Hide Start Button
    // Hide Redo Button
    // Show Stop Button
    // Show Remove Button
    private fun onTapRedo() {

        // It will be the same from Start button
        onTapStart()
    }

    // Show Start Button
    // Hide Redo Button
    // Hide Stop Button
    // Hide Remove Button
    private fun onTapRemove() {

        timer?.cancel()

        milliseconds = null

        result.text = ""

        editText.isEnabled = true

        buttonStart.visibility = View.VISIBLE
        buttonRedo.visibility = View.GONE
        buttonStop.visibility = View.GONE
        buttonRemove.visibility = View.GONE
    }
}