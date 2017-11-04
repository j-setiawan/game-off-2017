package com.mygdx.es.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.mygdx.es.helper.captionMapper
import com.mygdx.es.helper.labelFamily

class MoarExclamationMarksSystem : IntervalIteratingSystem(labelFamily, 1.0f) {
    override fun processEntity(entity: Entity?) {
        val captionComponent = captionMapper[entity!!]
        captionComponent.text += "!"
    }
}