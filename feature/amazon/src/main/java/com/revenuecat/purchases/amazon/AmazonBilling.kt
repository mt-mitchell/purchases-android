package com.revenuecat.purchases.amazon

import android.app.Activity
import android.content.Context
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.PurchasingService
import com.amazon.device.iap.model.ProductDataResponse
import com.amazon.device.iap.model.PurchaseResponse
import com.amazon.device.iap.model.PurchaseUpdatesResponse
import com.amazon.device.iap.model.UserDataResponse
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.SkuDetails
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.common.BillingAbstract
import com.revenuecat.purchases.common.BillingWrapper
import com.revenuecat.purchases.common.ProductInfo
import com.revenuecat.purchases.common.PurchaseHistoryRecordWrapper
import com.revenuecat.purchases.common.ReplaceSkuInfo

class AmazonBilling(
    private val applicationContext: Context
) : BillingAbstract(), PurchasingListener {

    var connected = false

    override fun startConnection() {
        PurchasingService.registerListener(applicationContext, this)
        connected = true
    }

    override fun endConnection() {
    }

    override fun queryAllPurchases(
        onReceivePurchaseHistory: (List<PurchaseHistoryRecordWrapper>) -> Unit,
        onReceivePurchaseHistoryError: (PurchasesError) -> Unit
    ) {
    }

    override fun querySkuDetailsAsync(
        itemType: String,
        skuList: List<String>,
        onReceiveSkuDetails: (List<SkuDetails>) -> Unit,
        onError: (PurchasesError) -> Unit
    ) {
        TODO("not implemented")
    }

    override fun consumePurchase(
        token: String,
        onConsumed: (billingResult: BillingResult, purchaseToken: String) -> Unit
    ) {
        TODO("not implemented")
    }

    override fun acknowledge(
        token: String,
        onAcknowledged: (billingResult: BillingResult, purchaseToken: String) -> Unit
    ) {
        TODO("not implemented")
    }

    override fun findPurchaseInPurchaseHistory(
        skuType: String,
        sku: String,
        completion: (BillingResult, PurchaseHistoryRecordWrapper?) -> Unit
    ) {
        TODO("not implemented")
    }

    override fun makePurchaseAsync(
        activity: Activity,
        appUserID: String,
        productInfo: ProductInfo,
        replaceSkuInfo: ReplaceSkuInfo?
    ) {
        PurchasingService.purchase(productInfo.productID)
    }

    override fun isConnected(): Boolean = connected

    override fun queryPurchases(skuType: String): BillingWrapper.QueryPurchasesResult? {
        TODO()
    }

    override fun onUserDataResponse(response: UserDataResponse?) {
    }

    override fun onProductDataResponse(response: ProductDataResponse?) {
    }

    override fun onPurchaseResponse(response: PurchaseResponse?) {
    }

    override fun onPurchaseUpdatesResponse(response: PurchaseUpdatesResponse) {
        // return a paginated response of purchase history since the last call
        // to getPurchaseUpdates().
        when (response.requestStatus) {
            PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL -> {
                for (receipt in response.receipts) {
                    TODO()
                }
                if (response.hasMore()) {
                    PurchasingService.getPurchaseUpdates(false)
                }
            }
            PurchaseUpdatesResponse.RequestStatus.FAILED -> TODO()
            PurchaseUpdatesResponse.RequestStatus.NOT_SUPPORTED -> TODO()
        }
    }
}
