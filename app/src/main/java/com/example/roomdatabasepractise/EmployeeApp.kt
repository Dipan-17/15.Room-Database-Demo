package com.example.roomdatabasepractise

import android.app.Application

//Application class to initialise the database
class EmployeeApp:Application() {
    val db by lazy {
        EmployeeDatabase.getInstance(this)
    }
}