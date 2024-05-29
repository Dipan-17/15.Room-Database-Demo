package com.example.roomdatabasepractise

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="employee_table")
data class EmployeeEntity(
    //these are the columns of the table
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val name:String="",
    @ColumnInfo(name="email-id")//internally this name used
    val email:String=""//we are using this name
)
