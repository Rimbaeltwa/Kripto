package com.anadolubank.kripto.event

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

fun logScreenView(firebaseAnalytics: FirebaseAnalytics, eventName: String, screenName: String, screenClass: String) {
    val bundle = Bundle().apply {
        putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
    }
    firebaseAnalytics.logEvent(eventName, bundle)
    Log.d("FirebaseAnalytics", "$eventName olayı kaydedildi: $screenName")
}

// Yardımcı fonksiyon: Kripto detay görüntüleme olayını kaydeder
fun logCryptoDetailViewed(firebaseAnalytics: FirebaseAnalytics, cryptoId: String?) {
    val bundle = Bundle().apply {
        putString("crypto_id", cryptoId)
    }
    firebaseAnalytics.logEvent("crypto_detail_viewed", bundle) // Özel olay adı
    Log.d("FirebaseAnalytics", "crypto_detail_viewed olayı kaydedildi: $cryptoId")
}