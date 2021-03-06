package com.example.kotlinearthquakemonitor.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kotlinearthquakemonitor.Earthquake
import com.example.kotlinearthquakemonitor.api.ApiResponseStatus
import com.example.kotlinearthquakemonitor.database.getDatabase
import kotlinx.coroutines.*
import java.net.UnknownHostException

class MainViewModel(application: Application, private val sortType: Boolean): AndroidViewModel(application) {


    //private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    //val eqList: LiveData<MutableList<Earthquake>>
    //get() = _eqList

    private val database = getDatabase(application)

    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    //val eqList = repository.eqList
    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
        get() = _eqList

    init {
        //Nuevo forma de corutina y permite comentar la creacion de variable job y corutineScore-
       //reloadEarthquakes()
        reloadEarthquakesFromDatabase(sortType)
    }

    private fun reloadEarthquakes(){
        viewModelScope.launch {
            //_eqList.value =
            try {
                _status.value = ApiResponseStatus.LOADING
                _eqList.value = repository.fetchEarthquake(sortType)

                _status.value = ApiResponseStatus.DONE
            } catch (eMsg: UnknownHostException) {
                _status.value = ApiResponseStatus.ERROR
                Log.d("No internet conexion", eMsg.toString())
            }


        }
    }


    fun reloadEarthquakesFromDatabase(sortByMagnitude: Boolean){
        viewModelScope.launch {
            _eqList.value = repository.fetchEarthquakeFromDb(sortByMagnitude)
        }
    }


}


//FUNCIONES SE TERMINAN PASANDO AL MAINVIEWMODEL
/*
//Palabra suspend permite habilitar al metodo para ser llamada desde una coroutine
private suspend fun fetchEarthquake(): MutableList<Earthquake> {

    return withContext(Dispatchers.IO){
        val eqJsonResponse = service.getLastHourEarthquakes()
        val eqList =  parseEqResult(eqJsonResponse)
        eqList

        //Log.d("fetchEarthquake",eqListString)
        //mutableListOf<Earthquake>()
    }
}

private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake> {
    //val eqJsonObject = JSONObject(eqListString)
    //obtenemos seccion del json
    //val featureJsonArray = eqJsonObject.getJSONArray("features")

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

    /*
    for (i in 0 until featureJsonArray.length())
    {
        val featuresJSONObject = featureJsonArray[i] as JSONObject
        val id = featuresJSONObject.getString("id")

        val propertiesJsonObject =  featuresJSONObject.getJSONObject("properties")
        val magnitude = propertiesJsonObject.getDouble("mag")
        val place = propertiesJsonObject.getString("place")
        val time = propertiesJsonObject.getLong("time")

        val geometryJsonObject =  featuresJSONObject.getJSONObject("geometry")
        val coordinateJsonArray = geometryJsonObject.getJSONArray("coordinates")
        val longitude = coordinateJsonArray.getDouble(0)
        val latitude = coordinateJsonArray.getDouble(1)

        val earthquake = Earthquake(id, place, magnitude, time, longitude, latitude)

        eqList.add(earthquake)
    }
    */

    return eqList
}
*/

//ANTERIOR FORMA DE HACER COROUTINA
/*
class MainViewModel: ViewModel() {

    //Forma anterior de corutina
    //private val job = Job()
    //Una vez creada la subrutina se define en que hilo se ejecutara.
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

        //Nuevo forma de corutina y permite comentar la creacion de variable job y corutineScore-
        viewModelScope.launch {
            _eqList.value = fetchEarthquake()
        }

    }

    //Palabra suspend permite habilitar al metodo para ser llamada desde una coroutine
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

* */