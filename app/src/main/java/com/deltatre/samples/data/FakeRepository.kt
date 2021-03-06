package com.deltatre.samples.data

import com.deltatre.samples.ui.UiModel
import com.deltatre.samples.ui.fakeUser
import com.massive.mtclient.sdk.StartOptions
import com.mtribes.mtspace.Mtribes
import com.mtribes.mtspace.experience.BannerExperience
import com.mtribes.mtspace.experience.HeroExperience
import com.mtribes.mtspace.section.HomepageBodySection
import io.reactivex.Flowable
import io.reactivex.Single

class FakeRepository {
    // header Experience
    private val headerExp = Mtribes.collections.homepage.header

    // body Section
    private val bodySection = Mtribes.collections.homepage.body

    /**
     * Using `Session.start()` to identify a user as a part of the user sign in workflow
     */
    fun signIn(user: FakeUser): Single<UiModel> {
        return FakeApi.signIn(user)
            .zipWith(Mtribes.session.start(
                StartOptions(
                    userId = user.id,
                    fields = mutableMapOf("subscription" to user.subscription) // `subscription` is added here as a contextual property
                )
            ), { t1, t2 -> t1 })
            .map {
                mapToUiModel(true, fakeDto = it)
            }
    }

    /**
     * Using `Session.start()` to annonymise a user as a part of the user sign out workflow
     */
    fun signOut(): Single<UiModel> {
        return FakeApi.signOut()
            .zipWith(Mtribes.session.start(), { t1, t2 -> t1 })
            .map {
                mapToUiModel(isSignedInUser = false, fakeDto = it)
            }
    }

    /**
     * Susbcribe/listen to `Experience` changes in real time
     */
    fun onHeaderChanges(): Flowable<Header> {
        return headerExp.changed().map {
            mapHeader()
        }
    }

    /**
     * Susbcribe/listen to `Section` changes in real time
     */
    fun onBodyChanges(): Flowable<List<FakeRow>> {
        return bodySection.changed().map {
            mapBody()
        }
    }

    /**
     * `Experience.track()` usage where an item click event is
     * captured on any of the experiences scheduled in the
     * `HomepageBodySection`
     */
    fun captureClickEvent(fakeRow: FakeRow) {
        bodySection.children.single {
            it.id == fakeRow.id
        }.also {
            it.track("item/clicked")
        }
    }

    private fun mapToUiModel(isSignedInUser: Boolean, fakeDto: FakeDto? = null): UiModel {
        return UiModel(
            isSignedIn = isSignedInUser,
            header = mapHeader(),
            body = mapBody()
        )
    }

    private fun mapHeader(): Header {
        return Header(
            id = headerExp.id,
            title = if (!Mtribes.session.isAnonymous) "Hi ${fakeUser.name}" else "Welcome",
            btnTitle = if (Mtribes.session.isAnonymous) "Sign In" else "Sign Out",
            isVisible = headerExp.enabled,
            backgroundColor = headerExp.data.backgroundColor?.value ?: "#6F58C4",
            gradientColor = headerExp.data.gradientColor?.value ?: "#2363AE"
        )
    }

    private fun mapBody(): List<FakeRow> {
        return bodySection.children
            .filter { it.enabled }
            .map {
                when (it::class) {
                    HomepageBodySection.Supported.BANNER.type -> {
                        it as BannerExperience
                        FakeRow(
                            id = it.id,
                            type = FakeRow.Type.BANNER,
                            isVisible = it.enabled,
                            banner = FakeRow.Banner(
                                text = it.data.label ?: ""
                            )
                        )
                    }

                    HomepageBodySection.Supported.HERO.type -> {
                        it as HeroExperience
                        FakeRow(
                            id = it.id,
                            type = FakeRow.Type.HERO,
                            isVisible = it.enabled,
                            hero = FakeRow.Hero(
                                imageUrl = it.data.source ?: ""
                            )
                        )
                    }
                    else -> {
                        FakeRow(
                            id = "",
                            type = FakeRow.Type.BANNER,
                            isVisible = false
                        )
                    }
                }
            }
    }
}

