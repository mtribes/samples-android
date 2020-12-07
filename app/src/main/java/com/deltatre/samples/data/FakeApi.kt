package com.deltatre.samples.data

import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Fake API which represents sign in/ sign out work flows
 */
object FakeApi {

    fun signIn(user: FakeUser): Single<FakeDto> {
        return Single.just(user)
            .map { FakeData.singedUser }
            .delay(1, TimeUnit.SECONDS) // add a fake delay
    }

    fun signOut(): Single<FakeDto> {
        return Single.just(true)
            .map { FakeData.anonymous }
            .delay(1, TimeUnit.SECONDS) // add a fake delay
    }
}