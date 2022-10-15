package com.pizza.application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pizza.application.R
import com.pizza.application.data.local.database.PizzaListEntity
import com.pizza.application.util.ImageLoader
import com.pizza.application.util.emptyIfNull
import com.pizza.application.util.toStringPrice
import kotlinx.android.synthetic.main.pizza_list_item.view.*

class PizzaListAdapter: RecyclerView.Adapter<PizzaListAdapter.PizzaListViewHolder>() {

    private val pizzasList = mutableListOf<PizzaListEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaListViewHolder {
        return PizzaListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pizza_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PizzaListViewHolder, position: Int) {
        holder.bind(pizzasList[position])
    }

    override fun getItemCount(): Int {
        return pizzasList.size
    }

    fun updateList(list: List<PizzaListEntity>) {
        this.pizzasList.clear()
        this.pizzasList.addAll(list)
        notifyDataSetChanged()
    }

    class PizzaListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: PizzaListEntity) {
            itemView.pizzaItemNameTextView.text = model.name.emptyIfNull()
            itemView.pizzaItemDscTextView.text = model.dsc.emptyIfNull()
            itemView.pizzaItemPriceTextView.text = model.price.toStringPrice()

            ImageLoader.loadImage(itemView.pizzaItemImageView, model.img ?: "")
        }
    }
}