package br.ufs.hiring.stone.features.wallet

import io.reactivex.Observable

/**
 *
 * Created by @ubiratanfsoares
 *
 */

interface RetrieveWallet {

    fun execute(): Observable<HomeInformation>

}