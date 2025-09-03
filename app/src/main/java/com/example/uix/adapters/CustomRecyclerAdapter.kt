package com.example.uix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uix.R
import com.example.uix.models.ListItem

class CustomRecyclerAdapter(
    private val items: List<ListItem>,
    private val onItemClick: (ListItem, Int) -> Unit
) : RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.tv_item_title)
        val descriptionText: TextView = itemView.findViewById(R.id.tv_item_description)
        val idText: TextView = itemView.findViewById(R.id.tv_item_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.titleText.text = item.title
        holder.descriptionText.text = item.description
        holder.idText.text = "#${item.id}"

        holder.itemView.setOnClickListener {
            onItemClick(item, position)
        }
    }

    override fun getItemCount(): Int = items.size
}
