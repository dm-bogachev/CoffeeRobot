package ru.bogachev.coffeerobot

import android.content.SharedPreferences

class TcpParameters {
    var tcpSocketIP = "192.168.0.2"
    var tcpSocketPort = 48569
    var tcpSocketReceiveTimeout = 5000
    var tcpSocketConnectTimeout = 5000

    private var appPreferencesTcpSocketIP = "tcpClientIP"
    private var appPreferencesTcpSocketPort = "tcpClientPort"
    private var appPreferencesTcpSocketReceiveTimeout = "tcpClientReceiveTimeout"
    private var appPreferencesTcpSocketConnectTimeout = "tcpClientConnectTimeout"

    fun load(preferences: SharedPreferences) {
        tcpSocketIP = preferences.getString(appPreferencesTcpSocketIP, "192.168.0.2").toString()
        tcpSocketPort = preferences.getInt(appPreferencesTcpSocketPort, 48569)
        tcpSocketConnectTimeout = preferences.getInt(appPreferencesTcpSocketConnectTimeout, 5000)
        tcpSocketReceiveTimeout = preferences.getInt(appPreferencesTcpSocketReceiveTimeout, 5000)
    }

    fun save(preferences: SharedPreferences) {
        val editor = preferences.edit()
        editor.putString(appPreferencesTcpSocketIP, tcpSocketIP).apply()
        editor.putInt(appPreferencesTcpSocketPort, tcpSocketPort).apply()
        editor.putInt(appPreferencesTcpSocketReceiveTimeout, tcpSocketReceiveTimeout).apply()
        editor.putInt(appPreferencesTcpSocketConnectTimeout, tcpSocketConnectTimeout).apply()

    }
}
