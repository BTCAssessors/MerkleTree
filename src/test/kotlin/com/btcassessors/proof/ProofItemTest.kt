package com.btcassessors.proof

import com.btcassessors.Sha256Hash
import org.junit.Assert.assertEquals
import org.junit.Test

class ProofItemTest {

    @Test
    fun equals() {
        assertEquals(
            ProofItem(
                ProofItemDirection.LEFT,
                Sha256Hash(ByteArray(32))
            ),
            ProofItem(
                ProofItemDirection.LEFT,
                Sha256Hash(ByteArray(32))
            )
        )
    }
}
