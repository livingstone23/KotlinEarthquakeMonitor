package com.example.kotlinearthquakemonitor.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinearthquakemonitor.Earthquake
import com.example.kotlinearthquakemonitor.R
import com.example.kotlinearthquakemonitor.api.ApiResponseStatus
import com.example.kotlinearthquakemonitor.databinding.ActivityMainBinding

private const val SORT_TYPE_KEY = "sort_type"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)

        val sortType = getSortType()

        viewModel = ViewModelProvider(this, MainViewModelFactory(application, sortType)).get(MainViewModel::class.java)

        val adapter = EqAdapter()
        binding.eqRecycler.adapter = adapter

        viewModel.eqList.observe(this, Observer {
            eqList ->
            adapter.submitList(eqList)

            handleEmptyView(eqList, binding)
        })

        viewModel.status.observe(this, Observer {
            apiResponseStatus ->
            if( apiResponseStatus == ApiResponseStatus.LOADING) {
                binding.loadingWheel.visibility = View.VISIBLE
            } else if (apiResponseStatus == ApiResponseStatus.DONE ) {
                binding.loadingWheel.visibility = View.GONE
            } else if (apiResponseStatus == ApiResponseStatus.ERROR) {
                binding.loadingWheel.visibility = View.GONE
            }
        })

        /*
        val eqList = mutableListOf<Earthquake>()

        eqList.add(Earthquake("1", "Buenos Aires",6.3,273846152L, -102.4756, 28.47365))
        eqList.add(Earthquake("2", "Madrid",5.3,273844562L, -102.4756, 28.47365))
        eqList.add(Earthquake("3", "Murcia",4.4,273844589L, -102.4756, 28.47365))
        eqList.add(Earthquake("4", "Moscow",5.8,273841478L, -102.4756, 28.47365))
        eqList.add(Earthquake("5", "Genova",7.3,  273478445L, -102.4756, 28.47365))
        eqList.add(Earthquake("6", "New York",3.3,273842366L, -102.4756, 28.47365))
        eqList.add(Earthquake("7", "Londres",9.7, 273841245L, -102.4756, 28.47365))
        eqList.add(Earthquake("8", "Israel",5.2,114746152L, -102.4756, 28.47365))
        */



        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }



        //service.getLastHourEarthquakes()
    }

    private fun getSortType(): Boolean {
        val prefs = getPreferences(MODE_PRIVATE)
        return prefs.getBoolean(SORT_TYPE_KEY, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if(itemId == R.id.main_menu_sort_magnitude){
            // TODO: - Sort by magnitude
            viewModel.reloadEarthquakesFromDatabase(true)
            saveSortType(true)
        } else if (itemId == R.id.main_menu_sort_time) {
            saveSortType(false)
            // TODO: - Sort by time
            viewModel.reloadEarthquakesFromDatabase(false)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveSortType(sortBymagnitude: Boolean){
        val prefs = getPreferences(MODE_PRIVATE)
        //val prefs = getSharedPreferences("eq_prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(SORT_TYPE_KEY, sortBymagnitude)
        editor.apply()

    }


    private fun handleEmptyView(
        eqList: MutableList<Earthquake>,
        binding: ActivityMainBinding
    ) {
        if (eqList.isEmpty()) {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else {
            binding.eqEmptyView.visibility = View.GONE
        }
    }
}

