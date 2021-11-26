package com.example.modul8

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button

import android.widget.EditText

class ProgressBar : AppCompatActivity() {
    var dialog: ProgressDialog? = null
    var increment = 0
    var maximum = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)
        val btnStart = findViewById<View>(R.id.btnStart) as Button
        btnStart.setOnClickListener { clicked() }
    }

    fun clicked() {
        val inc = findViewById<View>(R.id.increment) as EditText
        val max = findViewById<View>(R.id.maximum) as EditText
        increment = inc.text.toString().toInt()
        maximum = max.text.toString().toInt()
        dialog = ProgressDialog(this)
        dialog!!.setCancelable(true)
        dialog!!.setMessage("Loading...")
        dialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog!!.progress = 0
        dialog!!.max = maximum
        dialog!!.show()
        val background = Thread {
            try {
                while (dialog!!.progress <= dialog!!.max) {
                    Thread.sleep(500)
                    progressHandler.sendMessage(progressHandler.obtainMessage())
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        background.start()
    }

    private val progressHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            dialog!!.incrementProgressBy(increment)
            if (dialog!!.progress == dialog!!.max) {
                dialog!!.dismiss()
            }
        }
    }
}