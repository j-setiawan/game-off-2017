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

    fun gaussian(center: Float, variance: Float): Float {
        return gen.nextGaussian().toFloat() * variance + center
    }
}