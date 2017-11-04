package com.mygdx.es.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

val errorTexture: Texture by lazy { Texture("error-texture.png") }
val errorTextureRegion: TextureRegion by lazy { TextureRegion(errorTexture) }

class Sprite(var region: TextureRegion = errorTextureRegion,
             var width: Float = region.regionWidth.toFloat(),
             var height: Float = region.regionHeight.toFloat(),
             var cx: Float = 0f,
             var cy: Float = 0f) : Component {

    fun setRegion(texture: Texture) {
        region = TextureRegion(texture)
        width = region.regionWidth.toFloat()
        height = region.regionHeight.toFloat()
    }
}
