package com.mygdx.es.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.helper.collidableFamily
import com.mygdx.es.helper.spriteMapper
import com.mygdx.es.helper.terrainMapper
import com.mygdx.es.helper.transformMapper

class GravitySystem(val level : Entity) : IntervalIteratingSystem(collidableFamily, 0.007f) {
    override fun processEntity(entity: Entity?) {
        var terrain = terrainMapper[level]

        val sprite = spriteMapper[entity!!]
        val transform = transformMapper[entity]

        val position = sprite.sprite.boundingRectangle.getPosition(Vector2())
        transform.matrixGlobal.applyTo(position)

        if(terrain.pixmap.getPixel(position.x.toInt(), position.y.toInt()) == 0 || terrain.pixmap.getPixel((position.x + sprite.sprite.boundingRectangle.width).toInt(), position.y.toInt()) == 0) {
            transform.position.y --
        }
    }
}