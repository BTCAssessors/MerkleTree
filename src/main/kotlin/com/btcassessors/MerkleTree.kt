package com.btcassessors

import com.btcassessors.proof.Proof
import com.btcassessors.proof.ProofItem
import com.btcassessors.proof.ProofItemDirection
import java.security.MessageDigest
import kotlin.math.ceil
import kotlin.math.log2

/**
 * The `MerkleTree` class represents a balanced & binary merkle tree.
 */
class MerkleTree(val leafs: List<Sha256Hash>) {
    private val padding = Sha256Hash(ByteArray(32))
    private var steps: MutableList<List<Sha256Hash>> = mutableListOf()
    val root: Sha256Hash
        get() = steps.last()[0]

    init {
        if (leafs.isEmpty()) {
            throw IllegalArgumentException(
                "A Merkle tree requires at least " +
                        "one element"
            )
        } else if (leafs.size == 1) {
            steps = mutableListOf(listOf(leafs[0]))
        } else {

            steps = mutableListOf()

            val paddingCount = 2.toBigInteger().pow(ceil(log2(leafs.size.toFloat())).toInt()).toInt() - leafs.size
            val finalLeafs = leafs + List(paddingCount, { padding })

            var previousList = finalLeafs
            var currentList: MutableList<Sha256Hash>
            do {
                steps.add(previousList)
                currentList = mutableListOf()

                var idx = 0
                val iterations = previousList.size / 2

                while (currentList.size < iterations) {
                    currentList.add(
                        Sha256Hash(
                            MessageDigest.getInstance("SHA-256").digest(
                                previousList[idx].digest + previousList[idx + 1].digest
                            )
                        )
                    )
                    idx += 2
                }
                previousList = currentList
            } while (currentList.size >= 2)

            steps.add(previousList)
        }
    }

    fun generateProof(leafIndex: Int): Proof {
        if (leafIndex > leafs.size) {
            throw IndexOutOfBoundsException("Leaf index out of range")
        }
        val target = steps[0][leafIndex]

        val proofs: MutableList<ProofItem> = mutableListOf()
        var currentTarget = target

        for (i in 0 until ceil(log2(leafs.size.toFloat())).toInt()) {

            val currentIndex = steps[i].indexOf(currentTarget)

            if (currentIndex % 2 == 0) { //even
                proofs.add(
                    ProofItem(
                        ProofItemDirection.RIGHT,
                        steps[i][currentIndex + 1]
                    )
                )
            } else { //odd
                proofs.add(
                    ProofItem(
                        ProofItemDirection.LEFT,
                        steps[i][currentIndex - 1]
                    )
                )
            }
            currentTarget = steps[i + 1][currentIndex.div(2)]
        }
        return Proof(proofs)
    }
}
