package com.example.modul8


import androidx.appcompat.app.AppCompatActivity
import android.app.ProgressDialog
import android.os.Bundle

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button


class HandlerThread : AppCompatActivity() {
    private var fetchImage: Button? = null
    private var progressBar: Button? = null
    private var login: Button? = null
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_thread)
        fetchImage = findViewById<View>(R.id.btnChild) as Button
        fetchImage!!.setOnClickListener { view -> fetchData(view) }

        progressBar = findViewById<View>(R.id.btnProgressBar) as Button
        progressBar!!.setOnClickListener { view -> fetchData(view) }

        login = findViewById<View>(R.id.btnLogin) as Button
        login!!.setOnClickListener { view -> fetchData(view) }

    }

    protected fun fetchData(view: View) {
        val button = view as Button
        progressDialog = ProgressDialog.show(this, "", "Doing...")
        object : Thread() {
            override fun run() {
                try {
                    sleep(8000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                if (button.text == "Fetch Image") {
                    messageHandler.sendEmptyMessage(0)
                } else if(button.text == "Progress Bar") {
                    messageHandler2.sendEmptyMessage(0)
                }
                else{
                    messageHandler3.sendEmptyMessage(0)
                }
            }
        }.start()
    }

    private val messageHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            progressDialog!!.dismiss()
            val intent = Intent(this@HandlerThread, URL_image::class.java)
            startActivity(intent)
        }
    }

    private val messageHandler2: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            progressDialog!!.dismiss()
            val intent = Intent(this@HandlerThread,ProgressBar::class.java)
            startActivity(intent)
        }
    }

    private val messageHandler3: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            progressDialog!!.dismiss()
            val intent = Intent(this@HandlerThread,com.example.modul8.login::class.java)
            startActivity(intent)
        }
    }
}