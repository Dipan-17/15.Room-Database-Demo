package com.example.roomdatabasepractise

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.internal.synchronized


//here we are basically returning instance of a database

@Database(entities = [EmployeeEntity::class],version=1)
abstract class EmployeeDatabase:RoomDatabase() {
    //connect database to DAO
    abstract fun employeeDao():EmployeeDAO

    companion object{
        @Volatile //changes made by one thread visible to all
        private var INSTANCE:EmployeeDatabase?=null

        fun getInstance(context: Context):EmployeeDatabase{
            kotlin.synchronized(this){
                var instance= INSTANCE

                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE=instance
                }
                return instance
            }
        }

    }
}