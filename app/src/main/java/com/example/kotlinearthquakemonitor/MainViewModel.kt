package com.example.kotlinearthquakemonitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    //Forma anterior de corutina
    //private val job = Job()
    //private val corutineScore = CoroutineScope(Dispatchers.Main + job)

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
    get() = _eqList

    init {

        //Forma anterior
        /*
        corutineScore.launch {
            _eqList.value = fetchEarthquake()
        }
        */

        //Nuevo forma
        viewModelScope.launch {
            _eqList.value = fetchEarthquake()
        }

    }

    private suspend fun fetchEarthquake(): MutableList<Earthquake> {

        return withContext(Dispatchers.IO){
            val eqList = mutableListOf<Earthquake>()

            eqList.add(Earthquake("1", "Buenos Aires",6.3,273846152L, -102.4756, 28.47365))
            eqList.add(Earthquake("2", "Madrid",5.3,273844562L, -102.4756, 28.47365))
            eqList.add(Earthquake("3", "Murcia",4.4,273844589L, -102.4756, 28.47365))
            eqList.add(Earthquake("4", "Moscow",5.8,273841478L, -102.4756, 28.47365))
            eqList.add(Earthquake("5", "Genova",7.3,  273478445L, -102.4756, 28.47365))
            eqList.add(Earthquake("6", "New York",3.3,273842366L, -102.4756, 28.47365))
            eqList.add(Earthquake("7", "Londres",9.7, 273841245L, -102.4756, 28.47365))
            eqList.add(Earthquake("8", "Israel",5.2,114746152L, -102.4756, 28.47365))

            eqList
        }
    }

    /*
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
    */

}