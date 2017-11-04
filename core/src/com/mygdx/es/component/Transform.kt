package com.mygdx.es.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class Transform(var position: Vector2 = Vector2(0f, 0f),
                var degrees: Float = 0f,
                var scale: Vector2 = Vector2(1f, 1f)) : Component