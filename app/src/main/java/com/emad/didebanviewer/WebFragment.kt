package com.emad.didebanviewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.emad.didebanviewer.databinding.FragmentWebBinding
import android.net.http.SslError
import android.os.Looper
import android.util.Log

import android.webkit.SslErrorHandler
import android.webkit.WebSettings

import android.webkit.WebView

import android.webkit.WebViewClient


class WebFragment : Fragment() {

    lateinit var binding: FragmentWebBinding
    var loadingDialogFragment: LoadingDialogFragment = LoadingDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WebFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ww.webViewClient = SSLTolerentWebViewClient(object: PageLoadCallback{
            override fun pageLoaded() {
                loadPage2()
            }
        })
        binding.ww3.webViewClient = SSLTolerentWebViewClient(object: PageLoadCallback{
            override fun pageLoaded() {
                loadingDialogFragment.hideLoading()
            }
        })

        binding.ww.settings.javaScriptEnabled = true
        binding.ww.settings.setSupportZoom(true)
        binding.ww.settings.domStorageEnabled = true
        binding.ww.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.ww.settings.useWideViewPort = true
        binding.ww.settings.loadWithOverviewMode = true
        binding.ww.settings.pluginState = WebSettings.PluginState.ON_DEMAND

        binding.ww3.settings.javaScriptEnabled = true
        binding.ww3.settings.domStorageEnabled = true
        binding.ww3.settings.setSupportZoom(true)
        binding.ww3.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.ww3.settings.useWideViewPort = true
        binding.ww3.settings.loadWithOverviewMode = true
        binding.ww3.settings.pluginState = WebSettings.PluginState.ON_DEMAND

        binding.ww.loadUrl("https://back.didban.simorgh.space")
        loadingDialogFragment.showLoading("در حال آماده سازی سیستم", childFragmentManager)

    }

    private fun loadPage2() {
        loadingDialogFragment.showLoading("در حال بارگذاری پنل دوربین ها", childFragmentManager)
        binding.ww3.loadUrl("https://didban.simorgh.space")
        binding.ww3.visibility = View.VISIBLE
    }

    private class SSLTolerentWebViewClient(val callback: PageLoadCallback) : WebViewClient() {
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            callback.pageLoaded()
            super.onPageFinished(view, url)
        }
    }
}