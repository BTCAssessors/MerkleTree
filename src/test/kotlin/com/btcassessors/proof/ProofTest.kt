package com.btcassessors.proof

import com.btcassessors.Sha256Hash
import com.btcassessors.toHexByteArray
import org.junit.Assert
import org.junit.Test

class ProofTest {

    @Test
    fun should_not_validate_proof() {
        val root =
            Sha256Hash("b8b1f39aa2e3fc2dde37f3df04e829f514fb98369b522bfb35c663befa896766".toHexByteArray())
        val target =
            Sha256Hash("36e0fd847d927d68475f32a94efff30812ee3ce87c7752973f4dd7476aa2e97e".toHexByteArray())
        val proof = Proof(
            listOf(
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("09096dbc49b7909917e13b795ebf289ace50b870440f10424af8845fb7761ea5".toHexByteArray())
                ),
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("ed2456914e48c1e17b7bd922177291ef8b7f553edf1b1f66b6fc1a076524b22f".toHexByteArray())
                ),
                ProofItem(
                    ProofItemDirection.LEFT,
                    Sha256Hash("eac53dde9661daf47a428efea28c81a021c06d64f98eeabbdcff442d992153a8".toHexByteArray())
                )
            )
        )

        Assert.assertFalse(proof.validates(root, target))
    }

    @Test
    fun should_validate_proof() {
        val root =
            Sha256Hash("0932f1d2e98219f7d7452801e2b64ebd9e5c005539db12d9b1ddabe7834d9044".toHexByteArray())
        val target =
            Sha256Hash("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b".toHexByteArray())
        val proof = Proof(
            listOf(
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35".toHexByteArray())
                ),
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("4e07408562bedb8b60ce05c1decfe3ad16b72230967de01f640b7e4729b49fce".toHexByteArray())
                )
            )
        )
        Assert.assertTrue(proof.validates(root, target))
    }
}
