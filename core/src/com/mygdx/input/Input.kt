package com.mygdx.input

interface ControllerProcessor {
    fun buttonDown(controller: Int, button: Int): Boolean
    fun buttonUp(controller: Int, button: Int): Boolean
    fun axisChanged(controller: Int, axis: Int, value: Float): Boolean
}

/**
 * This is modelled after InputProcessor but after building this I'm not sure it's useful
 */
class ControllerMultiplexer(vararg processors: ControllerProcessor) : ControllerProcessor {

    var controllerProcessors: ArrayList<ControllerProcessor> = ArrayList(4)

    fun addProcessor(index: Int, controllerProcessor: ControllerProcessor) =
            controllerProcessors.add(index, controllerProcessor)

    fun removeProcessor(index: Int) =
            controllerProcessors.removeAt(index)

    fun addProcessor(controllerProcessor: ControllerProcessor) =
            controllerProcessors.add(controllerProcessor)

    fun removeProcessor(controllerProcessor: ControllerProcessor) =
            controllerProcessors.remove(controllerProcessor)

    val size get() = controllerProcessors.size

    override fun buttonDown(controller: Int, button: Int) =
            controllerProcessors.any { it.buttonDown(controller, button) }

    override fun buttonUp(controller: Int, button: Int) =
            controllerProcessors.any { it.buttonUp(controller, button) }

    override fun axisChanged(controller: Int, axis: Int, value: Float) =
            controllerProcessors.any { it.axisChanged(controller, axis, value) }

    init {
        controllerProcessors.addAll(processors)
    }

}