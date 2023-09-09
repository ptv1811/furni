package com.example.furni.di

import com.example.furni.repository.login.LoginRepository
import com.example.furni.repository.login.LoginRepositoryImpl
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
    fun provideLoginRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }
}