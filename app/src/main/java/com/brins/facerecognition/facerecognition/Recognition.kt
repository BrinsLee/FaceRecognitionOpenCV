package com.brins.facerecognition.facerecognition

import android.content.pm.PackageManager
import android.graphics.Camera
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_recognition.*
import org.opencv.android.*
import org.opencv.core.CvType
import org.opencv.core.Mat

class Recognition : AppCompatActivity() , CameraBridgeViewBase.CvCameraViewListener2{


    var TAG = "Recognition"
    var camera : Camera? = null
    internal var camerasurface: SurfaceView? = null
    internal var holder: SurfaceHolder? = null
    val FOCUS_MODE_MACRO = "macro"
    var javaCameraView : JavaCameraView? = null
    lateinit var mRgba : Mat
//    var aliveDetection: AliveDetection = AliveDetection("/sdcard/AliveDetection")

    internal var mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status){
                BaseLoaderCallback.SUCCESS -> javaCamera.enableView()

                else -> super.onManagerConnected(status)

            }

        }
    }

    companion object {
        private val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf("android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
        lateinit var warning: TextView

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognition)
        verifyStoragePermissions(this)
        warning = Instruction
//        javaCameraView = javaCamera
        javaCamera.visibility = SurfaceView.VISIBLE
        javaCamera.setCvCameraViewListener(this)



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

    override fun onPause() {
        super.onPause()
        if (javaCamera!= null)
            javaCamera.disableView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (javaCamera!= null)
            javaCamera.disableView()
    }

    override fun onResume() {
        super.onResume()
        if(OpenCVLoader.initDebug()){
            Log.i(TAG,"Opencv loaded successfully")
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }else{
            Log.i(TAG,"Opencv not load")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0
                    ,this,mLoaderCallback)
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

        mRgba = Mat(height ,width , CvType.CV_8UC4)
    }

    override fun onCameraViewStopped() {

        mRgba.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {

        mRgba = inputFrame!!.rgba()
        return  mRgba
    }
}
