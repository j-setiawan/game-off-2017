package com.mygdx.es.component

import com.badlogic.ashley.core.Component

class PlayerComponent(var name: String = "player",
                      var health: Float = 100f) : Component