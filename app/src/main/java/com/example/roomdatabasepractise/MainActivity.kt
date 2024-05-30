package com.example.roomdatabasepractise

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabasepractise.databinding.ActivityMainBinding
import com.example.roomdatabasepractise.databinding.DialogUpdateBinding
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
            val itemAdapter=ItemAdapter(employeeList,

                {   //this lambda is called when the update icon is pressed, cause in adapter we have used invoke
                    //click it -> invoke is used -> this lambda is invoked with the item id -> this lambda in turn calls another function called updateRecordDialog
                    updateId->
                    updateRecordDialog(updateId,employeeDAO)

                },
                {
                    deleteId->
                    deleteRecordAlertDialog(deleteId,employeeDAO)
                }
                )



            binding?.rvItemsList?.layoutManager=LinearLayoutManager(this)
            binding?.rvItemsList?.adapter=itemAdapter
            binding?.rvItemsList?.visibility= View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility=View.GONE
        }else{
            binding?.rvItemsList?.visibility= View.GONE
            binding?.tvNoRecordsAvailable?.visibility=View.VISIBLE
        }
    }

    private fun updateRecordDialog(id:Int,employeeDAO: EmployeeDAO){
        //define the dialog
        val updateDialog=Dialog(this, R.style.Theme_Dialog_Custom)
        updateDialog.setCancelable(false)
        //layout
        val binding=DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        //populate the dialog with existing record
        lifecycleScope.launch {
            employeeDAO.fetchAllEmployeesById(id).collect {
                binding.etUpdateName.setText(it.name)
                binding.etUpdateEmailId.setText(it.email)

            }
        }

        binding.tvUpdate.setOnClickListener {
            val name=binding.etUpdateName.text.toString()
            val eMail=binding.etUpdateEmailId.text.toString()

            if(name.isNotEmpty() && eMail.isNotEmpty()){
                lifecycleScope.launch {
                    employeeDAO.update(EmployeeEntity(id,name,eMail))
                    Toast.makeText(applicationContext,"Updated",Toast.LENGTH_SHORT).show()
                    updateDialog.dismiss()
                }
            }else{
                Toast.makeText(applicationContext,"Empty Field not possible",Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvCancel.setOnClickListener {
            updateDialog.dismiss()
        }

        updateDialog.show()
    }

    private fun deleteRecordAlertDialog(id:Int,employeeDAO: EmployeeDAO){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Delete Record!!")

        builder.setPositiveButton("Yes"){ dialogInterface,_ ->
            lifecycleScope.launch {
                employeeDAO.delete(EmployeeEntity(id))
                Toast.makeText(applicationContext,"Record deleted",Toast.LENGTH_SHORT).show()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No"){dialogInterface,_ ->
            dialogInterface.dismiss()
        }

        val alertDialog:AlertDialog=builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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