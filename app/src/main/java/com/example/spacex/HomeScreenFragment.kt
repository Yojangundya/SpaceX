package com.example.spacex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacex.databinding.FragmentHomeScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding

    private val spaceVm: SpaceViewModel by lazy {
        ViewModelProvider(this).get(SpaceViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeApiCall()
        showProgressbar(true)
        initViewModel()
    }

    private fun makeApiCall() {
        binding.tvNoResult.visibility=View.GONE
        if (SpaceManager.spaceResponse==null){
            SpaceManager.makeApiCall { data, show ->
                spaceVm.spaceXResponse.value=data
                showProgressbar(show)
                if (data==null && !show){
                    binding.tvNoResult.visibility=View.VISIBLE
                }
            }
        }
    }

    private fun initViewModel() {
        spaceVm.spaceXResponse.observe(viewLifecycleOwner, Observer { data ->
            if (!data.isNullOrEmpty()) {
                binding.rvSpace.layoutManager = LinearLayoutManager(context)
                val adapter = SpaceAdapter(data, object : OnItemClickListener {
                    override fun onItemClick(item: SpaceXResponse?) {
                        val c = context
                        if (c == null || item == null) {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                                .show()
                            return
                        }
                        SpaceManager.showDetailScreen(activity, item)
                    }
                })
                binding.rvSpace.adapter = adapter
            }
        })
    }


    private fun showProgressbar(show: Boolean) {
        if (show) {
            binding.homeProgressbar.visibility = View.VISIBLE
            binding.rvSpace.visibility = View.GONE
        } else {
            binding.homeProgressbar.visibility = View.GONE
            binding.rvSpace.visibility = View.VISIBLE
        }
    }
}