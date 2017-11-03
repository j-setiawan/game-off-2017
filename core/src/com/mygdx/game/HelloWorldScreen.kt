package com.mygdx.game

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxScreen
import ktx.app.use
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.mapperFor
import ktx.inject.Context

class Transform(var x: Float = 0f, var y: Float = 0f) : Component
class Label(var text: String = "") : Component

val labelFamily: Family = allOf(Transform::class, Label::class).get()
val transformMapper = mapperFor<Transform>()
val labelMapper = mapperFor<Label>()

class LabelProcessor : IntervalIteratingSystem(labelFamily, 1.0f) {
    override fun processEntity(entity: Entity?) {
        val e = entity!!
        val text = labelMapper[e]
        text.text += "!"
    }
}

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
            with<Transform> { x = 300f; y = 100f }
            with<Label> { text = "Hello world!" }
        }
        engine.addSystem(LabelProcessor())
    }

    override fun render(delta: Float) {
        batch.use { b ->
            labels.forEach { label ->
                val text: Label = labelMapper[label]
                val transform: Transform = transformMapper[label]
                font.draw(b, text.text, transform.x, transform.y)
            }
            b.draw(img, 0f, 0f)
        }
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        img.dispose()
    }
}