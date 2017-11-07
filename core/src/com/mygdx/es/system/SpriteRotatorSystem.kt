package com.mygdx.es.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.mygdx.es.helper.spriteFamily
import com.mygdx.es.helper.transformMapper

class SpriteRotatorSystem : IntervalIteratingSystem(spriteFamily, 0.007f) {
    override fun processEntity(entity: Entity?) {
        val spriteComponent = transformMapper[entity!!]
        spriteComponent.degrees += 0.2f
    }
}
