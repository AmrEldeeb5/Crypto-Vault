package com.example.cryptowallet.app.realtime.domain

/**
 * Strategy interface for handling reconnection attempts.
 */
interface ReconnectionStrategy {

    fun nextDelay(attemptNumber: Int): Long


    fun shouldFallback(attemptNumber: Int): Boolean


    fun reset()
}
