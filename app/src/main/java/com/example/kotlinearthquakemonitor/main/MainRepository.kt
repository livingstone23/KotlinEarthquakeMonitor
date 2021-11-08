package com.example.kotlinearthquakemonitor.main

import androidx.lifecycle.LiveData
import com.example.kotlinearthquakemonitor.Earthquake
import com.example.kotlinearthquakemonitor.api.EqJsonResponse
import com.example.kotlinearthquakemonitor.api.service
import com.example.kotlinearthquakemonitor.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: EqDatabase) {

    //val eqList: LiveData<MutableList<Earthquake>> = database.eqDao.getEarthquakes()

    //Palabra suspend permite habilitar al metodo para ser llamada desde una coroutine
    suspend fun fetchEarthquake(sortByMagnitude: Boolean): MutableList<Earthquake>
    {

        return withContext(Dispatchers.IO){
            val eqJsonResponse = service.getLastHourEarthquakes()
            val eqList =  parseEqResult(eqJsonResponse)

            database.eqDao.insertAll(eqList)
            fetchEarthquakeFromDb(sortByMagnitude)
            /*
            if (sortByMagnitude){
                database.eqDao.getEarthquakesByMagnitude()
            } else {
                database.eqDao.getEarthquakes()
            }
            */

            //val earthquakes = database.eqDao.getEarthquakes()
            //earthquakes
            //eqList
        }
    }


    suspend fun fetchEarthquakeFromDb(sortByMagnitude: Boolean): MutableList<Earthquake>{
        return withContext(Dispatchers.IO){
            if (sortByMagnitude){
                database.eqDao.getEarthquakesByMagnitude()
            } else {
                database.eqDao.getEarthquakes()
            }
        }
    }


    private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake> {

        val eqList = mutableListOf<Earthquake>()
        val featureList = eqJsonResponse.features

        for(feature in featureList) {
            val properties = feature.properties

            val id = feature.id
            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry =feature.geometry
            val longitude = geometry.longitude
            val latitude = geometry.latitude

            eqList.add(Earthquake(id, place, magnitude, time, longitude, latitude))
        }
        return eqList
    }
}