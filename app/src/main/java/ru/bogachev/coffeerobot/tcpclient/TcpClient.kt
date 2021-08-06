package ru.bogachev.coffeerobot.tcpclient

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

class TcpClient(socketIP_: String = "192.168.0.2", socketPort_: Int = 48569,) {

    var socketIP: String = socketIP_
    var socketPort: Int = socketPort_
    private var isConnected = false

    lateinit var tcpListener: TcpListener

    var receiveTimeout: Int = 500 // 60000
        set(value) {
            if (this::socket.isInitialized){
                socket.soTimeout = value
            }
            field = value
        }

    var connectTimeout: Int = 1000 // 60000

    private lateinit var socket: Socket// = Socket(socketIP, socketPort)
    private lateinit var bufferedReader: BufferedReader// = tcpClient.getInputStream().bufferedReader()
    private lateinit var bufferedWriter: BufferedWriter// = tcpClient.getOutputStream().bufferedWriter()

    private fun writeToStream(message: String) {
        bufferedWriter.write(message + System.getProperty("line.separator"))
        bufferedWriter.flush()
    }

    private fun main() {

        while (true) {
            openSocket()            
            var timeoutErrorCounter = 0
            var ioErrorCounter = 0
            var running = true
            while (running) {
                try {
                    val readMessage = bufferedReader.readLine()
                    tcpListener.onTcpMessageReceived(readMessage)
                    Log.i("Socket", readMessage)
                }
                catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    timeoutErrorCounter += 1
                    Log.i("Socket", "SocketTimeoutException: $timeoutErrorCounter")

                    if (timeoutErrorCounter == 5) {
                        running = false
                        closeSocket()
                        timeoutErrorCounter = 0
                    }
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    ioErrorCounter += 1
                    Log.i("Socket", "IOException: $ioErrorCounter")

                    if (ioErrorCounter == 5) {
                        running = false
                        closeSocket()
                        ioErrorCounter = 0
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    running = false
                    closeSocket()
                }

            }
        }
    }

    private fun openSocket() {
        while (!isConnected) {
            try {
                socket = Socket()//socketIP, socketPort)
                socket.connect(InetSocketAddress(socketIP, socketPort), connectTimeout)
                isConnected = true
            } catch (e: SocketTimeoutException)
            {
                e.printStackTrace()
                Log.i("Socket", "Unable to connect")
            }
        }
        socket.soTimeout = receiveTimeout
        bufferedReader = socket.getInputStream().bufferedReader()
        bufferedWriter = socket.getOutputStream().bufferedWriter()

        tcpListener.onTcpConnectionStatusChanged(true)
    }

    private fun closeSocket() {
        socket.close()
        bufferedReader.close();
        bufferedWriter.close();
        isConnected = false
        tcpListener.onTcpConnectionStatusChanged(false)
    }

    fun run() {
        thread(start = true) {
            main()
        }
    }
}