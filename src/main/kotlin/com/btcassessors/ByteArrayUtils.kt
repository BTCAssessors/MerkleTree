package com.btcassessors

fun ByteArray.toHexString() = joinToString("") { String.format("%02x", it) }


fun String.toHexByteArray(): ByteArray {
    val result = ByteArray(length / 2, { 0 })

    for (i in 0 until length step 2) {
        val byte = Integer.valueOf(substring(i, i + 2), 16).toByte()
        result[i / 2] = byte
    }
    return result
}
