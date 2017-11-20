package com.mygdx.es.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.mygdx.es.component.CollidableComponent
import com.mygdx.es.helper.*

class CollidedSystem() : IntervalIteratingSystem(projectileFamily, 0.01f) {
    override fun processEntity(entity: Entity?) {
        val collidable = collidableMapper[entity!!]

        // TODO: Why is collidable null in the next tick?
        if (collidable != null && collidable.hit) {
            val parent = parentMapper[entity]
            val explosion = textureMapper[parent.children[0]]

            val sprite = spriteMapper[entity]
            sprite.sprite = Sprite(explosion.texture)
            entity.remove(CollidableComponent::class.java)
        }
    }

}