package com.zq.incrementalupdating

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.itheima.updatelib.PatchUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.File

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        update_btn.onClick {
            update()
        }

        version_name.text = packageManager.getPackageInfo(this.packageName,0).versionName
    }

    //incrementUpdating：oldApk + patch -> newApk
    private fun update() {
        val packageManager = packageManager
        val applicationInfo = packageManager.getApplicationInfo("com.zq.incrementalupdating", 0)

        //1.get the path of the old pak
        val sourceDir = applicationInfo.sourceDir

        //2.the path of patch file
        val patchFile = File(Environment.getExternalStorageDirectory(), "temp.patch")

        //3.set the path of merged apk
        val apkFile = File(Environment.getExternalStorageDirectory(), "new.apk")

        //4.merge action：jni

        doAsync {

            val result = PatchUtil.patch(sourceDir, apkFile.absolutePath, patchFile.absolutePath)
            if (result == 0) {
                //complete merge
                runOnUiThread {

                }
            }
        }

    }
}
