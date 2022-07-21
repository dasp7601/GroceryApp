package com.example.groceryapp.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.groceryapp.Dao.GroceryDao
import com.example.groceryapp.Db.GroceryDatabase
import com.example.groceryapp.Model.GroceryItems
import com.example.groceryapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class GroceryDaoTest {

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()
    private lateinit var database:GroceryDatabase
    private lateinit var dao:GroceryDao

    @Before
    fun setUp(){
        database=Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GroceryDatabase::class.java).allowMainThreadQueries().build()
        dao=database.getGroceryDao()
    }

    @After
    fun tearDown(){
        database.close()
    }
    @Test
    fun insertItem()= runBlockingTest{
        val groceryItem=GroceryItems("potato",10,20)
        dao.insert(groceryItem)

        val allGroceryItems=dao.getALlGroceryItems().getOrAwaitValue()

        assertThat(allGroceryItems).contains(groceryItem)
    }

    @Test
    fun getAllItems()=runBlockingTest{
        val groceryItem1=GroceryItems("potato",10,20)
        val groceryItem2=GroceryItems("tomato",10,23)
        dao.insert(groceryItem1)
        dao.insert(groceryItem2)
        val allGroceryItems=dao.getALlGroceryItems().getOrAwaitValue()

        assertThat(allGroceryItems).hasSize(2)
    }
}