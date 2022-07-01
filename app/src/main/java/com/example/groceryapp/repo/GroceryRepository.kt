package com.example.groceryapp.repo

import com.example.groceryapp.Db.GroceryDatabase
import com.example.groceryapp.Model.GroceryItems

class GroceryRepository(private val db: GroceryDatabase) {
    suspend fun insert(items: GroceryItems)=db.getGroceryDao().insert(items)
    suspend fun delete(items: GroceryItems)=db.getGroceryDao().delete(items)

    fun getAllItems()=db.getGroceryDao().getALlGroceryItems()
}