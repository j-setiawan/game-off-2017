package com.mygdx.es.helper

import com.badlogic.ashley.core.Family
import com.mygdx.es.component.*
import ktx.ashley.allOf

val activePlayerFamily: Family = allOf(ActivePlayerComponent::class, PlayerComponent::class, TurretComponent::class).get()
val labelFamily: Family = allOf(TransformComponent::class, CaptionComponent::class).get()
val levelFamily: Family = allOf(LevelComponent::class).get()
val playerFamily: Family = allOf(PlayerComponent::class).get()
val spriteFamily: Family = allOf(TransformComponent::class, SpriteComponent::class).get()
val transformFamily: Family = allOf(TransformComponent::class).get()
