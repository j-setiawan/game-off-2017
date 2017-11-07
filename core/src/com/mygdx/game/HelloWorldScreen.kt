package com.mygdx.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.component.Caption
import com.mygdx.es.component.Sprite
import com.mygdx.es.component.Transform
import com.mygdx.es.helper.*
import com.mygdx.es.system.MoarExclamationMarksSystem
import com.mygdx.es.system.SpriteRotatorSystem
import ktx.app.KtxScreen
import ktx.app.use
import ktx.ashley.entity

class HelloWorldScreen(context: Context) : KtxScreen {
    private val batch = SpriteBatch().apply {
        color = Color.WHITE
    }
    private val font = BitmapFont()
    private val engine = context.engine
    private val labels = engine.getEntitiesFor(labelFamily)
    private val sprites = engine.getEntitiesFor(spriteFamily)
    private val exclamations = MoarExclamationMarksSystem()
    private val rotator = SpriteRotatorSystem()

    override fun show() {
        engine.addSystem(exclamations)
        engine.addSystem(rotator)
        engine.entity {
            with<Transform> { position = Vector2(300f, 100f) }
            with<Caption> { text = "Hello world!" }
        }
        engine.entity {
            with<Transform> { degrees = 45f }
            with<Sprite> { setRegion(Texture("badlogic.jpg")) }
        }
    }

    override fun render(delta: Float) {
        batch.use { b ->
            sprites.forEach { entity ->
                val sprite = spriteMapper[entity]
                val transform = transformMapper[entity]
                b.draw(sprite.region,
                        transform.position.x, transform.position.y,
                        sprite.cx, sprite.cy,
                        sprite.width, sprite.height,
                        transform.scale.x, transform.scale.y,
                        transform.degrees)
            }
            labels.forEach { entity ->
                val caption = captionMapper[entity]
                val transform = transformMapper[entity]
                font.draw(b, caption.text, transform.position.x, transform.position.y)
            }
        }
    }

    override fun hide() {
        engine.removeSystem(exclamations)
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}