package com.btcassessors

import org.junit.Assert
import org.junit.Test

class Sha256HashTest {

    @Test
    fun equals() {
        val a = Sha256Hash(ByteArray(32))
        val b = Sha256Hash(ByteArray(32))

        Assert.assertEquals(a, b)
    }

    @Test(expected = IllegalArgumentException::class)
    fun should_throw_exception_from_invalid_ByteArray() {
        Sha256Hash(ByteArray(31))
    }
}
