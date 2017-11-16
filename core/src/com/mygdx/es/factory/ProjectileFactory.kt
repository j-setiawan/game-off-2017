package com.mygdx.es.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.component.*
import ktx.ashley.entity

class ProjectileFactory(val engine: Engine) {
    fun buildProjectile(): Entity {
        return engine.entity {
            with<CollidableComponent>{}
            with<SpriteComponent>{
                sprite = Sprite(Texture("tank_explosion10.png"))
                setCentered()
            }
            with<DamageComponent>{
                damage = 100f
                radius = 10f
            }
            with<TransformComponent>{ position = Vector2(300f, 400f) }
            with<VelocityComponent>{ velocity = Vector2(-1f, -10f)}
        }
    }
}