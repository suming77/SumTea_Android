package com.sum.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.AppExit
import com.sum.main.databinding.ActivityMainBinding
import com.sum.stater.dispatcher.DelayInitDispatcher
import com.sum.stater.inittasks.InitTaskA
import com.sum.stater.inittasks.InitTaskB

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    //延迟初始化执行的任务 此处的时机应该是在页面渲染首帧后执行 这里暂时放在onWindowFocusChanged()
    //利用 IDLEHandler 特性，主线程空闲时机执行
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //延迟执行启动器
        DelayInitDispatcher().addTask(InitTaskA())
                .addTask(InitTaskB())
                .start()
    }

    override fun onBackPressed() {
//        if (mCurFragment?.onBackPressed() == true) {
//            return
//        }
        AppExit.onBackPressed(
            this,
            { TipsToast.showTips(getString(R.string.base_app_exit_one_more_press)) }
        )
    }


}