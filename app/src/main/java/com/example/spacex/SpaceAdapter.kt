package com.example.spacex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spacex.databinding.ItemSpaceBinding

class SpaceAdapter(
    private val spaceList: List<SpaceXResponse?>?,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<SpaceAdapter.SpaceViewHolder>() {


    inner class SpaceViewHolder(val binding: ItemSpaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = spaceList?.get(adapterPosition)
                listener.onItemClick(item)
            }
        }
    }

    // Inflate the item layout and create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceViewHolder {
        val binding = ItemSpaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpaceViewHolder(binding)
    }

    // Bind data to the views
    override fun onBindViewHolder(holder: SpaceViewHolder, position: Int) {
        val item = spaceList?.get(position)
        holder.binding.tvMissionName.text = item?.missionName ?: ""
        holder.binding.tvRocektName.text = item?.rocket?.rocketName?:""
        holder.binding.tvLaunchYear.text = item?.launchYear ?: ""
        holder.binding.tvRcType.text = item?.rocket?.rocketType ?: ""
        holder.binding.tvFlightNumber.text = (item?.flightNumber ?: "").toString()
    }

    override fun getItemCount(): Int = spaceList?.size ?: 0
}

interface OnItemClickListener {
    fun onItemClick(item: SpaceXResponse?)
}