package com.marcopettorali.remotepi.network

import timber.log.Timber
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

//commands
const val YOUTUBE_REQUEST_MSG = "YT"
const val BROWSER_REQUEST_MSG = "BW"
const val HIDE_QR_MSG = "HQ"
const val PING_MSG = "PI"
const val TEXT_HEADER = "TX"

//keys
const val KEY_HEADER = "KE"
const val KEY_START = "KS"

//mouse constants
const val MOUSE_BUTTON_LEFT_DOWN_MSG = "BTLD"
const val MOUSE_BUTTON_LEFT_UP_MSG = "BTLU"
const val MOUSE_BUTTON_RIGHT_DOWN_MSG = "BTRD"
const val MOUSE_BUTTON_RIGHT_UP_MSG = "BTRU"
const val MOUSE_WHEEL_DOWN_MSG = "WHD"
const val MOUSE_WHEEL_UP_MSG = "WHU"
const val MOUSE_POSITION_HEADER = "PO"
const val MOUSE_SCREEN_REF_HEADER = "SR"

class NetworkUtils {
    companion object {
        private var NETWORK_ADDRESS: String? = null
        private var NETWORK_PORT: Int? = null

        fun setAddressAndPort(address: String, port: Int) {
            NETWORK_ADDRESS = address
            NETWORK_PORT = port
        }

        @Synchronized
        fun send(msg: String): Int {
            var exitCode = 1
            val thread = Thread(Runnable {
                try {
                    val buffer = msg.toByteArray()
                    val packet = DatagramPacket(
                        buffer,
                        buffer.size,
                        InetAddress.getByName(NETWORK_ADDRESS),
                        NETWORK_PORT!!
                    )
                    DatagramSocket().send(packet)
                } catch (e: Exception) {
                    Timber.i(e)
                    if (NETWORK_ADDRESS == null || NETWORK_PORT == null) {
                        exitCode = 0
                        return@Runnable
                    }
                }
            })
            thread.start()
            return exitCode
        }

        suspend fun checkConnectionWithRemoteServer(): Boolean {
            Timber.i("check")
            try {
                val receiveData = ByteArray(2)
                var serverSocket = NETWORK_PORT?.let { DatagramSocket(it) }
                val receivePacket = DatagramPacket(receiveData, 2)
                send(PING_MSG)
                serverSocket?.receive(receivePacket)
                val sentence = String(receivePacket.getData(), 0, receivePacket.getLength())
                Timber.i(sentence)
                if (sentence == PING_MSG) {
                    return true
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            NETWORK_ADDRESS = null
            NETWORK_PORT = null

            return false
        }
    }
}







