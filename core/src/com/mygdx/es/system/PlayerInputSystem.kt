package com.mygdx.es.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.mygdx.es.helper.activePlayerFamily
import com.mygdx.es.helper.turretMapper
import ktx.ashley.get

val keyRate = 5f // per second
val turretAngleRate = 0.5f // per tick
val turretPowerRate = 1f // per tick

enum class Action {
    TURN_LEFT,
    TURN_RIGHT,
    POWER_UP,
    POWER_DOWN,
    FIRE
}

class PlayerInputSystem : IntervalIteratingSystem(activePlayerFamily, 1f / keyRate), InputProcessor {

    var actions: ArrayList<Action> = ArrayList()

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.LEFT -> actions.add(Action.TURN_LEFT)
            Input.Keys.RIGHT -> actions.add(Action.TURN_RIGHT)
            Input.Keys.UP -> actions.add(Action.POWER_UP)
            Input.Keys.DOWN -> actions.add(Action.POWER_DOWN)
            else -> return false
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun processEntity(entity: Entity) {
        val turret = entity[turretMapper]
        if (turret != null) {
            actions.forEach({
                when (it) {
                    Action.TURN_LEFT -> turret.angle = maxOf(-90f, turret.angle - turretAngleRate)
                    Action.TURN_RIGHT -> turret.angle = minOf(90f, turret.angle + turretAngleRate)
                    Action.POWER_UP -> turret.power = minOf(1000f, turret.power + turretPowerRate)
                    Action.POWER_DOWN -> turret.power = maxOf(0f, turret.power - turretPowerRate)
                    Action.FIRE -> {
                    } // TODO
                }
            })
        }
    }
}