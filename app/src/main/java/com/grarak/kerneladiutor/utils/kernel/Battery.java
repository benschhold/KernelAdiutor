/*
 * Copyright (C) 2015 Willi Ye
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grarak.kerneladiutor.utils.kernel;

import android.content.Context;

import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.root.Control;

/**
 * Created by willi on 03.01.15.
 */
public class Battery implements Constants {

    public static void setChargingRateAC(int value, Context context) {
        Control.runCommand(String.valueOf(value), CHARGE_AC, Control.CommandType.GENERIC, context);
    }

    public static int getChargingRateAC() {
        return Utils.stringToInt(Utils.readFile(CHARGE_AC));
    }

    public static boolean hasChargingRateAC() {
        return Utils.existFile(CHARGE_AC);
    }

    public static void setChargingRateUSB(int value, Context context) {
        Control.runCommand(String.valueOf(value), CHARGE_USB, Control.CommandType.GENERIC, context);
    }

    public static int getChargingRateUSB() {
        return Utils.stringToInt(Utils.readFile(CHARGE_USB));
    }

    public static boolean hasChargingRateUSB() {
        return Utils.existFile(CHARGE_USB);
    }


    public static void setlowpowervalue(int value, Context context) {
        Control.runCommand(String.valueOf(value), LOW_POWER_VALUE, Control.CommandType.GENERIC, context);
    }

    public static int getlowpowervalue() {
        return Utils.stringToInt(Utils.readFile(LOW_POWER_VALUE));
    }

    public static boolean haslowpowervalue() {
        return Utils.existFile(LOW_POWER_VALUE);
    }


    public static void activateCustomChargeRate(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", CHARGE_RATE_ENABLE, Control.CommandType.GENERIC, context);
    }

    public static boolean isCustomChargeRateActive() {
        return Utils.readFile(CHARGE_RATE_ENABLE).equals("1");
    }

    public static boolean hasCustomChargeRateEnable() {
        return Utils.existFile(CHARGE_RATE_ENABLE);
    }

    public static boolean hasChargeRate() {
        return Utils.existFile(CHARGE_RATE);
    }
    public static int getChargeRate() {
        return Utils.stringToInt(Utils.readFile(CHARGE_RATE));
    }

    public static void setBlx(int value, Context context) {
        Control.runCommand(String.valueOf(value), BLX, Control.CommandType.GENERIC, context);
    }

    public static int getCurBlx() {
        return Utils.stringToInt(Utils.readFile(BLX));
    }

    public static boolean hasBlx() {
        return Utils.existFile(BLX);
    }

    public static void activateForceFastCharge(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", FORCE_FAST_CHARGE, Control.CommandType.GENERIC, context);
    }

    public static boolean isForceFastChargeActive() {
        return Utils.readFile(FORCE_FAST_CHARGE).equals("1");
    }

    public static boolean hasForceFastCharge() {
        return Utils.existFile(FORCE_FAST_CHARGE);
    }

}
