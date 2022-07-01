package com.example.groceryapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.Adapter.GroceryRvAdapter
import com.example.groceryapp.Db.GroceryDatabase
import com.example.groceryapp.Model.GroceryItems
import com.example.groceryapp.ViewModel.GroceryViewModel
import com.example.groceryapp.ViewModel.GroceryViewModelFactory
import com.example.groceryapp.repo.GroceryRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity(),GroceryRvAdapter.GroceryItemClickInterface,GroceryRvAdapter.GroceryViewInterface{
    lateinit var itemsRV:RecyclerView
    lateinit var addFAB:FloatingActionButton
    lateinit var list:List<GroceryItems>
    lateinit var groceryRVAdapter:GroceryRvAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRV=findViewById(R.id.idRVItems)
        addFAB=findViewById(R.id.idFABAdd)
        list=ArrayList<GroceryItems>()
        groceryRVAdapter= GroceryRvAdapter(this,list,this,this)
        itemsRV.layoutManager=LinearLayoutManager(this)
        itemsRV.adapter=groceryRVAdapter
        val groceryRepository=GroceryRepository(GroceryDatabase(this))
        val factory=GroceryViewModelFactory(groceryRepository)
        groceryViewModel=ViewModelProvider(this,factory).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItems().observe(this, Observer {
            groceryRVAdapter.list=it
            groceryRVAdapter.notifyDataSetChanged()
        })
        addFAB.setOnClickListener {
            openDialog()
        }

    }
    fun openDialog(){
        val dialog=Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn=dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn=dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt=dialog.findViewById<EditText>(R.id.idEdtItemName)
        val itemPriceEdt=dialog.findViewById<EditText>(R.id.idEdtItemPrice)
        val itemQuantityEdt=dialog.findViewById<EditText>(R.id.idEdtItemQuantity)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            val itemName:String=itemEdt.text.toString()
            val itemPrice:String=itemPriceEdt.text.toString()
            val itemQuantity:String=itemQuantityEdt.text.toString()
            try {
                itemPrice.toInt()
                itemQuantity.toInt()
            }catch (e:NumberFormatException){
                Toast.makeText(applicationContext,"Please Enter Item name, price and quantity",Toast.LENGTH_LONG).show()
            }
            if (itemName.isNotEmpty()){
                val items=GroceryItems(itemName,itemPrice.toInt(),itemQuantity.toInt())
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item Inserted",Toast.LENGTH_LONG).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted",Toast.LENGTH_LONG).show()

    }

    override fun onItemView(groceryItems: GroceryItems){
//        val intent=Intent(this,DescriptionActivity::class.java)
//        startActivity(intent)
        groceryRVAdapter.notifyDataSetChanged()
    }
}