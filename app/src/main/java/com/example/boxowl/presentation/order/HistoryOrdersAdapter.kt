package com.example.boxowl.presentation.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boxowl.R
import com.example.boxowl.models.Order


/**
 * Created by Andrey Morgunov on 11/02/2021.
 */

class HistoryOrdersAdapter(private var dataSet: List<Order>) :
    RecyclerView.Adapter<HistoryOrdersAdapter.ViewHolder>() {

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderId: TextView = itemView.findViewById(R.id.order_id)
        private val orderRating: TextView = itemView.findViewById(R.id.order_rating)
        private val orderCost: TextView = itemView.findViewById(R.id.order_cost)

        fun bind(item: Order) {
            orderId.text =
                itemView.resources.getString(R.string.order_item_id, item.OrderId.toString())
            orderRating.text =
                itemView.context.getString(R.string.rating_text, item.OrderRating.toString())
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.history_order_item, parent, false)
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
