package com.emgram.kr.tmaas.myapp.view

import android.Manifest
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.emgram.kr.tmaas.myapp.R
import com.emgram.kr.tmaas.myapp.base.BaseView
import com.emgram.kr.tmaas.myapp.core.constant.C
import com.emgram.kr.tmaas.myapp.core.custom.view.MainTabItemView
import com.emgram.kr.tmaas.myapp.core.network.Status
import com.emgram.kr.tmaas.myapp.core.util.TMProviderFactory
import com.emgram.kr.tmaas.myapp.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseView<MainViewModel>(), EasyPermissions.PermissionCallbacks {
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override lateinit var viewModel: MainViewModel

    private val tabFragments = arrayOf(HomeFragment(), JourneyFragment(), ReserveFragment(), MyFragment())
    private val PERMS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CALL_PHONE
    )

    override fun initView() {
        viewModel = ViewModelProvider(this, TMProviderFactory()).get(MainViewModel::class.java)

        initTab()
//        viewModel.live2.observe(this, Observer {
//            when(it.status) {
//                Status.SUCCESS -> {
//                    showProgress(false)
//                    Log.d("GitListName", ""+it.data?.items?.get(0)?.fullName)
//                }
//                Status.FAIL -> {
//                    showProgress(false)
//                    showAlert(it.throwable?.getMessage(this) ?: "error")
//                }
//                Status.LOADING -> {
//                    showProgress(true)
//                }
//            }
//        })
    }

    override fun initObserver() {
        viewModel.live.observe(this, Observer {
            it?.let {
                Log.d("GitList", ""+it.items[0].id)
            }
        })
//        viewModel.pushSettingClicked.observe(this, Observer {
//            if(it.first && it.second) {
//                FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
//                    if(!it.isSuccessful) {
//                        return@addOnCompleteListener
//                    }
//                    val token = it.result?.token
//                    Log.d("FCM Token:", token?:"")
//                }
//            }
//            if (it.first && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(PERMS, REQUEST_PERMISSIONS)
//            }
//        })
    }

    override fun initListener() {
    }

    private fun initTab() {
        main_tab.addTab(createTabItem(R.drawable.ic_launcher_background, HomeFragment::class.java.simpleName))
        main_tab.addTab(createTabItem(R.drawable.ic_launcher_background, JourneyFragment::class.java.simpleName))
        main_tab.addTab(createTabItem(R.drawable.ic_launcher_background, ReserveFragment::class.java.simpleName))
        main_tab.addTab(createTabItem(R.drawable.ic_launcher_background, MyFragment::class.java.simpleName))
        main_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.let {
                    replaceFragment(it.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    replaceFragment(it.position)
                }
            }
        })
        selectTab(0)
    }

    private fun selectTab(position: Int) {
        main_tab.selectTab(main_tab.getTabAt(position))
    }

    private fun createTabItem(@DrawableRes icon: Int, tag: String): TabLayout.Tab {
        val tab = main_tab.newTab()
        val view = MainTabItemView(this)
        //view.setName(name)
        view.setIcon(icon)
        tab.customView = view
        tab.tag = tag
        return tab
    }

    private fun replaceFragment(tabIndex: Int) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.main_fl, tabFragments[tabIndex])
        ft.commit()
    }

    private fun checkPermissions() {
        if(!EasyPermissions.hasPermissions(this, *PERMS)) {
//            if(!PreferenceUtil.getBoolean(C.Preference.PERMISSION_SETTING_CHECKED, false)) {
//                //val permissionDlg = PermissionDlg(this)
//                PermissionDlg.showAlert(this, DialogInterface.OnClickListener { _, _ ->
//                    TMAlertDialog.showPushSetting(this,
//                        DialogInterface.OnClickListener { _, _ ->
//                            viewModel.pushSetting(false)
//                        }, DialogInterface.OnClickListener { _, _ ->
//                            viewModel.pushSetting(true)
//                        })
//                })
//            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }
}
