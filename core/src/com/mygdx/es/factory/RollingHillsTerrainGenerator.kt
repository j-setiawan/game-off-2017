package com.mygdx.es.factory

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.component.TerrainComponent
import com.mygdx.es.component.TransformComponent
import com.mygdx.es.helper.Random

class RollingHillsTerrainGenerator : LevelFactory.TerrainGenerator {
    override fun placePlayers(extent: Vector2, players: ImmutableArray<Entity>) {
        val margin = 0.1F * extent.x
        val leftMargin = margin * 0.5F
        val spacing = (extent.x - margin) / (players.size() + 1)
        val height = 100F // TODO
        players.forEachIndexed { index, player ->
            player.remove(TransformComponent::class.java)
            player.add(TransformComponent(com.badlogic.gdx.math.Vector2(leftMargin + spacing * index.toFloat(), height)))
        }
    }

    override fun generateTerrain(terrain: TerrainComponent) {
        val octavePeriodOffset: FloatArray = floatArrayOf(
                Random.uniformRange(0F, MathUtils.PI2),
                Random.uniformRange(0F, MathUtils.PI2),
                Random.uniformRange(0F, MathUtils.PI2)
        )
        val octaveValueOffset: FloatArray = floatArrayOf(
                0.5F,
                0F,
                0F
        )
        val octaveDensity: FloatArray = floatArrayOf(
                Random.gaussianRange(0.25F, 2.0F),
                Random.gaussianRange(2.0F, 4.0F),
                Random.gaussianRange(4.0F, 8.0F)
        )
        val octaveStrength: FloatArray = floatArrayOf(
                Random.gaussianRange(0.25F, 0.75F),
                Random.gaussianRange(0.01F, 0.25F),
                Random.gaussianRange(0.001F, 0.01F)
        )
        val elevation: FloatArray = kotlin.FloatArray(terrain.pixmap.width)
        val fWidth = terrain.pixmap.width.toFloat()
        val fHeight = terrain.pixmap.height.toFloat()
        for (i in elevation.indices) {
            val xNorm: Float = i.toFloat() / fWidth
            for (j in octavePeriodOffset.indices) {
                val yNorm = octaveStrength[j] * (octaveValueOffset[j] + MathUtils.sin(xNorm * octaveDensity[j] + octavePeriodOffset[j]))
                elevation[i] += yNorm * fHeight
            }
        }
        var min = Float.MAX_VALUE
        var max = Float.MIN_VALUE
        for (i in elevation.indices) {
            min = minOf(min, elevation[i])
            max = maxOf(max, elevation[i])
        }
        val baseline = 50F
        val offset = baseline - min
        for (i in elevation.indices) {
            elevation[i] += offset
        }
        terrain.pixmap.setColor(0F, 0F, 0F, 0F)
        terrain.pixmap.fill()
        terrain.pixmap.setColor(1F, 1F, 1F, 1F)
        for (x in 0 until terrain.pixmap.width) {
            for (y in 0 until terrain.pixmap.height) {
                if (y < elevation[x]) continue // done this column
                terrain.pixmap.drawPixel(x, y)
            }
        }
        terrain.updateTexture()
    }
}
