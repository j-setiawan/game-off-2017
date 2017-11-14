package com.mygdx.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
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
import com.mygdx.es.factory.LevelFactory
import com.mygdx.es.helper.*
import com.mygdx.es.system.GravitySystem
import com.mygdx.es.system.MoarExclamationMarksSystem
import com.mygdx.es.system.SpriteRotatorSystem
import com.mygdx.es.system.TransformHierarchySystem
import ktx.app.KtxScreen
import ktx.app.use
import ktx.ashley.entity

class HelloWorldScreen(private val context: Context) : KtxScreen {
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
    private lateinit var level: Entity

    override fun show() {
        engine.addEntityListener(terrainFamily, object : EntityListener {
            override fun entityAdded(entity: Entity?) {}
            override fun entityRemoved(entity: Entity?) {
                terrainMapper[entity!!].pixmap.dispose()
            }
        })
        engine.addSystem(exclamations)
        engine.addSystem(transformHierarchySystem)
        engine.entity {
            with<TransformComponent> { position = Vector2(300f, 100f) }
            with<CaptionComponent> { text = "Hello world!" }
        }
        engine.entity {
            with<TransformComponent> { degrees = 45f }
            with<SpriteComponent> {
                sprite = Sprite(Texture("badlogic.jpg"))
                setCentered()
            }
        }
        player = engine.entity {
            factory.buildPlayer("player 1")
        }
        level = LevelFactory(engine).buildLevel(context.viewport)
        engine.addSystem(GravitySystem(level))
    }

    override fun render(delta: Float) {
        batch.transformMatrix = context.viewport.camera.combined
        batch.use { b ->
            val texture: Texture = terrainMapper[level].texture
            b.draw(texture, 0F, 0F)
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