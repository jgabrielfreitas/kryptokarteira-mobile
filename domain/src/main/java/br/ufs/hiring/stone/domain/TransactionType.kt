package br.ufs.hiring.stone.domain

import java.lang.IllegalArgumentException

/**
 *
 * Created by @ubiratanfsoares
 *
 */

sealed class TransactionType {
    object Sell : TransactionType()
    object Buy : TransactionType()

    override fun toString(): String {
        return when (this) {
            is Buy -> "buy"
            is Sell -> "sell"
        }
    }

    companion object From {
        operator fun invoke(type: String): TransactionType = when (type) {
            "buy" -> Buy
            "sell" -> Sell
            else -> throw IllegalArgumentException("Unknow transaction type")
        }
    }
}
