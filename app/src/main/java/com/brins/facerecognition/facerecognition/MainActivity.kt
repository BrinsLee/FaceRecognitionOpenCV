package com.brins.facerecognition.facerecognition

import android.content.Intent
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader

class MainActivity : AppCompatActivity() {

    var s : CameraBridgeViewBase
    val TAG = "MainActivity"
    private var mExitTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = toolbarlayout.findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        button.setOnClickListener {
            val intent = Intent(this@MainActivity,Recognition::class.java)
            startActivity(intent)
        }

        if(OpenCVLoader.initDebug()){
            Log.i(TAG,"Opencv loaded successfully")
        }else{
            Log.i(TAG,"Opencv not load")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            //如果俩次按的时候的时间不小于2秒，就执行这个，否则就会退出程序
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(applicationContext, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}
