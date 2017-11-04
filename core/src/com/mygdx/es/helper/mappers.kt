package com.mygdx.es.helper

import com.mygdx.es.component.Caption
import com.mygdx.es.component.Sprite
import com.mygdx.es.component.Transform
import com.mygdx.es.component.Turret
import ktx.ashley.mapperFor

val captionMapper = mapperFor<Caption>()
val spriteMapper = mapperFor<Sprite>()
val transformMapper = mapperFor<Transform>()
val turretMapper = mapperFor<Turret>()