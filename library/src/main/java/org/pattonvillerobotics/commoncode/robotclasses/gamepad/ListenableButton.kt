package org.pattonvillerobotics.commoncode.robotclasses.gamepad

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton.ButtonState.*


/**
 * This class allows users to subscribe to an individual button's state in an event-driven fashion.
 *
 * @author Mitchell Skaggs
 * @since 3.4.0
 */
class ListenableButton {

    private val listeners: Multimap<ButtonState, () -> Unit> = ArrayListMultimap.create()
    private var currentButtonState: ButtonState = BEING_RELEASED

    /**
     * Constructs a new ListenableButton that is currently released.
     */
    init {
    }

    /**
     * Updates the current value of the button.
     *
     * @param updateValue true if pressed, false otherwise
     */
    internal fun update(updateValue: Boolean) {
        when (currentButtonState) {
            BEING_PRESSED -> currentButtonState = if (updateValue) {
                BEING_PRESSED
            } else {
                JUST_RELEASED
            }
            BEING_RELEASED -> currentButtonState = if (updateValue) {
                JUST_PRESSED
            } else {
                BEING_RELEASED
            }
            JUST_PRESSED -> currentButtonState = if (updateValue) {
                BEING_PRESSED
            } else {
                JUST_RELEASED
            }
            JUST_RELEASED -> currentButtonState = if (updateValue) {
                JUST_PRESSED
            } else {
                BEING_RELEASED
            }
        }

        notifyListeners()
    }

    /**
     * Add a listener to a given state.
     *
     * @param buttonState    the state to listen for
     * @param buttonListener the listener
     * @return itself, for chaining calls
     */
    fun addListener(buttonState: ButtonState, buttonListener: () -> Unit): ListenableButton {
        listeners.put(buttonState, buttonListener)
        return this
    }

    /**
     * Add a listener to a given state.
     *
     * @param buttonState    the state to listen for
     * @param buttonListener the listener
     * @return itself, for chaining calls
     */
    fun addListener(buttonState: ButtonState, buttonListener: ButtonListener): ListenableButton {
        listeners.put(buttonState, { buttonListener.run() })
        return this
    }

    /**
     * Remove a listener from a given state.
     *
     * @param buttonState    the state to listen for
     * @param buttonListener the listener
     * @return see [MutableList.remove]
     */
    fun removeListener(buttonState: ButtonState, buttonListener: () -> Unit): Boolean {
        return listeners.remove(buttonState, buttonListener)
    }

    /**
     * Remove a listener from a given state.
     *
     * @param buttonState    the state to listen for
     * @param buttonListener the listener
     * @return see [MutableList.remove]
     */
    fun removeListener(buttonState: ButtonState, buttonListener: ButtonListener): Boolean {
        return listeners.remove(buttonState, { buttonListener })
    }

    private fun notifyListeners() {
        for (buttonListener in listeners.get(currentButtonState))
            buttonListener()
    }

    /**
     * The possible states for a button to be in.
     *
     * @author Mitchell Skaggs
     * @since 3.4.0
     */
    enum class ButtonState {
        /**
         * The button has been pressed for more than one cycle.
         */
        BEING_PRESSED,

        /**
         * The button has been released for more than one cycle.
         */
        BEING_RELEASED,

        /**
         * The button has been pressed for exactly one cycle.
         */
        JUST_PRESSED,

        /**
         * The button has been released for exactly one cycle.
         */
        JUST_RELEASED
    }

    /**
     * A listener for a certain button state.
     */
    interface ButtonListener : Runnable {
        override fun run()
    }
}
