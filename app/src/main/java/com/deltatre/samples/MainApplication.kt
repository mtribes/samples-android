package com.deltatre.samples

import android.app.Application
import com.mtribes.mtspace.Mtribes

class MainApplication : Application() {
    override fun onCreate() {
        with(Mtribes) {
            init(applicationContext)
            client.userTracking = true
            client.sessionLock = false // enable live updates
        }
        super.onCreate()
    }
}