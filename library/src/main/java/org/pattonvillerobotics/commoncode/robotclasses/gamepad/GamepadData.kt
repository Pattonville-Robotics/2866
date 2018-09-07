package org.pattonvillerobotics.commoncode.robotclasses.gamepad

import com.qualcomm.robotcore.hardware.Gamepad
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData.Button.*
import javax.annotation.concurrent.Immutable

/**
 * This immutable class stores all data pertaining to a gamepad at the given instant.
 *
 * @author Mitchell Skaggs
 * @since 3.4.0
 */
@Immutable
class GamepadData
/**
 * Copies the contents of a [Gamepad] to an immutable form.
 *
 * @param gamepad the Gamepad to copy
 */
(gamepad: Gamepad) {


    @JvmField
    val left_stick_x: Float = gamepad.left_stick_x
    @JvmField
    val left_stick_y: Float = gamepad.left_stick_y
    @JvmField
    val right_stick_x: Float = gamepad.right_stick_x
    @JvmField
    val right_stick_y: Float = gamepad.right_stick_y
    @JvmField
    val dpad_up: Boolean = gamepad.dpad_up
    @JvmField
    val dpad_down: Boolean = gamepad.dpad_down
    @JvmField
    val dpad_left: Boolean = gamepad.dpad_left
    @JvmField
    val dpad_right: Boolean = gamepad.dpad_right
    @JvmField
    val a: Boolean = gamepad.a
    @JvmField
    val b: Boolean = gamepad.b
    @JvmField
    val x: Boolean = gamepad.x
    @JvmField
    val y: Boolean = gamepad.y
    @JvmField
    val left_bumper: Boolean = gamepad.left_bumper
    @JvmField
    val right_bumper: Boolean = gamepad.right_bumper
    @JvmField
    val left_stick_button: Boolean = gamepad.left_stick_button
    @JvmField
    val right_stick_button: Boolean = gamepad.right_stick_button
    @JvmField
    val left_trigger: Float = gamepad.left_trigger
    @JvmField
    val right_trigger: Float = gamepad.right_trigger

    /**
     * Gets the captured state of a given button.
     *
     * @param button the button to query
     * @return true if pressed, false otherwise
     */
    fun getButtonPressed(button: Button): Boolean {
        return when (button) {
            A -> a
            B -> b
            X -> x
            Y -> y
            LEFT_BUMPER -> left_bumper
            RIGHT_BUMPER -> right_bumper
            DPAD_UP -> dpad_up
            DPAD_DOWN -> dpad_down
            DPAD_LEFT -> dpad_left
            DPAD_RIGHT -> dpad_right
            STICK_BUTTON_LEFT -> left_stick_button
            STICK_BUTTON_RIGHT -> right_stick_button
        }
    }

    /**
     * All of the available buttons on the controller.
     *
     * @author Mitchell Skaggs
     * @since 3.4.0
     */
    enum class Button {
        A, B, X, Y, LEFT_BUMPER, RIGHT_BUMPER, DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT, STICK_BUTTON_LEFT, STICK_BUTTON_RIGHT
    }
}
