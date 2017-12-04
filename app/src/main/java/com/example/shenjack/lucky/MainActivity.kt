package com.example.shenjack.lucky

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import org.jetbrains.anko.*

class MainActivity : BaseActivity(){



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home ->{
                switchRooms()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchFriends()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> return@OnNavigationItemSelectedListener true
        }
        false
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        super.initViews()
        val navigation = find<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    protected fun switchRooms() {
        supportFragmentManager.beginTransaction().replace(R.id.content,RoomsFragment.newInstance(1)).commit()
    }

    protected fun switchFriends() {
        supportFragmentManager.beginTransaction().replace(R.id.content,RoomsFragment.newInstance(1)).commit()
    }



}
