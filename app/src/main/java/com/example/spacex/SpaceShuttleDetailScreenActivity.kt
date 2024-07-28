package com.example.spacex

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import com.example.spacex.databinding.ActivitySpaceShuttleDetailScreenBinding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class SpaceShuttleDetailScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpaceShuttleDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpaceShuttleDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateUi()
    }

    private fun updateUi() {
        val data = SpaceManager.selectedData
        if (data == null) {
            binding.cl.visibility = View.GONE
            return
        }
        binding.tvFn.text = data.flightNumber.toString()
        binding.tvMn.text = data.missionName
        binding.tvLy.text = data.launchYear
        binding.tvLd.text = getDate(data.launchDateUtc)
        binding.tvRId.text = data.rocket?.rocketId
        binding.tvRn.text = data.rocket?.rocketName
        binding.tvRocketType.text = data.rocket?.rocketType
//        binding.tvCoreSerial.text=data.rocket.firstStage.cores.
        binding.tvLaunchSiteName.text = data.launchSite?.siteName
        binding.tvFailure.text = if (data.launchSuccess == true) "Success" else "Failed"
        if (data.launchSuccess == false) {
            binding.tvReason.visibility = View.VISIBLE
            binding.tvReason.text = data.launchFailureDetails?.reason
        } else {
            binding.tvReason.visibility = View.GONE
        }
        val imageLink = data.links?.missionPatch
        if (imageLink == null) {
            binding.ivImage.visibility = View.GONE
        } else {
            binding.ivImage.visibility = View.VISIBLE
            Glide.with(this)
                .load(imageLink)
                .into(binding.ivImage)
        }

        val payloads = data.rocket?.secondStage?.payloads
        if (payloads != null) {
            for (payload in payloads) {
                val itemView = LayoutInflater.from(this)
                    .inflate(R.layout.payload_item, binding.llPayload, false)
                val tvPayloadId = itemView.findViewById<TextView>(R.id.tvId)
                val tvPayloadType = itemView.findViewById<TextView>(R.id.tvType)
                val tvPayloadNatioanality = itemView.findViewById<TextView>(R.id.tvNatioanality)
                val tvPayloadKg = itemView.findViewById<TextView>(R.id.tvKg)
                tvPayloadId.text = payload?.payloadId
                tvPayloadType.text = payload?.payloadType
                tvPayloadNatioanality.text = payload?.nationality
                val weight=payload?.payloadMassKg
                if(weight !=null){
                    tvPayloadKg.visibility=View.VISIBLE
                    tvPayloadKg.text = "${payload?.payloadMassKg} kg"
                }else{
                    tvPayloadKg.visibility=View.GONE
                }

                binding.llPayload.addView(itemView)
            }
        }
        val firstStage=data.rocket?.firstStage?.cores
        if (firstStage != null) {
            for (first in firstStage) {
                val itemView = LayoutInflater.from(this)
                    .inflate(R.layout.first_stage_item, binding.llFirstStage, false)
                val tvflight = itemView.findViewById<TextView>(R.id.tvFlight)
                val tvcs = itemView.findViewById<TextView>(R.id.tvCs)
                tvflight.text=first?.flight.toString()
                tvcs.text=first?.coreSerial
                binding.llFirstStage.addView(itemView)
            }
        }

        binding.tvWiki.setOnClickListener {
            val url = data.links?.wikipedia ?: return@setOnClickListener
            openBrowser(url)
        }
        binding.tvYoutube.setOnClickListener {
            val url = data.links?.videoLink ?: return@setOnClickListener
            openBrowser(url)
        }
        binding.tvArticle.setOnClickListener {
            val url = data.links?.articleLink ?: return@setOnClickListener
            openBrowser(url)
        }
    }


    @SuppressLint("NewApi")
    private fun getDate(launchDateUtc: String?): String {
        return if (launchDateUtc != null) {
            try {
                val zonedDateTime = ZonedDateTime.parse(launchDateUtc)
                val formatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy", Locale.getDefault())
                zonedDateTime.format(formatter)
            } catch (e: Exception) {
                "Invalid date format"
            }
        } else {
            "No date provided"
        }
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            intent.flags = intent.flags or Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No browser app found", Toast.LENGTH_SHORT).show()
        }
    }
}