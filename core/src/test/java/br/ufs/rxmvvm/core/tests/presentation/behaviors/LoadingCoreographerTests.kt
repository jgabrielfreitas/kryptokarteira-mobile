package br.ufs.rxmvvm.core.tests.presentation.behaviors

import br.ufs.rxmvvm.core.errors.InfrastructureError
import br.ufs.rxmvvm.core.presentation.loading.LoadingCoreographer
import br.ufs.rxmvvm.core.presentation.loading.LoadingView
import br.ufs.rxmvvm.core.tests.util.SilentObserver
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 *
 * Created by @ubiratanfsoares
 *
 */
class LoadingCoreographerTests {

    val uiScheduler = Schedulers.trampoline()
    lateinit var coreographer: LoadingCoreographer
    lateinit var showAction: Action
    lateinit var hideAction: Action

    @Before fun `before each test`() {
        showAction = mock()
        hideAction = mock()

        val view = object : LoadingView {
            override fun showLoading(): Action {
                return showAction
            }

            override fun hideLoading(): Action {
                return hideAction
            }
        }

        coreographer = LoadingCoreographer(view, uiScheduler)
    }

    @Test fun `should coordinate loading when flow emmits`() {
        Observable.just("A", "B", "C")
                .compose(coreographer)
                .subscribe()

        `checking loading coordinated`()
    }

    @Test fun `should coordinate loading when flow is empty`() {
        Observable.empty<Any>()
                .compose(coreographer)
                .subscribe()

        `checking loading coordinated`()
    }

    @Test fun `should coordinate loading when flow signal error`() {
        Observable.error<Any>(InfrastructureError.RemoteSystemDown)
                .compose(coreographer)
                .subscribe(SilentObserver)

        `checking loading coordinated`()
    }

    private fun `checking loading coordinated`() {
        val inOrder = Mockito.inOrder(showAction, hideAction)
        inOrder.verify(showAction).run()
        inOrder.verify(hideAction).run()
    }

}