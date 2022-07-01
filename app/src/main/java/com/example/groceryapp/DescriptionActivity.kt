package com.example.groceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.groceryapp.Model.GroceryItems
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)


        val name:String?=intent.getStringExtra("Name")
        val quantity:String?=intent.getStringExtra("Quantity")
        val rate:String?=intent.getStringExtra("Rate")
        val amount:String?=intent.getStringExtra("Amount")

        Name.text=name
        Quantity.text=quantity
        Rate.text=rate
        TotalAmt.text=amount
    }
}