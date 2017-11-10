package com.mygdx.es.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Affine2
import com.mygdx.es.component.ParentComponent
import com.mygdx.es.component.TransformComponent
import com.mygdx.es.helper.parentMapper
import com.mygdx.es.helper.transformFamily
import com.mygdx.es.helper.transformMapper
import ktx.ashley.get

val identity: Affine2 = Affine2().idt()

class TransformHierarchySystem : IteratingSystem(transformFamily) {

    val processed: MutableSet<Entity> = HashSet()

    override fun update(deltaTime: Float) {
        processed.clear()
        super.update(deltaTime)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (processed.contains(entity)) return

        processEntity(entity, identity) // start with identity matrix
    }

    private fun processEntity(entity: Entity, parent: Affine2) {
        processed.add(entity)

        val transform: TransformComponent = transformMapper[entity]
        val local: Affine2 = transform.matrixLocal.idt()
        local.translate(transform.position)
        local.rotate(transform.degrees)
        local.scale(transform.scale)
        transform.matrixGlobal.set(local)
        transform.matrixGlobal.preMul(parent)

        val hierarchy: ParentComponent = entity[parentMapper] ?: return
        hierarchy.children.forEach {
            processEntity(it, transform.matrixGlobal)
        }
    }
}