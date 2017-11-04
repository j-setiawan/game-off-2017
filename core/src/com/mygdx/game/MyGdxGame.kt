package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import ktx.app.KtxGame
import ktx.async.enableKtxCoroutines

class MyGdxGame : KtxGame<Screen>() {
    private val context = Context
    private val engine = context.engine
    override fun create() {
        enableKtxCoroutines(asynchronousExecutorConcurrencyLevel = 1)
        addScreen(HelloWorldScreen(context))
        setScreen<HelloWorldScreen>()
    }

    override fun render() {
        engine.update(Gdx.graphics.deltaTime)
        super.render()
    }

    override fun dispose() {
        context.dispose()
        super.dispose()
    }
}
