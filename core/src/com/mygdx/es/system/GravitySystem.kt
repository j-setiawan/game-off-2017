package com.mygdx.es.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.mygdx.es.helper.collidableFamily
import com.mygdx.es.helper.spriteMapper
import com.mygdx.es.helper.terrainMapper
import com.mygdx.es.helper.transformMapper

class GravitySystem(val level : Entity) : IntervalIteratingSystem(collidableFamily, 0.007f) {
    override fun processEntity(entity: Entity?) {
        var terrain = terrainMapper[level]

        val sprite = spriteMapper[entity!!]
        val transform = transformMapper[entity]

        val bottomY = transform.position.y + sprite.sprite.boundingRectangle.y
        val bottomLeftX = transform.position.x + sprite.sprite.boundingRectangle.x
        val bottomRightX = bottomLeftX + sprite.sprite.boundingRectangle.width

        if(terrain.pixmap.getPixel(bottomLeftX.toInt(), bottomY.toInt()) == 0 || terrain.pixmap.getPixel(bottomRightX.toInt(), bottomY.toInt()) == 0) {
            transform.position.y --
        }
    }
}