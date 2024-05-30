package com.example.roomdatabasepractise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasepractise.databinding.ItemsRowBinding

class ItemAdapter(private val items:ArrayList<EmployeeEntity>,//list of items
    private val updateListener:(id:Int)->Unit, //update button on recycler view listener
    private val deleteListener:(id:Int)->Unit //delete button on rv listener


):RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(binding:ItemsRowBinding):RecyclerView.ViewHolder(binding.root){//cause xml is itemsRow
        val  llMain= binding.llMain
        val tvName=binding.tvName
        val tvEmail=binding.tvEmail
        val ivEdit=binding.ivEdit
        val ivDelete=binding.ivDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context=holder.itemView.context
        val item=items[position]

        holder.tvName.text=item.name
        holder.tvEmail.text=item.email

        //just alternating bg for more pleasing effects
        if(position%2==0){
            holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.colorLightGrey))
        }else{
            holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
        }

        //handle the onclicklistener
        holder.ivEdit.setOnClickListener {
            updateListener.invoke(item.id)
        }
        holder.ivDelete.setOnClickListener {
            deleteListener.invoke(item.id)
        }
    }
}