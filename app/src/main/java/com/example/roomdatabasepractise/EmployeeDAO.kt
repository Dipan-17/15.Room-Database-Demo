package com.example.roomdatabasepractise

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Data Access Object
@Dao
//we define the functions that we need
interface EmployeeDAO {

    //for frequently used ones we have predefined functions
    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)

    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    //to retrieve -> all or particular
    @Query("SELECT * FROM 'employee_table'")
    fun fetchAllEmployees():Flow<List<EmployeeEntity>>//flow collect data that can change data at runtime -> no notify on dataset change

    @Query("SELECT * FROM 'employee_table' where id=:id")
    fun fetchAllEmployeesById(id:Int):Flow<EmployeeEntity>//flow collect data that can change data at runtime -> no notify on dataset change

}