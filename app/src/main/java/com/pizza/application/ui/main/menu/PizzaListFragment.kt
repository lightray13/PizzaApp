package com.pizza.application.ui.main.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pizza.application.adapters.BannerAdapter
import com.pizza.application.adapters.MenuItemsAdapter
import com.pizza.application.adapters.OnItemClickCallback
import com.pizza.application.adapters.PizzaListAdapter
import com.pizza.application.adapters.model.MenuItem
import com.pizza.application.data.local.database.PizzaListEntity
import com.pizza.application.databinding.FragmentPizzaListBinding
import com.pizza.application.util.Constants
import com.pizza.application.util.doOnChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pizza_list.*
import java.util.*

@AndroidEntryPoint
class PizzaListFragment : Fragment(), OnItemClickCallback {
    private lateinit var binding: FragmentPizzaListBinding

    private val viewModel: PizzaListViewModel by viewModels()
    private var pizzaListAdapter = PizzaListAdapter()
    private var bannerAdapter = BannerAdapter()
    private var menuItemsAdapter = MenuItemsAdapter(this)

    private val menuItemsList = mutableListOf(
        MenuItem(1, Constants.PIZZAS_CATEGORY, true),
        MenuItem(2, Constants.DESSERTS_CATEGORY, false),
        MenuItem(3, Constants.DRINKS_CATEGORY, false)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPizzaListBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@PizzaListFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.loadPizzasFromApi()
    }

    private fun initializeViews() {
        pizzaListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pizzaListAdapter
        }
        bannerListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = bannerAdapter
        }
        menuItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = menuItemsAdapter
            val name = viewModel.getMenuSelectedItem()
            menuItemsList.find{ it.name == name}?.isSelected = true
            menuItemsList.filter { it.name != name }.forEach { it.isSelected = false }
            menuItemsAdapter.updateList(menuItemsList)
        }
        chooseCityMaterialSpinner.setItems("Moscow", "Kazan", "Tver", "Saratov", "Novosibirsk")
    }

    private fun observeViewModel() {
        viewModel.isLoading.doOnChange(this) {
            pizzasListLoading.visibility = if (viewModel.isListEmpty() && it) View.VISIBLE else View.GONE

            if (it) {
                pizzaListErrorView.visibility = View.GONE
            }
        }
        viewModel.pizzasList.doOnChange(this) {
            val list = pizzasListByMenuSelectItem(it)
            pizzaListAdapter.updateList(list)

            pizzaListErrorView.visibility = if (viewModel.isListEmpty()) View.VISIBLE else View.GONE
        }
        viewModel.menuSelectedItem.doOnChange(this) {
            viewModel.pizzasList.value?.let {
                val list = pizzasListByMenuSelectItem(it)
                pizzaListAdapter.updateList(list)
            }
        }
    }

    override fun onItemClick(id: Int, name: String) {
        viewModel.setMenuSelectedItem(name)
        menuItemsList.find{ it.id == id}?.isSelected = true
        menuItemsList.filter { it.id != id }.forEach { it.isSelected = false }
        menuItemsAdapter.updateList(menuItemsList)
        pizzaListRecyclerView.smoothScrollToPosition(0);
    }

    private fun pizzasListByMenuSelectItem(pizzasList: List<PizzaListEntity>): List<PizzaListEntity> = when(viewModel.getMenuSelectedItem()) {
            Constants.PIZZAS_CATEGORY -> pizzasList.filter { it.category == Constants.PIZZAS_CATEGORY }
            Constants.DESSERTS_CATEGORY -> pizzasList.filter { it.category == Constants.DESSERTS_CATEGORY }
            Constants.DRINKS_CATEGORY -> pizzasList.filter { it.category == Constants.DRINKS_CATEGORY }
            else -> pizzasList.filter { it.category == Constants.DRINKS_CATEGORY }
        }
}