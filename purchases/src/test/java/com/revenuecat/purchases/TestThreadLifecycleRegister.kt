package com.revenuecat.purchases

import androidx.lifecycle.Lifecycle

/**
 * When [isMainThread] is true then simulate Lifecycle 1.3.0 behaviour and
 * enforcing main thread caller thread requirement.
 */
class TestThreadLifecycleRegister(owner: Lifecycle, private val isMainThread: Boolean): AppLifecycleRegister(owner) {

  override fun ensureMainThread(body: () -> Unit) {
    if (isMainThread) {
      body()
    } else {
      throw IllegalStateException("LifecycleRegister methods must be called on the main thread")
    }
  }

}
