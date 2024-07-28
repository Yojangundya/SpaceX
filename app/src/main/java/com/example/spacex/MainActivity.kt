package com.example.spacex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.spacex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val spaceVm: SpaceViewModel by lazy {
        ViewModelProvider(this).get(SpaceViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spaceVm.onNavItemClicked(R.id.action_home,supportFragmentManager)
        initClick()
    }

    private fun initClick() {
        binding.nav.setOnItemSelectedListener { item ->
            spaceVm.onNavItemClicked(item.itemId,supportFragmentManager)
            true
        }
    }
}