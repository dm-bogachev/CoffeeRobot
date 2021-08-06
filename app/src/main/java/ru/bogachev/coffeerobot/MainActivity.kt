package ru.bogachev.coffeerobot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ru.bogachev.coffeerobot.tcpclient.TcpClient
import ru.bogachev.coffeerobot.tcpclient.TcpListener

class MainActivity : AppCompatActivity(), TcpListener {

    private lateinit var tcpClient: TcpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tcpClient = TcpClient("192.168.0.2", 48569)
        tcpClient.connectTimeout = 10000
        tcpClient.receiveTimeout = 5000
        tcpClient.tcpListener = this
        tcpClient.run()
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
                v.text = "CONNECTED"
            } else
            {
                v.text = "DISCONNECTED"
            }
        }
    }
}