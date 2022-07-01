package com.example.groceryapp.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groceryapp.Model.GroceryItems

@Dao
interface GroceryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GroceryItems)


    @Delete
    suspend fun delete(item: GroceryItems)


    @Query("SELECT * FROM grocery_items")
    fun getALlGroceryItems():LiveData<List<GroceryItems>>

}
//Dao