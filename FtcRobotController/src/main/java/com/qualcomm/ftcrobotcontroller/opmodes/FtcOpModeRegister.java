/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

import org.atteo.classindex.ClassIndex;
import org.pattonvillerobotics.commoncode.OpMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Register Op Modes
 */
public class FtcOpModeRegister implements OpModeRegister {

    /**
     * The Op Mode Manager will call this method when it wants a list of all
     * available op modes. Add your op mode to the list to enable it.
     *
     * @param manager op mode manager
     */
    @SuppressWarnings("unchecked")
    public void register(OpModeManager manager) {

    /*
     * register your op modes here.
     * The first parameter is the name of the op mode
     * The second parameter is the op mode class property
     *
     * If two or more op modes are registered with the same name, the app will display an error.
     */
        List<Class<? extends com.qualcomm.robotcore.eventloop.opmode.OpMode>> opModes = new ArrayList<Class<? extends com.qualcomm.robotcore.eventloop.opmode.OpMode>>();


        for (Class<?> c : ClassIndex.getAnnotated(OpMode.class)) { // Some annotation wizardry at work here...
            if (com.qualcomm.robotcore.eventloop.opmode.OpMode.class.isAssignableFrom(c)) {
                //manager.register(c.getAnnotation(OpMode.class).value(), c);
                opModes.add((Class<? extends com.qualcomm.robotcore.eventloop.opmode.OpMode>) c);
            } else {
                try {
                    //manager.register(c.getAnnotation(OpMode.class).value(), c);
                    opModes.add((Class<? extends com.qualcomm.robotcore.eventloop.opmode.OpMode>) c);
                } catch (Exception e) {
                    throw new ClassCastException("Could not register OpMode! \"" + c.getSimpleName() + "\" cannot be cast to OpMode. Please only annotate classes extending OpMode with the @OpMode annotation.");
                }
            }
        }

        Collections.sort(opModes, new Comparator<Class<? extends com.qualcomm.robotcore.eventloop.opmode.OpMode>>() {
            @Override
            public int compare(Class<? extends com.qualcomm.robotcore.eventloop.opmode.OpMode> lhs, Class<? extends com.qualcomm.robotcore.eventloop.opmode.OpMode> rhs) {
                String left = lhs.getAnnotation(OpMode.class).value();
                String right = rhs.getAnnotation(OpMode.class).value();

                return left.compareTo(right);
            }
        });

        for (Class<? extends com.qualcomm.robotcore.eventloop.opmode.OpMode> klass : opModes) {
            manager.register(klass.getAnnotation(OpMode.class).value(), klass);
        }
    }
}