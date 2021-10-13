package com.example.kotlinearthquakemonitor

data class Earthquake(val id: String, val place: String, val magnitude: Double, val time: Long, val longitude: Double, val latitude: Double) {
}