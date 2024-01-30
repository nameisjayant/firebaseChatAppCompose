package com.nameisjayant.chatapp.di

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nameisjayant.chatapp.R
import com.nameisjayant.chatapp.utils.DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRealtimeDb(): DatabaseReference =
        Firebase.database.reference.child(DATABASE)

    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesBeginSignInRequest(
        @ApplicationContext context: Context
    ): BeginSignInRequest = BeginSignInRequest.builder()
        .run {
            setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string._516813890892_2egilntk35kgpmln5r1t063g8emg5ab8_apps_googleusercontent_com))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            setAutoSelectEnabled(true)
        }.build()

    @Provides
    @Singleton
    fun providesSignInClient(
        @ApplicationContext context: Context
    ): SignInClient = Identity.getSignInClient(context)

}