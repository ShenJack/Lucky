package com.example.shenjack.lucky

import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import org.jetbrains.anko.*

/**
 * sjjkk on 2017/12/3 in Beijing.
 */

open class BaseActivity : AppCompatActivity(){
    var alertDialog:AlertDialog? = null

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        alertDialog = alert{
            customView {
                include<RelativeLayout>(R.layout.recyclerview_loading_layout){
                    padding = dip(10)
                }
            }
        }.build() as AlertDialog

        initViews()
    }

    open fun getLayoutId(): Int {
        throw IllegalArgumentException("must override this method")
    }

    open fun initViews(){

    }


    fun showProgress(show:Boolean){
        if(show){
            alertDialog!!.show()
        }else{
            alertDialog!!.hide()
        }
    }
}
