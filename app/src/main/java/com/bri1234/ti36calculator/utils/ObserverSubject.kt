package com.bri1234.ti36calculator.utils

/**
 * A generic event class that allows listeners to subscribe to events and be notified when the event is triggered.
 */
class ObserverSubject <T> {

    private val listeners = mutableSetOf<(ObserverSubject<T>.(T) -> Unit)>()

    operator fun plusAssign(listener: ObserverSubject<T>.(T) -> Unit) {
        listeners.add(listener)
    }

    operator fun minusAssign(listener: ObserverSubject<T>.(T) -> Unit) {
        listeners.remove(listener)
    }

    operator fun invoke(value: T) {
        for (listener in listeners)
            listener(value)
    }

}
