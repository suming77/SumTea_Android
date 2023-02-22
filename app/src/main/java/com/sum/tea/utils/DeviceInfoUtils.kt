package com.sum.tea.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import com.sum.tea.utils.log.LogUtil
import java.io.FileInputStream
import java.net.NetworkInterface

/**
 * 设备信息的单例工具类
 */
object DeviceInfoUtils {
    private lateinit var appContext: Context

    // 设备imei号
    var imei: String = ""
        private set

    // 设备imsi号
    var imsi: String = ""
        private set

    // androidId
    var androidId: String = ""
        private set

    // 设备mac地址
    var mac: String = ""
        private set

    // WiFi mac地址
    var wifiMacAddress: String = ""
        private set

    // WiFi ssid号
    var wifiSSID: String = ""
        private set
    var phoneModel: String = ""
        private set
    var phoneBrand: String = ""
        private set
    var phoneManufacturer: String = ""
        private set
    var phoneDevice: String = ""
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
        initDeviceInfo()
    }

    /**
     * 刷新设备信息：
     */
    private fun initDeviceInfo() {
        initImei()
        initMac()
        initAndroidId()
        phoneModel = Build.MODEL
        phoneBrand = Build.BRAND
        phoneManufacturer = Build.MANUFACTURER
        phoneDevice = Build.DEVICE
    }

    /**
     * 初始化AndroidId
     */
    @SuppressLint("HardwareIds")
    private fun initAndroidId() {
        if (androidId.isNotEmpty()) {
            return
        }
        LogUtil.d("init device androidId")
        val tmpAndroidId = Settings.Secure.getString(
            appContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        if (!tmpAndroidId.isNullOrEmpty()) {
            androidId = tmpAndroidId.lowercase()
        }
    }

    /**
     * 初始化Mac地址
     */
    private fun initMac() {
        if (mac.isNotEmpty() || wifiMacAddress.isNotEmpty() || wifiSSID.isNotEmpty()) {
            return
        }
        try {
            LogUtil.d("init device mac, wifiMac and ssid")
            val wm = appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            initMacAddress(wm)
            if (!wm.connectionInfo.bssid.isNullOrEmpty()) {
                wifiMacAddress = wm.connectionInfo.bssid
            }
            if (!wm.connectionInfo.ssid.isNullOrEmpty()) {
                wifiSSID = wm.connectionInfo.ssid
            }
        } catch (e: Exception) {
            LogUtil.d("init mac failure", throwable = e)
        }
    }

    @SuppressLint("HardwareIds")
    private fun initMacAddress(vm: WifiManager) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // 6.0以前可以从wifiInfo中获取mac地址
                val wifiInfo = vm.connectionInfo
                if (wifiInfo != null && wifiInfo.macAddress != null) {
                    mac = wifiInfo.macAddress.lowercase()
                }
            } else {
                // 6.0~7.0读取设备文件获取
                val arrStrings = arrayOf("/sys/class/net/wlan0/address", "/sys/devices/virtual/net/wlan0/address")
                for (str in arrStrings) {
                    mac = readFile(str)
                    break
                }
                if (mac.isNotEmpty()) {
                    return
                }
                // 7.0及以上通过以下方式获取
                val interfaces = NetworkInterface.getNetworkInterfaces() ?: return
                while (interfaces.hasMoreElements()) {
                    val netInfo = interfaces.nextElement()
                    if ("wlan0" == netInfo.name) {
                        val addresses = netInfo.hardwareAddress
                        if (addresses == null || addresses.isEmpty()) {
                            continue
                        }
                        mac = macByte2String(addresses)
                        break
                    }
                }
            }
        } catch (e: Exception) {
            LogUtil.d("read mac failure", throwable = e)
        }
    }


    /**
     * 初始化imei、imsi
     */
    @SuppressLint("MissingPermission")
    private fun initImei() {
        if (imei.isNotEmpty() || imsi.isNotEmpty()) {
            return
        }
        if (appContext.packageManager.checkPermission(
                    android.Manifest.permission.READ_PHONE_STATE,
                    appContext.packageName
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        try {
            LogUtil.d("init device imei and imsi")
            val tm = appContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var tmpImei = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tmpImei = tm.imei
            } else {
                @Suppress("DEPRECATION")
                tmpImei = tm.deviceId
            }
            if (tmpImei.isNotEmpty()) {
                imei = tmpImei.lowercase()
            }
            val tmpImsi = tm.subscriberId
            if (!tmpImsi.isNullOrEmpty()) {
                imsi = tmpImsi.lowercase()
            }
        } catch (e: Exception) {
            LogUtil.d(message = "initImei exception, msg=${e.message}")
        }
    }

    /**
     * 将mac的byte数组转化为string
     */
    private fun macByte2String(bytes: ByteArray): String {
        val buf = StringBuffer()
        for (b in bytes) {
            buf.append(String.format("%02X:", b))
        }
        if (buf.isNotEmpty()) {
            buf.deleteCharAt(buf.length - 1)
        }
        return buf.toString()
    }

    /**
     * 从指定的文件内读取内容
     */
    private fun readFile(filePath: String): String {
        var res = ""
        var fin: FileInputStream? = null
        try {
            fin = FileInputStream(filePath)
            val length = fin.available()
            val buffer = ByteArray(length)
            val count = fin.read(buffer)
            if (count > 0) {
                res = String(buffer, Charsets.UTF_8)
            }
        } catch (e: Exception) {
            LogUtil.d("read file exception", throwable = e)
        } finally {
            try {
                fin?.close()
            } catch (e: Exception) {
                LogUtil.d("close FileInputStream failure")
            }
        }
        return res
    }
}