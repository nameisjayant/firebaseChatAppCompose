package com.nameisjayant.chatapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.nameisjayant.chatapp.BaseApplication
import com.nameisjayant.chatapp.data.model.AuthModel
import com.nameisjayant.chatapp.utils.PreferenceStore
import com.nameisjayant.chatapp.utils.ResultState
import com.nameisjayant.chatapp.utils.USER_TABLE
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val authDb: FirebaseAuth,
    private val db: DatabaseReference,
    private val preferenceStore: PreferenceStore
) {

    suspend fun registerAccountWithEmailPassword(
        auth: AuthModel
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        authDb.createUserWithEmailAndPassword(
            auth.email ?: "", auth.password ?: ""
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                BaseApplication.scope.launch {
                    preferenceStore.setPref(PreferenceStore.userId, authDb.currentUser?.uid ?: "")
                    preferenceStore.setPref(PreferenceStore.email, auth.email ?: "")
                }
                trySend(ResultState.Success("User created successfully"))
            }
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

    suspend fun loginWithEmailPassword(auth: AuthModel): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        authDb.signInWithEmailAndPassword(
            auth.email ?: "",
            auth.password ?: ""
        ).addOnSuccessListener {
            BaseApplication.scope.launch {
                preferenceStore.setPref(PreferenceStore.userId, authDb.currentUser?.uid ?: "")
                preferenceStore.setPref(PreferenceStore.email, auth.email ?: "")
            }
            trySend(ResultState.Success("Login Successfully"))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }

    suspend fun addUserDetail(data: AuthModel, id: String): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            db.child(USER_TABLE).child(id)
                .setValue(
                    data
                ).addOnCompleteListener {
                    if (it.isSuccessful)
                        trySend(ResultState.Success("Data inserted Successfully.."))
                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }

            awaitClose {
                close()
            }
        }

    suspend fun registerWithGoogle(data: AuthModel): Flow<ResultState<AuthModel>> = callbackFlow {
        trySend(ResultState.Loading)

    }

}
