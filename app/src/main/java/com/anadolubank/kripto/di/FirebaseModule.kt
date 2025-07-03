package com.anadolubank.kripto.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Bu modüldeki bağımlılıklar uygulamanın tüm yaşam döngüsü boyunca kullanılabilir olacak.
object AppModule {

    @Provides
    @Singleton // FirebaseAuth nesnesinin uygulama boyunca tek bir örneği olmasını sağlarız.
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}