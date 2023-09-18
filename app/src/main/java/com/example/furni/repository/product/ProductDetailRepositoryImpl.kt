package com.example.furni.repository.product

import com.example.furni.data.network.Resource
import com.example.furni.data.product.Product
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
 * on 2023-09-18
 *
 * <p>
 * </p>
 */
class ProductDetailRepositoryImpl @Inject constructor(
    private val mDatabase: FirebaseDatabase
) : ProductDetailRepository {

    override suspend fun getProductDetail(productUID: String): Flow<Resource<Product>> =
        callbackFlow {
            val ref = mDatabase.getReference("Product")
                .child(productUID)

            val productDetailListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(Product::class.java)?.let {
                        this@callbackFlow.trySendBlocking(Resource.Success(it))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Resource.Failure(error.toException().localizedMessage))
                }

            }

            ref.addValueEventListener(productDetailListener)

            awaitClose {
                ref.removeEventListener(productDetailListener)
            }
        }
}