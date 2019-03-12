package com.brins.facerecognition.facerecognition

import android.content.pm.PackageManager
import android.graphics.Camera
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_recognition.*

class Recognition : AppCompatActivity() {


    var camera : Camera? = null
    internal var camerasurface: SurfaceView? = null
    internal var holder: SurfaceHolder? = null
    val FOCUS_MODE_MACRO = "macro"
    var aliveDetection: AliveDetection = AliveDetection("/sdcard/AliveDetection")

    companion object {
        private val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
        lateinit var warning: TextView

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognition)
        verifyStoragePermissions(this)
        warning = Instruction


    }

    fun verifyStoragePermissions (activity: AppCompatActivity){

        try {
            if (ActivityCompat.checkSelfPermission(activity,"android.permission.WRITE_EXTERNAL_STORAGE") !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}
