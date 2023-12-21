package com.example.timerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_value)
        val buttonStart: Button = findViewById(R.id.btn_start)
        val buttonStop: Button = findViewById(R.id.btn_stop)
        val result: TextView = findViewById(R.id.txt_result)

        buttonStart.setOnClickListener {

            try {
                val number = editText.text.toString().toLong()

                timer = object : CountDownTimer(
                    number * 60 * 1000, // o tempo total que vai levar || minutos -> (faz * 60) passa para segundos -> (faz * 1000) passa para milisegundos
                    1000 // de quanto em quanto vai decrementando
                ) {
                    // A cada segundo (no caso o countDownInterval) vai executar essa função. O valor em milesegundos de quanto falta para acabar a contagem
                    override fun onTick(millisUntilFinished: Long) {

                        var seconds = millisUntilFinished / 1000 // -> preciso fazer o módulo disso pois se não vai pegar o valor original dos segundos pra mostrar
                        // na tela, por exemplo, 2 minutos mostraria 2:120
                        val minutes = seconds / 60

                        seconds = seconds % 60

                        result.text = String.format("%02d:%02d", minutes, seconds)
                    }

                    override fun onFinish() {
                        result.text = "O tempo acabou"
                    }

                }

                timer?.start()

            } catch(e: NumberFormatException) {
                Toast.makeText(this, "Digite um número no campo de texto!", Toast.LENGTH_SHORT).show()
            }

            buttonStop.setOnClickListener {
                timer?.cancel()
            }
        }
    }
}