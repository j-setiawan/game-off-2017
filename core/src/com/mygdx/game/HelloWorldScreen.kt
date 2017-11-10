package com.mygdx.game

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.mygdx.es.component.CaptionComponent
import com.mygdx.es.component.SpriteComponent
import com.mygdx.es.component.TransformComponent
import com.mygdx.es.factory.EntityFactory
import com.mygdx.es.helper.*
import com.mygdx.es.system.MoarExclamationMarksSystem
import com.mygdx.es.system.SpriteRotatorSystem
import com.mygdx.es.system.TransformHierarchySystem
import ktx.app.KtxScreen
import ktx.app.use
import ktx.ashley.entity

class HelloWorldScreen(context: Context) : KtxScreen {
    private val batch = CpuSpriteBatch().apply {
        color = Color.WHITE
    }
    private val font = BitmapFont()
    private val engine = context.engine
    private val labels = engine.getEntitiesFor(labelFamily)
    private val sprites = engine.getEntitiesFor(spriteFamily)
    private val exclamations = MoarExclamationMarksSystem()
    private val transformHierarchySystem = TransformHierarchySystem()
    private val rotator = SpriteRotatorSystem()
    private val factory = EntityFactory(engine)
    private lateinit var player: Entity

    override fun show() {
        engine.addSystem(exclamations)
        engine.addSystem(rotator)
        engine.addSystem(transformHierarchySystem)
        engine.entity {
            with<TransformComponent> { position = Vector2(300f, 100f) }
            with<CaptionComponent> { text = "Hello world!" }
        }
        engine.entity {
            with<TransformComponent> { degrees = 45f }
            with<SpriteComponent> {
                sprite = Sprite(Texture("badlogic.jpg"))
                sprite.setOriginCenter()
            }
        }
        player = engine.entity {
            factory.buildPlayer("player 1")
        }
    }

    override fun render(delta: Float) {
        batch.use { b ->
            sprites.forEach { entity ->
                val sprite = spriteMapper[entity]
                val transform = transformMapper[entity]
                b.setTransformMatrix(transform.matrixGlobal)
                sprite.sprite.draw(b, sprite.alpha)
            }
            labels.forEach { entity ->
                val caption = captionMapper[entity]
                val transform = transformMapper[entity]
                b.setTransformMatrix(transform.matrixGlobal)
                font.draw(b, caption.text, 0F, 0F)
            }
        }
    }

    override fun hide() {
        engine.removeSystem(exclamations)
        engine.removeSystem(rotator)
        engine.removeSystem(transformHierarchySystem)
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}