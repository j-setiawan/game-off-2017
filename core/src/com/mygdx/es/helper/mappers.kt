package com.mygdx.es.helper

import com.mygdx.es.component.*
import ktx.ashley.mapperFor

val captionMapper = mapperFor<Caption>()
val spriteMapper = mapperFor<Sprite>()
val transformMapper = mapperFor<Transform>()
val relativeTransformMapper = mapperFor<RelativeTransform>()
val turretMapper = mapperFor<Turret>()
val connectedToMapper = mapperFor<ConnectedTo>()