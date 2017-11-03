package com.mygdx.es.helper

import com.badlogic.ashley.core.Family
import com.mygdx.es.component.Caption
import com.mygdx.es.component.Position
import ktx.ashley.allOf

val labelFamily: Family = allOf(Position::class, Caption::class).get()
