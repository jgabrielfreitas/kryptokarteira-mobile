package br.ufs.hiring.stone.features.onboarding

import io.reactivex.Completable
import io.reactivex.Observable

/**
 *
 * Created by @ubiratanfsoares
 *
 */

interface ReclaimGiveaway {

    fun now(): Completable

    fun checkStatus(): Observable<GiveawayStatus>

}