package com.mygdx.es.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.component.*
import ktx.ashley.entity

class EntityFactory(val engine: Engine) {
    fun buildTurret(turretParent: Entity): Entity {
        return engine.entity {
            with<Sprite> { setRegion(Texture("tanks_turret1.png")) }
            with<RelativeTransform> { relativePosition = Vector2(-20f, 20f) }
            with<ConnectedTo> { connection = turretParent }
        }
    }

    fun buildTankBody(): Entity {
        return engine.entity {
            with<Sprite> { setRegion(Texture("tanks_tankGreen_body5.png")) }
            with<Transform> { position = Vector2(300f, 300f) }
        }
    }

    fun buildPlayerParent(): Entity {
        val tank = buildTankBody()
        return engine.entity {
            with<Parent> { children = listOf(buildTurret(tank), tank) }
        }
    }

    fun buildPlayer(playerName: String): Entity {
        return engine.entity {
            buildPlayerParent()
            with<Player> { name = playerName }
        }
    }
}