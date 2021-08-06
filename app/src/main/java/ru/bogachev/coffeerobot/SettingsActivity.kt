package ru.bogachev.coffeerobot

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


class SettingsActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private var tcpParameters: TcpParameters = TcpParameters()

    private lateinit var ipField: EditText
    private lateinit var portField: EditText
    private lateinit var connectToField: EditText
    private lateinit var receiveToField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        tcpParameters.load(preferences)
        ipFieldInitialization()
        portFieldInitialization()
        connectToFieldInitialization()
        receiveToFieldInitialization()
    }

    override fun onPause() {
        super.onPause()
        tcpParameters.save(preferences)
    }

    override fun onResume() {
        super.onResume()
        tcpParameters.load(preferences)

        ipField.setText(tcpParameters.tcpSocketIP)
        portField.setText(tcpParameters.tcpSocketPort.toString())
        connectToField.setText(tcpParameters.tcpSocketConnectTimeout.toString())
        receiveToField.setText(tcpParameters.tcpSocketReceiveTimeout.toString())
    }

    private fun ipFieldInitialization() {
        ipField = findViewById(R.id.ipField)
        ipField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tcpParameters.tcpSocketIP = ipField.text.toString()
            }
        })
    }

    private fun portFieldInitialization() {
        portField = findViewById(R.id.portField)
        portField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tcpParameters.tcpSocketPort = portField.text.toString().toInt()
            }
        })
    }

    private fun connectToFieldInitialization() {
        connectToField = findViewById(R.id.connectToField)
        connectToField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tcpParameters.tcpSocketConnectTimeout = connectToField.text.toString().toInt()
            }
        })
    }

    private fun receiveToFieldInitialization() {
        receiveToField = findViewById(R.id.receiveToField)
        receiveToField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tcpParameters.tcpSocketReceiveTimeout = receiveToField.text.toString().toInt()
            }
        })
    }

}