package com.example.cryptowallet.app.realtime.domain

/**
 * Manages active subscriptions across different screens.
 * Each screen can subscribe to a set of coin IDs, and the manager
 * aggregates all subscriptions to determine the active set.
 */
interface SubscriptionManager {

    fun subscribe(screenId: String, coinIds: List<String>)


    fun unsubscribe(screenId: String)


    fun getActiveSubscriptions(): Set<String>


    fun getSubscriptionsForScreen(screenId: String): Set<String>
}
