package com.mygdx.es.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

class ConnectedTo(var connection: Entity? = null) : Component