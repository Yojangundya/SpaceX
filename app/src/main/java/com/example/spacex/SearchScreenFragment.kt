package com.example.spacex

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacex.databinding.FragmentSearchScreenBinding


class SearchScreenFragment : Fragment() {


    private lateinit var binding: FragmentSearchScreenBinding
    private val spaceVm: SpaceViewModel by lazy {
        ViewModelProvider(this).get(SpaceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search()
    }

    private fun search() {
        val data = SpaceManager.spaceResponse
        if (data == null) {
            SpaceManager.makeApiCall { data, show ->
                spaceVm.spaceXResponse.value = data
                SpaceManager.spaceResponse = data
                updateAdapter(data)
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                performSearch(searchText)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun performSearch(searchText: String) {
        if (searchText.isEmpty()) {
            binding.rvSearch.visibility = View.GONE
            return
        }
        binding.rvSearch.visibility = View.VISIBLE
        var data = SpaceManager.spaceResponse
        val filteredList = data?.filter { item ->
            item?.missionName?.contains(searchText, true) ?: false ||
                    item?.rocket?.rocketName?.contains(searchText, true) ?: false ||
                    item?.launchYear?.contains(searchText, true) ?: false
        }
        data = filteredList
        if (data.isNullOrEmpty()) {
            binding.tvNo.visibility = View.VISIBLE
            binding.rvSearch.visibility = View.GONE
            return
        }
        binding.tvNo.visibility = View.GONE
        binding.rvSearch.visibility = View.VISIBLE
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        updateAdapter(data)
    }

    private fun updateAdapter(data: List<SpaceXResponse?>?) {
        val adapter = SpaceAdapter(data, object : OnItemClickListener {
            override fun onItemClick(item: SpaceXResponse?) {
                val c = context
                if (c == null || item == null) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                    return
                }
                SpaceManager.showDetailScreen(activity, item)
            }
        })
        binding.rvSearch.adapter = adapter
    }
}
