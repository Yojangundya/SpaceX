package com.example.spacex

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.FragmentNavigator

class SpaceViewModel(): ViewModel() {
    var spaceXResponse=MutableLiveData<List<SpaceXResponse?>?>()

    fun onNavItemClicked(itemId: Int, fm: FragmentManager) {
        val fragment = when (itemId) {
            R.id.action_home -> HomeScreenFragment()
            R.id.action_store -> StoreScreenFragment()
            R.id.action_search -> SearchScreenFragment()
            else -> return
        }
        fm.beginTransaction()
            .replace(R.id.flMain, fragment)
            .addToBackStack(null)
            .commit()
    }
}