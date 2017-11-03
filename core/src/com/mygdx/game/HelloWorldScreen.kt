package com.mygdx.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.es.component.Caption
import com.mygdx.es.component.Position
import com.mygdx.es.helper.captionMapper
import com.mygdx.es.helper.labelFamily
import com.mygdx.es.helper.positionMapper
import com.mygdx.es.system.MoarExclamationMarksSystem
import ktx.app.KtxScreen
import ktx.app.use
import ktx.ashley.entity
import ktx.inject.Context

class HelloWorldScreen(context: Context) : KtxScreen {
    private val batch = SpriteBatch().apply {
        color = Color.WHITE
    }
    private val font = BitmapFont()
    private val img = Texture("badlogic.jpg")
    private val engine: PooledEngine = context.inject()
    private val labels = engine.getEntitiesFor(labelFamily)

    override fun show() {
        engine.entity {
            with<Position> { x = 300f; y = 100f }
            with<Caption> { text = "Hello world!" }
        }
        engine.addSystem(MoarExclamationMarksSystem())
    }

    override fun render(delta: Float) {
        batch.use { b ->
            b.draw(img, 0f, 0f)
            labels.forEach { label ->
                val caption = captionMapper[label]
                val position = positionMapper[label]
                font.draw(b, caption.text, position.x, position.y)
            }
        }
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        img.dispose()
    }
}