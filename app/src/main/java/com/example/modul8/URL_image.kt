package com.example.modul8

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class URL_image : AppCompatActivity() {
    var imageView: ImageView? = null
    var progressDialog: ProgressDialog? = null
    var loadedImage: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url_image)
        imageView = findViewById<View>(R.id.imageView) as ImageView
        downloadFile("https://himsiunair.com/assets/image/himsi.png")
    }

    fun downloadFile(url: String) {
        progressDialog = ProgressDialog.show(this, "", "Fetching Image...")
        object : Thread() {
            override fun run() {
                try {
                    val imageUrl = URL(url)
                    val conn = imageUrl.openConnection() as HttpsURLConnection
                    conn.connect()
                    loadedImage = BitmapFactory.decodeStream(conn.inputStream)
                } catch (e: IOException) {
                    Toast.makeText(
                        applicationContext,
                        "Unable to load image: " + e.message, Toast.LENGTH_LONG
                    ).show()
                    e.printStackTrace()
                }
                messageHandler.sendEmptyMessage(0)
            }
        }.start()
    }

    private val messageHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            imageView!!.setImageBitmap(loadedImage)
            progressDialog!!.dismiss()
        }
    }

}