package com.pizza.application.ui.main.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.pizza.application.R

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pizza_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}