package com.mygdx.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import ktx.app.KtxGame
import ktx.async.enableKtxCoroutines
import ktx.inject.Context

class MyGdxGame : KtxGame<Screen>() {
    private val context = Context()
    private val engine = PooledEngine()
    override fun create() {
        enableKtxCoroutines(asynchronousExecutorConcurrencyLevel = 1)
        context.register {
            bindSingleton(engine)
        }
        addScreen(HelloWorldScreen(context))
        setScreen<HelloWorldScreen>()
    }

    override fun render() {
        engine.update(Gdx.graphics.deltaTime)
        super.render()
    }

    override fun dispose() {
        context.dispose() // disposes all that implement Disposable
        super.dispose()
    }
}
