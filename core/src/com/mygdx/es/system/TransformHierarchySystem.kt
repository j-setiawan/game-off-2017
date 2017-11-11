package com.mygdx.es.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Affine2
import com.mygdx.es.component.ParentComponent
import com.mygdx.es.component.TransformComponent
import com.mygdx.es.helper.parentFamily
import com.mygdx.es.helper.parentMapper
import com.mygdx.es.helper.transformFamily
import com.mygdx.es.helper.transformMapper
import ktx.ashley.get


class TransformHierarchySystem : IteratingSystem(transformFamily) {

    private val identity: Affine2 = Affine2().idt()
    private val notRoots: MutableSet<Entity> = HashSet()
    private var parents: ImmutableArray<Entity>? = null

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        parents = engine!!.getEntitiesFor(parentFamily)
    }

    override fun removedFromEngine(engine: Engine?) {
        parents = null
    }

    override fun update(deltaTime: Float) {
        // Create a set of transform roots.
        // 1. Clear set, assume all are roots
        notRoots.clear()
        // 2. Iterate every parent component and add all children
        //    if the parent has a transform. (To create a transform
        //    hierarchy, every "generation" needs a transform, even if
        //    it is the identity.)
        parents!!.forEach {
            if (it[transformMapper] != null) notRoots.addAll(parentMapper[it].children)
        }
        // 3. Iterate all transforms, but if the entity is in notRoots
        //    skip it and do it as part of a root's hierarchy
        super.update(deltaTime)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (notRoots.contains(entity)) return
        processEntity(entity, identity)
    }

    private fun processEntity(entity: Entity, parent: Affine2) {
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