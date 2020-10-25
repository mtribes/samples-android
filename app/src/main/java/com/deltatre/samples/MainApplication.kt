package com.deltatre.samples

import android.app.Application
import com.massive.mtclient.sdk.logs.MtLogger
import com.mtribes.mtspace.Mtribes
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainApplication : Application() {
    override fun onCreate() {
        with(Mtribes) {
            init(applicationContext)
            client.userTracking = true
            client.sessionLock = false // enable live updates

            if (BuildConfig.DEBUG) {
                Timber.plant(DebugTree())

                client.logger = object : MtLogger {
                    override fun error(tag: String, message: String, throwable: Throwable?) {
                        Timber.e(tag, message)
                    }

                    override fun info(tag: String, message: String) {
                        Timber.i(tag, message)
                    }

                    override fun warn(tag: String, message: String) {
                        Timber.w(tag, message)
                    }
                }
            }
        }
        super.onCreate()
    }
}