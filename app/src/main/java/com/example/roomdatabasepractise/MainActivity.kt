package com.example.roomdatabasepractise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabasepractise.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //DAO to the database is required for anything to do in database
        val employeeDAO=(application as EmployeeApp).db.employeeDao()
        binding?.btnAdd?.setOnClickListener {
            addRecord(employeeDAO)
        }

        //setup recycler view
        //run it on background to fetch data
        lifecycleScope.launch {
            employeeDAO.fetchAllEmployees().collect(){
                //convert list to array list to pass
                val list=ArrayList(it)
                setUpListOfDataIntoRecyclerView(list,employeeDAO)
            }
        }

    }

    private fun setUpListOfDataIntoRecyclerView(employeeList:ArrayList<EmployeeEntity>,
                                                employeeDAO: EmployeeDAO){
        if(employeeList.isNotEmpty()){
            val itemAdapter=ItemAdapter(employeeList)
            binding?.rvItemsList?.layoutManager=LinearLayoutManager(this)
            binding?.rvItemsList?.adapter=itemAdapter
            binding?.rvItemsList?.visibility= View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility=View.GONE
        }else{
            binding?.rvItemsList?.visibility= View.GONE
            binding?.tvNoRecordsAvailable?.visibility=View.VISIBLE
        }
    }

    private fun addRecord(employeeDAO: EmployeeDAO){
        val name=binding?.etName?.text.toString()
        val email=binding?.etEmailId?.text.toString()
        if(name.isNotEmpty() && email.isNotEmpty()){
            //coroutine
            lifecycleScope.launch {
                employeeDAO.insert(EmployeeEntity(name=name,email=email))

                Toast.makeText(applicationContext,"Record saved",Toast.LENGTH_SHORT).show()
                binding?.etName?.text?.clear()
                binding?.etEmailId?.text?.clear()
            }
        }else{
            Toast.makeText(applicationContext,"Please fill records first",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        binding=null
        super.onDestroy()
    }
}