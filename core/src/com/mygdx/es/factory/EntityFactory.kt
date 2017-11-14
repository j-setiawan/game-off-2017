package com.mygdx.es.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.component.*
import ktx.ashley.entity

class EntityFactory(val engine: Engine) {
    fun buildTurret(): Entity {
        return engine.entity {
            with<SpriteComponent> {
                sprite = Sprite(Texture("tanks_turret1.png"))
                setOrigin(0F, sprite.height / 2)
            }
            with<TransformComponent> { position = Vector2(2f, 16f) }
        }
    }

    fun buildTankBody(): Entity {
        return engine.entity {
            with<SpriteComponent> {
                sprite = Sprite(Texture("tanks_tankGreen_body5.png"))
                setCentered()
            }
            with<TransformComponent> { position = Vector2(300f, 300f) }
            with<ParentComponent> { children = listOf(buildTurret()) }
            with<CollidableComponent> {}
        }
    }

    fun buildPlayer(playerName: String): Entity {
        return engine.entity {
            with<PlayerComponent> { name = playerName }
            with<ParentComponent> { children = listOf(buildTankBody()) }
        }
    }

}