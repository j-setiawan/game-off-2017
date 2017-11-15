package com.mygdx.es.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Affine2
import com.badlogic.gdx.math.Vector2

class TransformComponent(var position: Vector2 = Vector2(0f, 0f), // Set by game logic
                         var lastPosition: Vector2 = Vector2(0f, 0f),
                         var degrees: Float = 0f,                      // Set by game logic
                         var scale: Vector2 = Vector2(1f, 1f),    // Set by game logic
                         var matrixGlobal: Affine2 = Affine2().idt(),  // Set by transform hierarchy system
                         var matrixLocal: Affine2 = Affine2().idt()// Set by transform hierarchy system
) : Component