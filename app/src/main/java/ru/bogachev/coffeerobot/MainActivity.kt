package ru.bogachev.coffeerobot

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import ru.bogachev.coffeerobot.tcpclient.TcpClient
import ru.bogachev.coffeerobot.tcpclient.TcpListener


class MainActivity : AppCompatActivity(), TcpListener {

    private lateinit var preferences: SharedPreferences
    private var tcpParameters: TcpParameters = TcpParameters()

    private lateinit var tcpClient: TcpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        tcpParameters.load(preferences)
        tcpClientInitialization()


    }

    override fun onTcpMessageReceived(message: String?) {
        runOnUiThread {
            val v = findViewById<TextView>(R.id.testConnectionMsg)
            v.text = message

        }
    }

    override fun onTcpConnectionStatusChanged(isConnectedNow: Boolean) {
        runOnUiThread {
            val v = findViewById<TextView>(R.id.testConnectionStatus)
            if (isConnectedNow) {
                v.text = getString(R.string.status_connected)
            } else
            {
                v.text = getString(R.string.status_disconnected)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tcpParameters.load(preferences)
        tcpClient.socketIP = tcpParameters.tcpSocketIP
        tcpClient.socketPort = tcpParameters.tcpSocketPort
        tcpClient.connectTimeout = tcpParameters.tcpSocketConnectTimeout
        tcpClient.receiveTimeout = tcpParameters.tcpSocketReceiveTimeout
    }

    private fun tcpClientInitialization() {

        tcpClient = TcpClient(tcpParameters.tcpSocketIP, tcpParameters.tcpSocketPort)
        tcpClient.connectTimeout = tcpParameters.tcpSocketConnectTimeout
        tcpClient.receiveTimeout = tcpParameters.tcpSocketReceiveTimeout
        tcpClient.tcpListener = this
        tcpClient.run()
    }

    @Suppress("UNUSED_PARAMETER")
    fun testSend(v: View)
    {
        val text: EditText = findViewById(R.id.editTextTextPersonName)
        tcpClient.writeToStream(text.text.toString())
    }

    @Suppress("UNUSED_PARAMETER")
    fun testClick(v: View) {
        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
        startActivity(intent)
    }
}