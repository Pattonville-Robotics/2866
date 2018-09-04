package org.pattonvillerobotics.commoncode.robotclasses.gamepad

import com.qualcomm.robotcore.hardware.Gamepad
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData.Button
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton.ButtonListener
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton.ButtonState

/**
 * This class allows users to subscribe to certain buttons' states in an event-driven fashion.
 *
 * @author Mitchell Skaggs
 * @since 3.4.0
 */
class ListenableGamepad {

    private val buttons: Map<Button, ListenableButton> = mapOf(*Button.values().map { Pair(it, ListenableButton()) }.toTypedArray())

    /**
     * Calls all button listeners based on the new gamepad values.
     *
     * @param gamepadData the gamepad values
     */
    fun update(gamepadData: GamepadData) {
        for (b in Button.values())
            buttons[b]!!.update(gamepadData.getButtonPressed(b))
    }


    /**
     * Calls all button listeners based on the new gamepad values.
     *
     * @param gamepad the gamepad from which to extract immutable values
     */
    fun update(gamepad: Gamepad) {
        this.update(GamepadData(gamepad))
    }

    /**
     * Gets an instance of a button from the button's corresponding enum
     *
     * @param button the button enum to find
     * @return a button matching the enum
     */
    fun getButton(button: Button): ListenableButton {
        return buttons[button]!!
    }

    /**
     * Add a listener to a given button and state.
     *
     * @param button the button to listen for
     * @param buttonState    the state to listen for
     * @param buttonListener the listener
     * @return itself, for chaining calls
     */
    fun addButtonListener(button: Button, buttonState: ButtonState, buttonListener: () -> Unit): ListenableGamepad {
        getButton(button).addListener(buttonState, buttonListener)
        return this
    }

    /**
     * Add a listener to a given button and state.
     *
     * @param button the button to listen for
     * @param buttonState    the state to listen for
     * @param buttonListener the listener
     * @return itself, for chaining calls
     */
    fun addButtonListener(button: Button, buttonState: ButtonState, buttonListener: ButtonListener): ListenableGamepad {
        getButton(button).addListener(buttonState, buttonListener)
        return this
    }

    /**
     * Remove a listener from a given state.
     *
     * @param button the button to listen for
     * @param buttonState    the state to listen for
     * @param buttonListener the listener
     * @return see [MutableList.remove]
     */
    fun removeListener(button: Button, buttonState: ButtonState, buttonListener: () -> Unit): Boolean {
        return getButton(button).removeListener(buttonState, buttonListener)
    }

    /**
     * Remove a listener from a given state.
     *
     * @param button the button to listen for
     * @param buttonState    the state to listen for
     * @param buttonListener the listener
     * @return see [MutableList.remove]
     */
    fun removeListener(button: Button, buttonState: ButtonState, buttonListener: ButtonListener): Boolean {
        return getButton(button).removeListener(buttonState, buttonListener)
    }
}
