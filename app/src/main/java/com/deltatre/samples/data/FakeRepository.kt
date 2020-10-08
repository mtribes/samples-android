package com.deltatre.samples.data

import com.deltatre.samples.ui.UiModel
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class FakeRepository {
    fun signIn(user: FakeUser): Single<UiModel> {
        return Single.just(
            FakeData.singedUser
        ).map {
            UiModel(
                isSignedIn = true,
                header = it.header,
                fakeRows = it.fakeRows
            )
        }.delay(1, TimeUnit.SECONDS) // add a fake delay
    }

    fun signOut(): Single<UiModel> {
        return Single.just(
            FakeData.anonymous
        ).map {
            UiModel(
                isSignedIn = false,
                header = it.header,
                fakeRows = it.fakeRows
            )
        }.delay(1, TimeUnit.SECONDS) // add a fake delay
    }
}

data class FakeUser(
    val id: String,
    val name: String,
    val subscription: String
)

data class FakeDto(
    val header: Header,
    val fakeRows: List<FakeRow>
)

data class Header(
    val id: String,
    val title: String = "",
    val isVisible: Boolean = true,
    val backgroundColor: String,
    val gradientColor: String
)

// represent an item in the list
data class FakeRow(
    val id: String,
    val type: Type,
    val isVisible: Boolean = true,
    val hero: Hero? = null,
    val banner: Banner? = null,
) {
    enum class Type {
        HERO,
        BANNER
    }

    data class Hero(
        val id: String,
        val imageUrl: String
    )

    data class Banner(
        val id: String,
        val text: String
    )
}
