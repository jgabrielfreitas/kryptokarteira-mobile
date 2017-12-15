package br.ufs.hiring.stone.features.onboarding

import br.ufs.architecture.core.infrastructure.errorhandlers.InfraErrorsHandler
import br.ufs.hiring.stone.data.storage.WalletStorage
import br.ufs.hiring.stone.data.webservice.KryptoKarteiraWebService
import br.ufs.hiring.stone.data.webservice.models.WalletPayload
import br.ufs.hiring.stone.features.onboarding.GiveawayStatus.Available
import br.ufs.hiring.stone.features.onboarding.GiveawayStatus.Received
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 *
 * Created by @ubiratanfsoares
 *
 */

class OnboardingInfrastructure(
        private val storage: WalletStorage,
        private val webService: KryptoKarteiraWebService,
        private val worker: Scheduler = Schedulers.trampoline()) : ReclaimGiveaway {

    override fun checkStatus(): Observable<GiveawayStatus> {
        return Observable.just(storage.retrieveOwner())
                .map {
                    when (it) {
                        WalletStorage.NO_WALLET -> Available
                        else -> Received
                    }
                }
    }

    override fun now(): Completable {
        return webService.newWallet()
                .subscribeOn(worker)
                .compose(InfraErrorsHandler())
                .map { it as WalletPayload }
                .doOnNext { storage.storeOwner(it.owner) }
                .ignoreElements()
    }

}