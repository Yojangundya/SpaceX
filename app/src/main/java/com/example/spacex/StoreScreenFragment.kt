package com.example.spacex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.spacex.databinding.FragmentStoreScreenBinding


class StoreScreenFragment : Fragment() {

    private lateinit var binding: FragmentStoreScreenBinding
    private var webViewState: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUrl()
    }


    override fun onPause() {
        super.onPause()
        webViewState = Bundle()
        if (webViewState != null) {
            binding.storeWebView.saveState(webViewState!!)
        }
    }

    override fun onResume() {
        super.onResume()
        if (webViewState != null) {
            binding.storeWebView.restoreState(webViewState!!)
        } else {
            loadUrl()
        }
    }

    private fun loadUrl() {
        val url = "https://www.spacex.com/vehicles/"
        val webViewSettings: WebSettings = binding.storeWebView.settings
        webViewSettings.javaScriptEnabled = true
        webViewSettings.domStorageEnabled = true
        binding.storeWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: android.graphics.Bitmap?
            ) {
                binding.pb.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.pb.visibility = View.GONE
            }
        }
        if (binding.storeWebView.url != url) {
            binding.storeWebView.loadUrl(url)
        }
    }
}