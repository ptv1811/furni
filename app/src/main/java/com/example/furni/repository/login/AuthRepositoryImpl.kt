package com.example.furni.repository.login

import com.bumptech.glide.load.HttpException
import com.example.furni.data.AuthState
import com.example.furni.data.network.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val mAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(userName: String, password: String): Flow<Resource<AuthState>> =
        flow {
            emit(Resource.Loading)

            try {
                val result = mAuth.signInWithEmailAndPassword(userName, password).await()
                emit(result.user?.let {
                    Resource.Success(AuthState(it))
                }!!)
            } catch (e: HttpException) {
                emit(Resource.Failure(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Failure(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Failure(message = e.localizedMessage ?: ""))
            }
        }

    override suspend fun register(userName: String, password: String): Flow<Resource<AuthState>> =
        flow {
            emit(Resource.Loading)

            try {
                val result = mAuth.createUserWithEmailAndPassword(userName, password).await()

                emit(result.user?.let {
                    Resource.Success(AuthState(it))
                }!!)
            } catch (e: HttpException) {
                emit(Resource.Failure(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Failure(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Failure(message = e.localizedMessage ?: ""))
            }
        }

    override suspend fun logout() {
        mAuth.signOut()
    }
}