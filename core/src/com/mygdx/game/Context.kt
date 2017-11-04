package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

object Context : Disposable {
    val engine: Engine = PooledEngine()
    val camera: Camera = OrthographicCamera()
    val viewport: Viewport = FitViewport(640f, 480f, camera)

    override fun dispose() {
        // nothing yet
    }
}