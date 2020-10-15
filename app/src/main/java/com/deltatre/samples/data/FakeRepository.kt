package com.deltatre.samples.data

import com.deltatre.samples.ui.UiModel
import io.reactivex.Single

class FakeRepository {
    fun signIn(user: FakeUser): Single<UiModel> {
        return FakeApi.signIn(user)
            .map {
                mapToUiModel(true, fakeDto = it)
            }
    }

    fun signOut(): Single<UiModel> {
        return FakeApi.signOut()
            .map {
                mapToUiModel(isSignedInUser = false, fakeDto = it)
            }
    }

    private fun mapToUiModel(isSignedInUser: Boolean, fakeDto: FakeDto): UiModel {
        return UiModel(
            isSignedIn = isSignedInUser,
            header = mapHeader(fakeDto),
            body = mapBody(fakeDto)
        )
    }

    private fun mapHeader(fakeDto: FakeDto): Header {
        return fakeDto.header
    }

    private fun mapBody(fakeDto: FakeDto): List<FakeRow> {
        return fakeDto.fakeRows
    }
}
