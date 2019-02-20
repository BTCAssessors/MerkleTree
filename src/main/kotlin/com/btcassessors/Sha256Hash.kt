package com.btcassessors


/**
 * That `Sha256Hash` class represents a valid sha-256 digest.
 */
data class Sha256Hash(val digest: ByteArray) {

    init {
        if (digest.size != 32) {
            throw IllegalArgumentException("Invalid length for a sha256")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is Sha256Hash) {
            return digest.contentEquals(other.digest)
        }
        return false
    }

    override fun hashCode(): Int {
        return digest.contentHashCode()
    }

    override fun toString() = digest.toHexString()
}
