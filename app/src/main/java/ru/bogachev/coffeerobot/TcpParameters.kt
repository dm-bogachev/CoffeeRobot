package ru.bogachev.coffeerobot

import android.content.SharedPreferences

class TcpParameters {
    var tcpSocketIP = "192.168.0.2"
    var tcpSocketPort = 48569
    var tcpSocketReceiveTimeout = 5000
    var tcpSocketConnectTimeout = 5000

    private var APP_PREFERENCES_tcpSocketIP = "tcpClientIP"
    private var APP_PREFERENCES_tcpSocketPort = "tcpClientPort"
    private var APP_PREFERENCES_tcpSocketReceiveTimeout = "tcpClientReceiveTimeout"
    private var APP_PREFERENCES_tcpSocketConnectTimeout = "tcpClientConnectTimeout"

    fun load(preferences: SharedPreferences) {
        tcpSocketIP = preferences.getString(APP_PREFERENCES_tcpSocketIP, "192.168.0.2").toString()
        tcpSocketPort = preferences.getInt(APP_PREFERENCES_tcpSocketPort, 48569)
        tcpSocketConnectTimeout = preferences.getInt(APP_PREFERENCES_tcpSocketConnectTimeout, 5000)
        tcpSocketReceiveTimeout = preferences.getInt(APP_PREFERENCES_tcpSocketReceiveTimeout, 5000)
    }

    fun save(preferences: SharedPreferences) {
        val editor = preferences.edit()
        editor.putString(APP_PREFERENCES_tcpSocketIP, tcpSocketIP).apply()
        editor.putInt(APP_PREFERENCES_tcpSocketPort, tcpSocketPort).apply()
        editor.putInt(APP_PREFERENCES_tcpSocketReceiveTimeout, tcpSocketReceiveTimeout).apply()
        editor.putInt(APP_PREFERENCES_tcpSocketConnectTimeout, tcpSocketConnectTimeout).apply()

    }
}
