package com.example.groceryapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.DescriptionActivity
import com.example.groceryapp.Model.GroceryItems
import com.example.groceryapp.R

class GroceryRvAdapter(
    val context: Context,
    var list:List<GroceryItems>,
    val groceryItemClickInterface: GroceryItemClickInterface,
    val groceryViewInterface: GroceryViewInterface,
    ): RecyclerView.Adapter<GroceryRvAdapter.GroceryViewHolder>() {

    inner class GroceryViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val nameTV=itemView.findViewById<TextView>(R.id.idTVItmName)
//        val quantityTV=itemView.findViewById<TextView>(R.id.idTVQuantity)
//        val rateTV=itemView.findViewById<TextView>(R.id.idTVRate)
        val amountTV=itemView.findViewById<TextView>(R.id.idTVTotalAmt)
        val deleteIV=itemView.findViewById<ImageView>(R.id.idIVDelete)
        val view=itemView.findViewById<LinearLayout>(R.id.idLL1)
    }

    interface GroceryViewInterface{
        fun onItemView(groceryItems: GroceryItems)
    }

    interface GroceryItemClickInterface{
        fun onItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.grocery_rv_item,parent,false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.nameTV.text="Name: "+list.get(position).itemName
//        holder.quantityTV.text="No. "+list.get(position).itemQuantity.toString()
//        holder.rateTV.text="Rate: Rs. "+list.get(position).itemPrice.toString()
        val itemTotal:Int=list.get(position).itemPrice*list.get(position).itemQuantity
        holder.amountTV.text="Total: Rs. "+itemTotal.toString()
        holder.deleteIV.setOnClickListener {
            groceryItemClickInterface.onItemClick(list.get(position))
        }
        holder.view.setOnClickListener {
            groceryViewInterface.onItemView(list.get(position))
            val intent= Intent(context, DescriptionActivity::class.java)
            intent.putExtra("Name","ITEMNAME\n"+list.get(position).itemName)
            intent.putExtra("Quantity","Quantity\n"+(list.get(position).itemQuantity).toString())
            intent.putExtra("Rate","Price\n"+(list.get(position).itemPrice).toString())
            intent.putExtra("Amount","Total Amount\n"+itemTotal.toString())
            startActivity(context,intent,null)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}