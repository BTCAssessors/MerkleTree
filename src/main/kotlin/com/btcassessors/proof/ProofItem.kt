package com.btcassessors.proof

import com.btcassessors.Sha256Hash


data class ProofItem(val direction: ProofItemDirection, val hash: Sha256Hash)
