package com.mygdx.es.helper

import com.mygdx.es.component.*
import ktx.ashley.mapperFor

val captionMapper = mapperFor<CaptionComponent>()
val parentMapper = mapperFor<ParentComponent>()
val spriteMapper = mapperFor<SpriteComponent>()
val terrainMapper = mapperFor<TerrainComponent>()
val transformMapper = mapperFor<TransformComponent>()
val turretMapper = mapperFor<TurretComponent>()
val velocityMapper = mapperFor<VelocityComponent>()
