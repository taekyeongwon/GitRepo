package com.emgram.kr.tmaas.myapp

import android.content.Context
import android.os.Process
import androidx.multidex.MultiDexApplication
import com.emgram.kr.tmaas.myapp.core.language.LocaleHelper
import com.emgram.kr.tmaas.myapp.core.util.Logger
import java.io.PrintWriter
import java.io.StringWriter

class MainApplication: MultiDexApplication() {

    companion object {
        private var _application: MainApplication? = null
        val application: MainApplication?
        get() {
            return _application?.let {
                it
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        _application = this
        setUncaughtExceptionHandler()
    }

    private fun setUncaughtExceptionHandler() {
        val defaultHandler: Thread.UncaughtExceptionHandler? = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Logger.e(t.name + " : " +getStackTrace(e))

            defaultHandler?.uncaughtException(t, e)
            Process.killProcess(Process.myPid())
            System.exit(0)
        }
    }

    private fun getStackTrace(e : Throwable): String {
        val result = StringWriter()
        val printWriter = PrintWriter(result)

        var th: Throwable? = e
        while(th != null) {
            th.printStackTrace(printWriter)
            th = th.cause
        }

        val stackTraceAsString = result.toString()
        printWriter.close()

        return stackTraceAsString
    }

    override fun attachBaseContext(base: Context) {
        val lang = LocaleHelper.getSelectedLanguage(base, "")
        super.attachBaseContext(LocaleHelper.getUpdateContext(base, lang))
    }
}