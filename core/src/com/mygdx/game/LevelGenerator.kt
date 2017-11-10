package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.component.LevelComponent
import com.mygdx.es.component.TransformComponent
import com.mygdx.es.helper.levelFamily
import com.mygdx.es.helper.playerFamily
import ktx.ashley.entity

object LevelGenerator {
    fun generate(engine: Engine, viewport: Vector2) {
        val levels: ImmutableArray<Entity> = engine.getEntitiesFor(levelFamily)
        levels.forEach { engine.removeEntity(it) }
        val level = engine.entity {
            with<LevelComponent> {} // TODO: terrain, wind, ...
        }
        val players: ImmutableArray<Entity> = engine.getEntitiesFor(playerFamily)
        val margin = 0.1F * viewport.x
        val leftMargin = margin * 0.5F
        val spacing = (viewport.x - margin) / (players.size() + 1)
        val height = 100F // TODO
        players.forEachIndexed { index, player ->
            player.remove(TransformComponent::class.java)
            player.add(TransformComponent(Vector2(leftMargin + spacing * index.toFloat(), height)))
        }
    }
}