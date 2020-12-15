package com.revenuecat.purchases.common

import android.app.Activity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.SkuDetails
import com.revenuecat.purchases.PurchasesError

abstract class BillingAbstract {

    @get:Synchronized
    @set:Synchronized
    @Volatile
    var stateListener: StateListener? = null

    @get:Synchronized
    @Volatile
    var purchasesUpdatedListener: BillingWrapper.PurchasesUpdatedListener? = null
        set(value) {
            synchronized(this@BillingAbstract) {
                field = value
            }
            if (value != null) {
                startConnection()
            } else {
                endConnection()
            }
        }

    interface StateListener {
        fun onConnected()
    }

    abstract fun startConnection()

    abstract fun endConnection()

    abstract fun queryAllPurchases(
        onReceivePurchaseHistory: (List<PurchaseHistoryRecordWrapper>) -> Unit,
        onReceivePurchaseHistoryError: (PurchasesError) -> Unit
    )

    abstract fun querySkuDetailsAsync(
        @BillingClient.SkuType itemType: String,
        skuList: List<String>,
        onReceiveSkuDetails: (List<SkuDetails>) -> Unit,
        onError: (PurchasesError) -> Unit
    )

    abstract fun consumePurchase(
        token: String,
        onConsumed: (billingResult: BillingResult, purchaseToken: String) -> Unit
    )

    abstract fun acknowledge(
        token: String,
        onAcknowledged: (billingResult: BillingResult, purchaseToken: String) -> Unit
    )

    abstract fun findPurchaseInPurchaseHistory(
        @BillingClient.SkuType skuType: String,
        sku: String,
        completion: (BillingResult, PurchaseHistoryRecordWrapper?) -> Unit
    )

    abstract fun makePurchaseAsync(
        activity: Activity,
        appUserID: String,
        skuDetails: SkuDetails,
        replaceSkuInfo: ReplaceSkuInfo?,
        presentedOfferingIdentifier: String?
    )

    abstract fun isConnected(): Boolean

    abstract fun queryPurchases(
        @BillingClient.SkuType skuType: String
    ): BillingWrapper.QueryPurchasesResult?

}