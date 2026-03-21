package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.utils.ObserverSubject

enum class MemoryOperation {
    NONE,
    STORE,
    RECALL,
    EXCHANGE,
    SUM,
}

class Ti36Memory(val computation: Ti36Computation) {

    val onContentChanged: ObserverSubject<Boolean> = ObserverSubject()

    private val memoryArray = DoubleArray(10)

    fun performOperation(operation: MemoryOperation, memoryCell : Int) {
        require(memoryCell in 0..9)

        when (operation) {
            MemoryOperation.STORE -> memoryArray[memoryCell] = computation.getValue()
            MemoryOperation.RECALL -> computation.setResult(memoryArray[memoryCell])
            MemoryOperation.EXCHANGE -> {
                val tmp = memoryArray[memoryCell]
                memoryArray[memoryCell] = computation.getValue()
                computation.setResult(tmp)
            }
            MemoryOperation.SUM -> memoryArray[memoryCell] += computation.getValue()
            MemoryOperation.NONE -> {}
        }

        onContentChanged(hasNonZeroMemory())
    }

    fun hasNonZeroMemory(): Boolean {
        return memoryArray.any { it != 0.0 }
    }

    fun reset() {
        memoryArray.fill(0.0)
        onContentChanged(hasNonZeroMemory())
    }
}
