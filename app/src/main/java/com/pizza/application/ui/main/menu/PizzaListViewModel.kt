package com.pizza.application.ui.main.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pizza.application.data.repository.menu.PizzaListRepository
import com.pizza.application.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PizzaListViewModel @Inject constructor(private val repository: PizzaListRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _menuSelectedItem = MutableLiveData<String>()
    val menuSelectedItem: LiveData<String> = _menuSelectedItem

    val pizzasList = repository.pizzasList

    fun isListEmpty(): Boolean {
        return pizzasList.value?.isEmpty() ?: true
    }

    fun loadPizzasFromApi() {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                repository.loadPizzasLists()
                _isLoading.postValue(false)
            }
        }
    }

    fun setMenuSelectedItem(name: String) {
        _menuSelectedItem.postValue(name)
    }

    fun getMenuSelectedItem(): String {
        return menuSelectedItem.value ?: Constants.PIZZAS_CATEGORY
    }
}