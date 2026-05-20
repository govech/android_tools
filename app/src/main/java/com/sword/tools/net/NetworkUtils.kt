package com.sword.tools.net

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

object NetworkUtils {

    fun get(urlString: String): String {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("HTTP error code: ${connection.responseCode}")
            }

            val response = StringBuffer()
            BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    StandardCharsets.UTF_8
                )
            ).use { reader ->
                var inputLine: String?
                while (reader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
            }

            return response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        } finally {
            connection?.disconnect()
        }
    }


    fun post(urlString: String, requestBody: String): String {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true

            val outputStream = connection.outputStream
            BufferedWriter(OutputStreamWriter(outputStream, StandardCharsets.UTF_8)).use { writer ->
                writer.write(requestBody)
            }

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("HTTP error code: ${connection.responseCode}")
            }

            val response = StringBuffer()
            BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    StandardCharsets.UTF_8
                )
            ).use { reader ->
                var inputLine: String?
                while (reader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
            }

            return response.toString()
        } finally {
            connection?.disconnect()
        }
    }
}