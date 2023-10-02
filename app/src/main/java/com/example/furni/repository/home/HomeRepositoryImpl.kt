package com.example.furni.repository.home

import com.example.furni.data.cart.Cart
import com.example.furni.data.network.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * @author ltvan@fossil.com
 * on 2023-09-11
 *
 * <p>
 * </p>
 */
class HomeRepositoryImpl @Inject constructor(
    private val mDatabase: FirebaseDatabase
) : HomeRepository {

    override suspend fun <T : Any> fetchInformationByClass(
        path: String,
        typeClass: Class<T>
    ): Flow<Resource<T>> = callbackFlow {
        val ref = mDatabase.getReference(path)
        trySendBlocking(Resource.Loading)

        val infoListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(typeClass)?.let { info ->
                    this@callbackFlow.trySendBlocking(Resource.Success(info))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Resource.Failure(error.toException().localizedMessage))
            }
        }

        ref.addValueEventListener(infoListener)

        awaitClose {
            ref.removeEventListener(infoListener)
        }
    }

    override suspend fun <T : Any> fetchListInformationByClass(
        path: String,
        typeClass: Class<T>,
        str: String
    ): Flow<Resource<List<T>>> = callbackFlow {
        val ref = mDatabase.getReference(path)

        val infoListByClass = mutableListOf<T>()

        val infoListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(typeClass)?.let { info ->
                        infoListByClass.add(info)
                    }
                }

                this@callbackFlow.trySendBlocking(Resource.Success(infoListByClass))
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Resource.Failure(error.toException().localizedMessage))
            }
        }

        ref.addValueEventListener(infoListener)

        awaitClose {
            ref.removeEventListener(infoListener)
        }
    }

    override suspend fun fetchCartByUser(
        uid: String
    ): Flow<Resource<List<Cart>>> = callbackFlow {
        val ref = mDatabase.reference.child("Users/").child("$uid/").child("orders/")

        val userCart = mutableListOf<Cart>()

        val userCartListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(Cart::class.java)?.let { info ->
                        userCart.add(info)
                    }
                }

                this@callbackFlow.trySendBlocking(Resource.Success(userCart))
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Resource.Failure(error.toException().localizedMessage))
            }

        }

        ref.addValueEventListener(userCartListener)

        awaitClose {
            ref.removeEventListener(userCartListener)
        }
    }

    override suspend fun removeCartValue(uid: String) {
        mDatabase.reference.child("Users/").child("$uid/").child("orders/").removeValue()
    }
}