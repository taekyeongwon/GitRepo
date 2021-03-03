package com.emgram.kr.tmaas.myapp.core.alert

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import com.emgram.kr.tmaas.myapp.R

class MyProgressDialog {
    companion object {
        var dialog : AlertDialog? = null
        fun showAlert(context: Context) {
            if (dialog != null) {
                dialog?.dismiss()
                dialog = null
            } else {

                val builder = AlertDialog.Builder(context, R.style.ProgressDialogTheme)
                    .apply {
                        setView(R.layout.alert_progress)
                        setCancelable(false)
                    }
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    hide()
                }, 15000)

                dialog = builder.create().also { it.show() }
            }
        }

        fun hide() {
            dialog?.dismiss()
            dialog = null
        }
    }
}