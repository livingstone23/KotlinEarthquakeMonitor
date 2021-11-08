package com.example.kotlinearthquakemonitor.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinearthquakemonitor.Earthquake

@Dao
interface EqDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)

    @Query("Select * from earthquakes")
    fun getEarthquakes(): MutableList<Earthquake>

    @Query("Select * from earthquakes Where magnitude > :magnitude")
    fun getEarthquakesWithMagnitude(magnitude: Double): MutableList<Earthquake>


    @Query("Select * from earthquakes order by magnitude ASC")
    fun getEarthquakesByMagnitude(): MutableList<Earthquake>
}