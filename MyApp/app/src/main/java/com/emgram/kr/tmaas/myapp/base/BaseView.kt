package com.emgram.kr.tmaas.myapp.base

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.emgram.kr.tmaas.myapp.MainApplication
import com.emgram.kr.tmaas.myapp.R
import com.emgram.kr.tmaas.myapp.core.alert.MyAlertDialog
import com.emgram.kr.tmaas.myapp.core.alert.MyProgressDialog
import com.emgram.kr.tmaas.myapp.core.language.LocaleHelper
import com.emgram.kr.tmaas.myapp.core.manager.UserInfoManager
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseView<T: BaseViewModel>: AppCompatActivity() {
    companion object {
        var clickable = true
    }
    abstract val layoutResourceId: Int
    abstract var viewModel: T

    abstract fun initView()
    abstract fun initObserver()
    abstract fun initListener()

    private val PERMS_REQUEST_CODE = 0x00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResourceId)

        initView()
        initObserver()
        initListener()

        observeAlert()
        observeProgress()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.getUpdateContext(newBase, UserInfoManager.languageCode))
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        super.applyOverrideConfiguration(LocaleHelper.applyConfigNightMode(baseContext, overrideConfiguration))
    }

    private fun observeAlert() {
        viewModel.alertFlag.observe(this, Observer {
            MyAlertDialog.showAlert(this, it.getMessage(this))
            Log.d("Alert", it.getMessage(this))
        })
    }
//    fun showAlert(errorMessage: String) {
//        MyAlertDialog.showAlert(this, errorMessage)
//    }

    private fun observeProgress() {
        viewModel.progressFlag.observe(this, Observer {
            if(it) {
                MyProgressDialog.showAlert(this)
            } else {
                MyProgressDialog.hide()
            }
        })
    }

//    fun showProgress(isShow: Boolean) {
//        if(isShow) {
//            MyProgressDialog.showAlert(this)
//        } else {
//            MyProgressDialog.hide()
//        }
//    }

    //개별 권한 요청 시 사용
    fun checkPermission(perms: Array<String>, title: String): Boolean {
        return if(EasyPermissions.hasPermissions(this, *perms)) {
            true
        } else {
            if(EasyPermissions.somePermissionPermanentlyDenied(this, perms.toMutableList())) {
//                TMAlertDialog.showAlert(this, call_permission,
//                    MainApplication.getString(R.string.permission_setting_title),
//                    R.string.btn_close, DialogInterface.OnClickListener{ _, _ ->
//                    },
//                    R.string.btn_setting, DialogInterface.OnClickListener{ _, _ ->
//                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+packageName))
//                        intent.addCategory(Intent.CATEGORY_DEFAULT)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                    } )
            } else {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(perms, PERMS_REQUEST_CODE)
                }
            }
            false
        }
    }
}