package com.deltatre.samples

import android.app.Application
import com.massive.mtclient.sdk.logs.MtLogger
import com.mtribes.mtspace.Mtribes
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainApplication : Application() {
    override fun onCreate() {
        with(Mtribes) {
            // invoke `init` as the first thing during the integration process
            // and always pass the application context as a good practise.
            // As a precaution even if SDK receives Activity context it will not keep a reference
            // to it but will use the extracted Application context from it.
            init(applicationContext)

            // enable custom analytics
            client.userTracking = true

            // enable live updates
            client.sessionLock = false

            // enables internal SDK logs
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