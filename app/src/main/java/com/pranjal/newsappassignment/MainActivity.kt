package com.pranjal.newsappassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pranjal.newsappassignment.databinding.ActivityMainBinding
import com.pranjal.newsappassignment.db.ArticleDatabase
import com.pranjal.newsappassignment.repository.NewsRepository
import com.pranjal.newsappassignment.viewmodel.NewsViewModel
import com.pranjal.newsappassignment.viewmodel.NewsViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var newsViewModel :NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                .setupWithNavController(navController)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val newsRepository= NewsRepository(ArticleDatabase(this))
        val newsViewModelFactory= NewsViewModelFactory(application,newsRepository)
        newsViewModel= ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)
    }
}