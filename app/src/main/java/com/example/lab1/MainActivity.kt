package com.example.lab1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



//    public fun showFragment(fragmentClass: Class<out Fragment>) {
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//
//        fragmentManager.fragments.forEach {
//            fragmentTransaction.hide(it)
//        }
//
//        val tag = fragmentClass.simpleName
//        var fragment = fragmentManager.findFragmentByTag(tag)
//        if (fragment == null) {
//            fragment = fragmentClass.newInstance()
//            fragmentTransaction.add(R.id.fragment_container, fragment, tag)
//        } else {
//            fragmentTransaction.show(fragment)
//        }
//
//        fragmentTransaction.commitNow()
//    }
}

