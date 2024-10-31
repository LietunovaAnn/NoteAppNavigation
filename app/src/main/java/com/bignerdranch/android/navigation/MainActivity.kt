package com.bignerdranch.android.navigation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bignerdranch.android.navigation.databinding.ActivityMainBinding
import com.bignerdranch.android.navigation.utilits.APP_ACTIVITY
import com.bignerdranch.android.navigation.utilits.AppPreference

class MainActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this

        mToolbar = mBinding.toolbar
        navController = Navigation.findNavController(this, R.id.nav_host)
        setSupportActionBar(mToolbar)
        title = getString(R.string.title)

        AppPreference.getPreference(this)


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}