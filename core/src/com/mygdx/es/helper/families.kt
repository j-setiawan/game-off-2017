package com.mygdx.es.helper

import com.badlogic.ashley.core.Family
import com.mygdx.es.component.*
import ktx.ashley.allOf

val activePlayerFamily: Family = allOf(ActivePlayer::class, Player::class, Turret::class).get()
val labelFamily: Family = allOf(Transform::class, Caption::class).get()
val levelFamily: Family = allOf(Level::class).get()
val playerFamily: Family = allOf(Player::class).get()
val spriteFamily: Family = allOf(Transform::class, Sprite::class).get()