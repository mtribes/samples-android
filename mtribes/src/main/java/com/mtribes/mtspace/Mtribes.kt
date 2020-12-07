// Code generated by mtribes. DO NOT EDIT.

package com.mtribes.mtspace

import android.content.Context
import androidx.annotation.NonNull
import com.massive.mtclient.sdk.Broker
import com.massive.mtclient.sdk.Client
import com.massive.mtclient.sdk.Session
import com.mtribes.mtspace.collection.HomepageCollection

/**
 * The entry point into the mtribes platform via the Android SDK
 */
object Mtribes {
    /**
     * Client instance of an mtribes Space.
     */
    @JvmField
    val client = Client(apiKey = "6ce409e6860440b18f581233cd41b4ca", fallbacks = fallbacks)

    /**
     * The active users session.
     */
    @JvmField
    val session = client.session

    /**
     * mtribes Collection Template instances.
     */
    @JvmField
    val collections = Collections(session)

    /**
     * Entry point to the SDK startup flow.
     * By calling this the [client] will be initialised and users active [session] will be enabled.
     *
     * @param context here we always make it a point to grab the application Context
     * to avoid memory leaks if the user accidentally passes an activity instance etc.
     *
     * @see Client.init
     */
    @JvmStatic
    fun init(@NonNull context: Context) = client.init(context)
}

class Collections internal constructor(session: Session) {

    private val broker: Broker = Broker(session)

    @JvmField
    val homepage: HomepageCollection = HomepageCollection(
        "wN23m6l",
        listOf("8x6DeEy", "yxE8Q2R"),
        broker
    )
}
