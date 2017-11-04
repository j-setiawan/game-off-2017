package com.mygdx.es.helper

import com.badlogic.ashley.core.Family
import com.mygdx.es.component.Caption
import com.mygdx.es.component.Sprite
import com.mygdx.es.component.Transform
import ktx.ashley.allOf

val labelFamily: Family = allOf(Transform::class, Caption::class).get()
val spriteFamily: Family = allOf(Transform::class, Sprite::class).get()