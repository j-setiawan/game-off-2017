package com.mygdx.es.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

class Parent(var children : List<Entity> = emptyList()) : Component