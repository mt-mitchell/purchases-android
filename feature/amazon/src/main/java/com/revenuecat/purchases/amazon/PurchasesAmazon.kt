package com.revenuecat.purchases.amazon

import android.content.Context
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.Store
import java.util.concurrent.ExecutorService

object PurchasesAmazon {

    fun configure(
        context: Context,
        apiKey: String,
        appUserID: String?,
        observerMode: Boolean,
        service: ExecutorService
    ): Purchases {
        return Purchases.configure(context, apiKey, appUserID, observerMode, service, Store.AMAZON)
    }
}
