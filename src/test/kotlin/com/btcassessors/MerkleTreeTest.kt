package com.btcassessors

import com.btcassessors.proof.Proof
import com.btcassessors.proof.ProofItem
import com.btcassessors.proof.ProofItemDirection
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MerkleTreeTest {

    private val testHash = Sha256Hash(ByteArray(32))

    @Test
    fun should_create_tree_from_one() {
        val expected = "0000000000000000000000000000000000000000000000000000000000000000"
        val mt = MerkleTree(listOf(testHash))

        assertEquals(expected, mt.root.toString())
    }

    @Test
    fun should_create_tree_from_two() {
        val expected = "f5a5fd42d16a20302798ef6ed309979b43003d2320d9f0e8ea9831a92759fb4b"
        val mt = MerkleTree(listOf(testHash, testHash))

        assertEquals(expected, mt.root.toString())
    }


    @Test
    fun should_create_tree_from_five() {
        val expected = "c78009fdf07fc56a11f122370658a353aaa542ed63e44c4bc15ff4cd105ab33c"
        val mt = MerkleTree(
            listOf(
                testHash,
                testHash,
                testHash,
                testHash,
                testHash
            )
        )

        assertEquals(expected, mt.root.toString())
    }

    @Test
    fun should_create_tree_from_seven() {
        val expected = "c78009fdf07fc56a11f122370658a353aaa542ed63e44c4bc15ff4cd105ab33c"
        val mt = MerkleTree(
            listOf(
                testHash,
                testHash,
                testHash,
                testHash,
                testHash,
                testHash,
                testHash
            )
        )

        assertEquals(expected, mt.root.toString())
    }

    @Test
    fun example_from_blockcerts() {
        val expected = "0932f1d2e98219f7d7452801e2b64ebd9e5c005539db12d9b1ddabe7834d9044"
        val mt = MerkleTree(
            listOf(
                Sha256Hash("4295f72eeb1e3507b8461e240e3b8d18c1e7bd2f1122b11fc9ec40a65894031a".toHexByteArray()),
                Sha256Hash("4e07408562bedb8b60ce05c1decfe3ad16b72230967de01f640b7e4729b49fce".toHexByteArray())
            )
        )

        assertEquals(expected, mt.root.toString())
    }

    @Test
    fun should_get_proofs_from_four() {
        var expected = Proof(
            listOf(
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("eac53dde9661daf47a428efea28c81a021c06d64f98eeabbdcff442d992153a8".toHexByteArray())
                ),
                ProofItem(
                    ProofItemDirection.LEFT,
                    Sha256Hash("68b8c08254e7fc70761f640d8efeb950c84c34e10ee396563fe0c5d5cc7499fd".toHexByteArray())
                )
            )
        )
        val mt = MerkleTree(
            listOf(
                Sha256Hash("09096dbc49b7909917e13b795ebf289ace50b870440f10424af8845fb7761ea5".toHexByteArray()),
                Sha256Hash("4e07408562bedb8b60ce05c1decfe3ad16b72230967de01f640b7e4729b49fce".toHexByteArray()),
                Sha256Hash("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b".toHexByteArray()),
                Sha256Hash("eac53dde9661daf47a428efea28c81a021c06d64f98eeabbdcff442d992153a8".toHexByteArray())
            )
        )
        assertEquals(expected, mt.generateProof(2))

        expected = Proof(
            listOf(
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("4e07408562bedb8b60ce05c1decfe3ad16b72230967de01f640b7e4729b49fce".toHexByteArray())
                ),
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("16502e0632af1e18ba4c5876d7dd15e9d5bd7e1e3638c71f86d54b8df636795a".toHexByteArray())
                )
            )
        )
        assertEquals(expected, mt.generateProof(0))
    }

    @Test
    fun should_get_proofs_from_five() {
        val expected = Proof(
            listOf(
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("0000000000000000000000000000000000000000000000000000000000000000".toHexByteArray())
                ),
                ProofItem(
                    ProofItemDirection.RIGHT,
                    Sha256Hash("f5a5fd42d16a20302798ef6ed309979b43003d2320d9f0e8ea9831a92759fb4b".toHexByteArray())
                ),
                ProofItem(
                    ProofItemDirection.LEFT,
                    Sha256Hash("ad48a9792904245741b04ca0f99efaf7fe74b699e26b0f685160d8142fd55619".toHexByteArray())
                )
            )
        )
        val mt = MerkleTree(
            listOf(
                Sha256Hash("09096dbc49b7909917e13b795ebf289ace50b870440f10424af8845fb7761ea5".toHexByteArray()),
                Sha256Hash("4e07408562bedb8b60ce05c1decfe3ad16b72230967de01f640b7e4729b49fce".toHexByteArray()),
                Sha256Hash("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b".toHexByteArray()),
                Sha256Hash("b8b1f39aa2e3fc2dde37f3df04e829f514fb98369b522bfb35c663befa896766".toHexByteArray()),
                Sha256Hash("eac53dde9661daf47a428efea28c81a021c06d64f98eeabbdcff442d992153a8".toHexByteArray())
            )
        )
        assertTrue(expected.equals(mt.generateProof(4)))

    }

    @Test(expected = IllegalArgumentException::class)
    fun should_not_create_empty_tree() {
        MerkleTree(listOf())
    }
}
