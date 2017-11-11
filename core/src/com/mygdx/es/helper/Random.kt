package com.mygdx.es.helper

import java.util.Random

object Random {
    val gen = Random()

    fun seed(seed: Long) {
        gen.setSeed(seed)
    }

    fun uniformRange(low: Float, high: Float): Float {
        return gen.nextFloat() * (high - low) + low
    }

    fun gaussianRange(low: Float, high: Float): Float {
        return gen.nextGaussian().toFloat() * (high - low) + low
    }
}