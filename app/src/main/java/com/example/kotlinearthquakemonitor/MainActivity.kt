package com.example.kotlinearthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinearthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)

        val eqList = mutableListOf<Earthquake>()
        /*
        eqList.add(Earthquake("1", "Buenos Aires",6.3,273846152L, -102.4756, 28.47365))
        eqList.add(Earthquake("2", "Madrid",5.3,273844562L, -102.4756, 28.47365))
        eqList.add(Earthquake("3", "Murcia",4.4,273844589L, -102.4756, 28.47365))
        eqList.add(Earthquake("4", "Moscow",5.8,273841478L, -102.4756, 28.47365))
        eqList.add(Earthquake("5", "Genova",7.3,  273478445L, -102.4756, 28.47365))
        eqList.add(Earthquake("6", "New York",3.3,273842366L, -102.4756, 28.47365))
        eqList.add(Earthquake("7", "Londres",9.7, 273841245L, -102.4756, 28.47365))
        eqList.add(Earthquake("8", "Israel",5.2,114746152L, -102.4756, 28.47365))
        */
        
        val adapter = EqAdapter()
        binding.eqRecycler.adapter = adapter
        adapter.submitList(eqList)

        if(eqList.isEmpty())
        {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else {
            binding.eqEmptyView.visibility = View.GONE
        }
    }
}

