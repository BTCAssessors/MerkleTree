package com.btcassessors.proof

import com.btcassessors.Sha256Hash
import java.security.MessageDigest

data class Proof(val proofItems: List<ProofItem>) {

    fun validates(merkleRoot: Sha256Hash, targetHash: Sha256Hash): Boolean {
        if (proofItems.isEmpty()) {
            return (targetHash == merkleRoot)
        }

        var proofHash = targetHash

        for (item in proofItems) {

            when (item.direction) {
                ProofItemDirection.LEFT -> proofHash =
                    Sha256Hash(
                        MessageDigest.getInstance("SHA-256").digest(
                            item.hash.digest + proofHash.digest
                        )
                    )
                ProofItemDirection.RIGHT -> proofHash =
                    Sha256Hash(
                        MessageDigest.getInstance("SHA-256").digest(
                            proofHash.digest + item.hash.digest
                        )
                    )
            }
        }
        return proofHash == merkleRoot
    }
}
