package com.deltatre.samples.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deltatre.samples.data.FakeRepository
import com.deltatre.samples.data.FakeRow
import com.deltatre.samples.data.FakeUser
import com.deltatre.samples.data.Header
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

val fakeUser = FakeUser(
    id = "2id2f459d2s5",
    name = "Olivia",
    subscription = "gold"
)

private val fakeRepository = FakeRepository()

class MainViewModel : ViewModel() {

    val mainListLiveData = MutableLiveData<UiModel>()

    private val disposables = CompositeDisposable()

    private val _state
        get() = mainListLiveData.value

    init {
        // subscribe to changes
        listenToHeader()
        listenToBody()

        // fetch anon user on startup
        fetchForAnonUser()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun onBtnClick() {
        if (_state?.isSignedIn == true) {
            fetchForAnonUser()
        } else {
            fetchForKnownUser()
        }
    }

    fun onItemClick(fakeRow: FakeRow) {
        fakeRepository.captureClickEvent(fakeRow)
    }

    private fun fetchForAnonUser() {
        disposables.add(
            fakeRepository.signOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mainListLiveData.value = it
                }, {
                    Timber.e(it, "Error during sign out")
                })
        )
    }

    private fun fetchForKnownUser() {
        disposables.add(
            fakeRepository.signIn(fakeUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mainListLiveData.value = it
                }, {
                    Timber.e(it, "Error during sign in")
                })
        )
    }

    private fun listenToHeader() {
        disposables.add(
            fakeRepository.onHeaderChanges()
                .subscribe({
                    mainListLiveData.value = _state?.copy(
                        header = it
                    )
                }, {
                    Timber.e(it, "Error during Header monitoring")
                })
        )
    }

    private fun listenToBody() {
        disposables.add(
            fakeRepository.onBodyChanges()
                .subscribe({
                    mainListLiveData.value = _state?.copy(
                        body = it
                    )
                }, {
                    Timber.e(it, "Error during Body Section monitoring")
                })
        )
    }
}

data class UiModel(
    val isSignedIn: Boolean,
    val header: Header,
    val body: List<FakeRow>
)