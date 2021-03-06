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

package com.grarak.kerneladiutor.fragments.kernel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.cards.CardViewItem;
import com.grarak.kerneladiutor.elements.cards.SeekBarCardView;
import com.grarak.kerneladiutor.elements.cards.SwitchCardView;
import com.grarak.kerneladiutor.elements.cards.UsageCardView;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.kernel.Battery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 03.01.15.
 */
public class BatteryFragment extends RecyclerViewFragment implements
        SwitchCardView.DSwitchCard.OnDSwitchCardListener,
        SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener {

    private UsageCardView.DUsageCard mBatteryLevelCard;
    private CardViewItem.DCardView mBatteryVoltageCard, mBatteryVoltage1Card, mBatteryTemperature;

    private SwitchCardView.DSwitchCard mForceFastChargeCard;

    private SeekBarCardView.DSeekBarCard mBlxCard;


    private SwitchCardView.DSwitchCard mCustomChargeRateEnableCard;
    private SeekBarCardView.DSeekBarCard mChargingRateCardAC;
    private SeekBarCardView.DSeekBarCard mChargingRateCardUSB;
    private SeekBarCardView.DSeekBarCard mlowpowervalueCard;


    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        batteryLevelInit();
        lowpowervalueInit();
        batteryVoltageInit();
        batteryVoltage1Init();
        batteryTemperatureInit();
        if (Battery.hasForceFastCharge()) forceFastChargeInit();
        if (Battery.hasBlx()) blxInit();
        if (Battery.hasChargeRate()) chargerateInit();

        try {
            getActivity().registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public void postInit(Bundle savedInstanceState) {
        super.postInit(savedInstanceState);
        if (getCount() < 4) showApplyOnBoot(false);
    }


    private void batteryLevelInit() {
        mBatteryLevelCard = new UsageCardView.DUsageCard();
        mBatteryLevelCard.setText(getString(R.string.battery_level));

        addView(mBatteryLevelCard);
    }

    private void lowpowervalueInit() {
         if (Battery.haslowpowervalue()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 101; i++) list.add(String.valueOf(i));

            mlowpowervalueCard = new SeekBarCardView.DSeekBarCard(list);
            mlowpowervalueCard.setTitle(getString(R.string.lowpowervalue));
            mlowpowervalueCard.setDescription(getString(R.string.lowpowervalue_summary));
            mlowpowervalueCard.setProgress(Battery.getlowpowervalue());
            mlowpowervalueCard.setOnDSeekBarCardListener(this);

            addView(mlowpowervalueCard);
        }}

    private void batteryVoltageInit() {
        mBatteryVoltageCard = new CardViewItem.DCardView();
        mBatteryVoltageCard.setTitle(getString(R.string.battery_voltage));

        addView(mBatteryVoltageCard);
    }
    private void batteryVoltage1Init() {
        mBatteryVoltage1Card = new CardViewItem.DCardView();
        mBatteryVoltage1Card.setTitle(getString(R.string.battery_voltage_current));

        addView(mBatteryVoltage1Card);
    }
    private void batteryTemperatureInit() {
        mBatteryTemperature = new CardViewItem.DCardView();
        mBatteryTemperature.setTitle(getString(R.string.battery_temperature));

        addView(mBatteryTemperature);
    }



    private void forceFastChargeInit() {
        mForceFastChargeCard = new SwitchCardView.DSwitchCard();
        mForceFastChargeCard.setTitle(getString(R.string.usb_fast_charge));
        mForceFastChargeCard.setDescription(getString(R.string.usb_fast_charge_summary));
        mForceFastChargeCard.setChecked(Battery.isForceFastChargeActive());
        mForceFastChargeCard.setOnDSwitchCardListener(this);

        addView(mForceFastChargeCard);
    }

    private void blxInit() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 101; i++) list.add(String.valueOf(i));

        mBlxCard = new SeekBarCardView.DSeekBarCard(list);
        mBlxCard.setTitle(getString(R.string.blx));
        mBlxCard.setDescription(getString(R.string.blx_summary));
        mBlxCard.setProgress(Battery.getCurBlx());
        mBlxCard.setOnDSeekBarCardListener(this);

        addView(mBlxCard);
    }

    private void chargerateInit() {



        if (Battery.hasChargingRateAC()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 221; i++) list.add((i * 10) + getString(R.string.ma));

            mChargingRateCardAC = new SeekBarCardView.DSeekBarCard(list);
            mChargingRateCardAC.setTitle(getString(R.string.charge_rate_ac));
            mChargingRateCardAC.setDescription(getString(R.string.charge_rate_ac_summary));
            mChargingRateCardAC.setProgress(Battery.getChargingRateAC() / 10);
            mChargingRateCardAC.setOnDSeekBarCardListener(this);

            addView(mChargingRateCardAC);
        }

        if (Battery.hasChargingRateUSB()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 151; i++) list.add((i * 10) + getString(R.string.ma));

            mChargingRateCardUSB = new SeekBarCardView.DSeekBarCard(list);
            mChargingRateCardUSB.setTitle(getString(R.string.charge_rate_usb));
            mChargingRateCardUSB.setDescription(getString(R.string.charge_rate_usb_summary));
            mChargingRateCardUSB.setProgress(Battery.getChargingRateUSB() / 10);
            mChargingRateCardUSB.setOnDSeekBarCardListener(this);

            addView(mChargingRateCardUSB);
        }
    }

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            int voltage1 = (Battery.getChargeRate());

            if (mBatteryLevelCard != null) mBatteryLevelCard.setProgress(level);
            if (mBatteryVoltageCard != null)
                mBatteryVoltageCard.setDescription(voltage + getString(R.string.mv));
            if (mBatteryVoltage1Card != null)
                mBatteryVoltage1Card.setDescription(voltage1 + getString(R.string.mv));
            if (mBatteryTemperature != null) {
                double celsius = (double) temperature / 10;
                mBatteryTemperature.setDescription(Utils.formatCelsius(celsius) + " " + Utils.celsiusToFahrenheit(celsius));
            }
        }
    };

    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mForceFastChargeCard)
            Battery.activateForceFastCharge(checked, getActivity());
        else if (dSwitchCard == mCustomChargeRateEnableCard)
            Battery.activateCustomChargeRate(checked, getActivity());
    }

    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
        if (dSeekBarCard == mBlxCard)
            Battery.setBlx(position, getActivity());
        else if (dSeekBarCard == mChargingRateCardAC)
            Battery.setChargingRateAC((position * 10) ,getActivity());
        else if (dSeekBarCard == mChargingRateCardUSB)
            Battery.setChargingRateUSB((position * 10) ,getActivity());
        else if (dSeekBarCard == mlowpowervalueCard)
            Battery.setlowpowervalue((position) ,getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(mBatInfoReceiver);
        } catch (IllegalArgumentException ignored) {
        }
    }
}
