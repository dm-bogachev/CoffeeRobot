package ru.bogachev.coffeerobot.tcpclient

interface TcpListener {
    fun onTcpMessageReceived(message: String?)
    fun onTcpConnectionStatusChanged(isConnectedNow: Boolean)
}