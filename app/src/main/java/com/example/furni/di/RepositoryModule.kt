package com.example.furni.di

import com.example.furni.repository.home.HomeRepository
import com.example.furni.repository.home.HomeRepositoryImpl
import com.example.furni.repository.login.AuthRepository
import com.example.furni.repository.login.AuthRepositoryImpl
import com.example.furni.repository.product.ProductDetailRepository
import com.example.furni.repository.product.ProductDetailRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideLoginRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(impl: HomeRepositoryImpl): HomeRepository = impl

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @ViewModelScoped
    fun provideProductDetailRepository(
        impl: ProductDetailRepositoryImpl
    ): ProductDetailRepository = impl
}