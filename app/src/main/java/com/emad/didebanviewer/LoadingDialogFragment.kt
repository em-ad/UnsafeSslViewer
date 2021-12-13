package com.emad.didebanviewer

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.emad.didebanviewer.databinding.LoadingDialogBinding

class LoadingDialogFragment : DialogFragment() {

    private var message: String? = null
    lateinit var binding: LoadingDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (message == null) message = "منتظر بمانید"
        view.findViewById<TextView>(R.id.magicalTextView_message).text = message
        setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.loading_dialog, container, false)
        return binding.root
    }

    private fun setDialogMessage(message: String?) {
        this.message = message
        if (this::binding.isInitialized)
            binding.magicalTextViewMessage.text = message
    }

    override fun setCancelable(cancelable: Boolean) {
        super.setCancelable(cancelable)
    }

    fun showLoading(message: String?, getChildFragmentManager: FragmentManager) {
//        instance = LoadingDialogFragment()
        getChildFragmentManager.executePendingTransactions()
        this.isCancelable = false
        this.message = message
        if (!isAdded)
            show(getChildFragmentManager, "loadingDialog")
        else setDialogMessage(message)
    }

    fun hideLoading() {
        dismissAllowingStateLoss()
    }

    companion object {
        private var instance: LoadingDialogFragment? = null
        fun getInstance(): LoadingDialogFragment? {
            if (instance == null) instance = LoadingDialogFragment()
            return instance
        }
    }
}