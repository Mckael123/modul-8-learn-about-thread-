package com.example.modul8

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class login : AppCompatActivity() {
    private var inputLogin: Button? = null
    var dialog: ProgressDialog? = null
    var increment = 10
    var maximum = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inputLogin = findViewById<View>(R.id.submit_btn) as Button

        inputLogin!!.setOnClickListener { submit() }

    }

    fun submit() {
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
                    progressloader.sendMessage(progressloader.obtainMessage())
                }

            } catch (e: InterruptedException) {
                e.printStackTrace()

            }
        }
        background.start()

    }

    private val progressloader: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            dialog!!.incrementProgressBy(increment)
            if (dialog!!.progress == dialog!!.max) {
                var nginten = Intent(this@login, home::class.java)
                startActivity(nginten)
                dialog!!.dismiss()
            }

        }

    }
}