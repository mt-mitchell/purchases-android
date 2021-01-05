package com.revenuecat.purchases

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

abstract class AppLifecycleRegister(private val owner: Lifecycle) : Lifecycle() {
  protected abstract fun ensureMainThread(body: () -> Unit)

  override fun addObserver(observer: LifecycleObserver) {
    ensureMainThread { owner.addObserver(observer) }
  }

  override fun removeObserver(observer: LifecycleObserver) {
    ensureMainThread { owner.removeObserver(observer) }
  }

  override fun getCurrentState(): State = owner.currentState
}

/**
 * In androidx.lifecycle 1.3.0 addObserver will throw if not invoked on the MainThread.
 *
 * To prevent apps initialing [Purchases] on a background thread and crashing, the
 * addObserver is scheduled onto the MainLooper if invoked from a background thread.
 *
 */
class MainThreadLifecycleRegister(
    owner: Lifecycle,
    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
) : AppLifecycleRegister(owner) {

  override fun ensureMainThread(body: () -> Unit) {
    if (isMainThread()) {
      body()
    } else {
      mainThreadHandler.post { body() }
    }
  }

  private fun isMainThread(): Boolean = Looper.getMainLooper().thread == Thread.currentThread()
}
