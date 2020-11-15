package com.example.boxowl.presentation.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.boxowl.R
import com.example.boxowl.models.Order


/**
 * Created by Andrey Morgunov on 13/11/2020.
 */

class HomeAdapter(private var dataSet: List<Order>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderId: TextView = itemView.findViewById(R.id.order_id)
        private val orderAddress: TextView = itemView.findViewById(R.id.order_address)
        private val locationButton: ImageButton = itemView.findViewById(R.id.location_btn)

        fun bind(item: Order) {
            orderId.text = "1"
            orderAddress.text = item.DeliveryAddress
            locationButton.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:0,0?q=${item.DeliveryAddress}, Москва")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(itemView.context, mapIntent, null)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.order_item, parent, false)
                return ViewHolder(view)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bind(item)
    }

    override fun getItemCount() = dataSet.size

}
