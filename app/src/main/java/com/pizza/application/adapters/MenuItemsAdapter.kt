package com.pizza.application.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pizza.application.R
import com.pizza.application.adapters.model.MenuItem
import kotlinx.android.synthetic.main.pizza_menu_item.view.*

interface OnItemClickCallback {
    fun onItemClick(id: Int, name: String)
}

class MenuItemsAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<MenuItemsAdapter.MenuItemsViewHolder>() {
    private val menuItemsList = mutableListOf<MenuItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemsViewHolder {
        return MenuItemsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pizza_menu_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuItemsViewHolder, position: Int) {
        holder.bind(menuItemsList[position], onItemClickCallback)
    }

    override fun getItemCount(): Int {
        return menuItemsList.size
    }

    fun updateList(list: List<MenuItem>) {
        this.menuItemsList.clear()
        this.menuItemsList.addAll(list)
        notifyDataSetChanged()
    }

    class MenuItemsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: MenuItem, onItemClickCallback: OnItemClickCallback) {
            itemView.menuItemTextView.text = model.name

            itemView.menuItemTextView.setTextColor(
                if (model.isSelected) Color.parseColor("#FD3A69") else Color.parseColor("#C3C4C9")
            )
            itemView.pizzaMenuItemCardView.setCardBackgroundColor(
                if (model.isSelected) Color.parseColor("#f3ccd6") else Color.parseColor("#FFFFFF")
            )

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(model.id, model.name)
            }
        }
    }
}