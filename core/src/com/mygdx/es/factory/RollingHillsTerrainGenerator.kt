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
                Random.gaussian(1.25f, 0.2f),
                Random.gaussian(3f, 0.75f),
                Random.gaussian(6f, 1f)
        )
        val octaveStrength: FloatArray = floatArrayOf(
                Random.gaussian(0.5F, 0.3F),
                Random.gaussian(0.25F, 0.15F),
                Random.gaussian(0.125F, 0.1F)
        )
        val elevation: FloatArray = kotlin.FloatArray(terrain.pixmap.width)
        val fWidth = terrain.pixmap.width.toFloat()
        val fHeight = terrain.pixmap.height.toFloat()
        for (i in elevation.indices) {
            val xNorm: Float = i.toFloat() / fWidth * MathUtils.PI2
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
        val scale = (fHeight - baseline) / (max - min)
        for (i in elevation.indices) {
            elevation[i] *= scale
            elevation[i] += offset
        }
        terrain.pixmap.setColor(0F, 0F, 0F, 0F)
        terrain.pixmap.fill()
        terrain.pixmap.setColor(1F, 1F, 1F, 1F)
        for (x in 0 until terrain.pixmap.width) {
            for (yi in 0 until terrain.pixmap.height) {
                val y = terrain.pixmap.height - yi
                if (y > elevation[x]) continue // done this column
                terrain.pixmap.drawPixel(x, yi)
            }
        }
        terrain.updateTexture()
    }
}
