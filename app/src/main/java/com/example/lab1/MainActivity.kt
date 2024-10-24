package com.example.lab1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var settingsDataStore: SettingsDataStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        settingsDataStore = SettingsDataStore(applicationContext)

        // Сохранение темы
        lifecycleScope.launch {
            settingsDataStore.saveTheme(true)
        }

        // Подписка на изменения темы
        settingsDataStore.isDarkModeFlow
            .onEach { isDarkMode ->
                println("Is dark mode: $isDarkMode")
            }
            .launchIn(lifecycleScope)
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

