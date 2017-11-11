package com.mygdx.es.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.es.component.LevelComponent
import com.mygdx.es.component.TerrainComponent
import com.mygdx.es.helper.levelFamily
import com.mygdx.es.helper.playerFamily
import ktx.ashley.entity

class LevelFactory(private val engine: Engine) {

    interface TerrainGenerator {
        fun generateTerrain(terrain: TerrainComponent)
        fun placePlayers(extent: Vector2, players: ImmutableArray<Entity>)
    }

    private val players: ImmutableArray<Entity> = engine.getEntitiesFor(playerFamily)

    fun buildLevel(viewport: Viewport): Entity {
        val levels: ImmutableArray<Entity> = engine.getEntitiesFor(levelFamily)
        levels.forEach { engine.removeEntity(it) }
        val generator: TerrainGenerator = RollingHillsTerrainGenerator()
        val level = engine.entity {
            with<LevelComponent> {
            } // TODO: terrain, wind, ...
            with<TerrainComponent> {
                pixmap = Pixmap(viewport.worldWidth.toInt(), viewport.worldHeight.toInt(), Pixmap.Format.RGBA8888)
                generator.generateTerrain(this)
            }
        }
        // TODO; use pixmap to place players, then update texture
        generator.placePlayers(Vector2(viewport.worldWidth, viewport.worldHeight), players)
        return level
    }
}