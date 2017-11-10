package com.mygdx.es.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

val errorTexture: Texture by lazy { Texture("error-texture.png") }

class SpriteComponent(var sprite: Sprite = Sprite(errorTexture),
                      var alpha: Float = 1.0F) : Component {
    fun setCentered() {
        setOrigin(sprite.width / 2F, sprite.height / 2F)
    }

    fun setOrigin(x: Float, y: Float) {
        sprite.setOrigin(x, y)
        sprite.setPosition(-x, -y)
    }
}