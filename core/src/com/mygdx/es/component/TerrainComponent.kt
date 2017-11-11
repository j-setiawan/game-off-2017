package com.mygdx.es.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture

class TerrainComponent(var pixmap: Pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888),
                       var texture: Texture = Texture(pixmap)) : Component {
    fun updateTexture() {
        texture = Texture(pixmap)
    }
}