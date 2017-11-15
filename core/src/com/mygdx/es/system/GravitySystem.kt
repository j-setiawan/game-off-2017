package com.mygdx.es.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.helper.*

class GravitySystem(val level : Entity) : IntervalIteratingSystem(physicsFamily, 0.01f) {
    val gravity = 0.1f
    val terrain = terrainMapper[level]

    override fun processEntity(entity: Entity?) {
        val sprite = spriteMapper[entity!!]
        val transform = transformMapper[entity]
        val velocity = velocityMapper[entity]

        val bottomLeft = sprite.sprite.boundingRectangle.getPosition(Vector2())
        transform.matrixGlobal.applyTo(bottomLeft)

        val bottomRight = sprite.sprite.boundingRectangle.getPosition(Vector2())
        bottomRight.x += sprite.sprite.boundingRectangle.width
        transform.matrixGlobal.applyTo(bottomRight)

        if(missedPixel(bottomLeft) || missedPixel(bottomRight)) {
            if (!missedPixel(bottomLeft)) {
                transform.degrees -= 1
            } else if (!missedPixel(bottomRight)) {
                transform.degrees += 1
            } else {
                transform.position.x -= velocity.velocity.x
                transform.position.y -= velocity.velocity.y

                velocity.velocity.y += gravity
            }
        }
    }

    private fun missedPixel(position: Vector2) : Boolean {
        return terrain.pixmap.getPixel(position.x.toInt(), terrain.pixmap.height - position.y.toInt()) == 0
    }

    private fun missedPixel(x: Float, y: Float) : Boolean {
        return missedPixel(Vector2(x, y))
    }
}