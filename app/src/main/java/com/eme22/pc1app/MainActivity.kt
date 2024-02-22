package com.eme22.pc1app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eme22.pc1app.databinding.ActivityMainBinding
import com.eme22.pc1app.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.Serializable


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PC1App);
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_results, R.id.navigation_notifications, R.id.navigation_configuration
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_menu -> {
                val intent = Intent(this, LoginActivity::class.java)
                showText(R.string.loged_out)
                startActivity(intent)
                setResult(Activity.RESULT_OK)
                finish()
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showText(@StringRes string: Int) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressedDispatcher.onBackPressed()

        return true

    }

}