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

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.DAdapter;
import com.grarak.kerneladiutor.elements.DDivider;
import com.grarak.kerneladiutor.elements.cards.CardViewItem;
import com.grarak.kerneladiutor.elements.cards.PopupCardView;
import com.grarak.kerneladiutor.elements.cards.SeekBarCardView;
import com.grarak.kerneladiutor.elements.cards.SwitchCardView;
import com.grarak.kerneladiutor.elements.cards.UsageCardView;
import com.grarak.kerneladiutor.fragments.PathReaderFragment;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.fragments.ViewPagerFragment;
import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.kernel.CoreControl;
import com.grarak.kerneladiutor.utils.root.Control;
import com.kerneladiutor.library.root.RootFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class CoreControlFragment extends RecyclerViewFragment implements
        SwitchCardView.DSwitchCard.OnDSwitchCardListener, SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener {
    private SwitchCardView.DSwitchCard mbchCard;
    private SeekBarCardView.DSeekBarCard mMinLittleCard;
    private SeekBarCardView.DSeekBarCard mMaxLittleCard;
    private SeekBarCardView.DSeekBarCard mMinBigCard;
    private SeekBarCardView.DSeekBarCard mMaxBigCard;



    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);


        if (CoreControl.hasbch()) bchInit();
        if (CoreControl.hasMinLittle()) MinLittleInit();
        if (CoreControl.hasMaxLittle()) MaxLittleInit();
        if (CoreControl.hasMinBig()) MinBigInit();
        if (CoreControl.hasMaxBig()) MaxBigInit();
    }




    private void bchInit() {
        if (CoreControl.hasbch()) {
            mbchCard = new SwitchCardView.DSwitchCard();
             mbchCard.setTitle(getString(R.string.bch));
             mbchCard.setDescription(getString(R.string.bch_summary));
             mbchCard.setChecked(CoreControl.isbchActive());
             mbchCard.setOnDSwitchCardListener(this);

            addView(mbchCard);
        }}
   
    private void MinLittleInit() {
     if (CoreControl.hasMinLittle()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 5; i++) list.add(String.valueOf(i));

            mMinLittleCard = new SeekBarCardView.DSeekBarCard(list);
            mMinLittleCard.setTitle(getString(R.string.minlittle));
            mMinLittleCard.setDescription(getString(R.string.minlittle_summary));
            mMinLittleCard.setProgress(CoreControl.getMinLittle() -1);
            mMinLittleCard.setOnDSeekBarCardListener(this);

            addView(mMinLittleCard);
        }
}

    private void MaxLittleInit() {
     if (CoreControl.hasMaxLittle()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 5; i++) list.add(String.valueOf(i));

            mMaxLittleCard = new SeekBarCardView.DSeekBarCard(list);
            mMaxLittleCard.setTitle(getString(R.string.maxlittle));
            mMaxLittleCard.setDescription(getString(R.string.maxlittle_summary));
            mMaxLittleCard.setProgress(CoreControl.getMaxLittle() -1);
            mMaxLittleCard.setOnDSeekBarCardListener(this);

            addView(mMaxLittleCard);
        }

 }   

    private void MinBigInit() {
     if (CoreControl.hasMinBig()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 5; i++) list.add(String.valueOf(i));

            mMinBigCard = new SeekBarCardView.DSeekBarCard(list);
            mMinBigCard.setTitle(getString(R.string.minbig));
            mMinBigCard.setDescription(getString(R.string.minbig_summary));
            mMinBigCard.setProgress(CoreControl.getMinBig() -1);
            mMinBigCard.setOnDSeekBarCardListener(this);

            addView(mMinBigCard);
        }
}

    private void MaxBigInit() {
     if (CoreControl.hasMaxBig()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 5; i++) list.add(String.valueOf(i));

            mMaxBigCard = new SeekBarCardView.DSeekBarCard(list);
            mMaxBigCard.setTitle(getString(R.string.maxbig));
            mMaxBigCard.setDescription(getString(R.string.maxbig_summary));
            mMaxBigCard.setProgress(CoreControl.getMaxBig() -1);          
            mMaxBigCard.setOnDSeekBarCardListener(this);

            addView(mMaxBigCard);
        }

 }   


    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mbchCard)
            CoreControl.activatebch(checked, getActivity());
    }
    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
         if (dSeekBarCard == mMinLittleCard)
            CoreControl.setMinLittle(position + 1, getActivity());
         else if (dSeekBarCard == mMaxLittleCard)
            CoreControl.setMaxLittle(position + 1, getActivity());
         else if (dSeekBarCard == mMinBigCard)
            CoreControl.setMinBig(position + 1, getActivity());
         else if (dSeekBarCard == mMaxBigCard)
            CoreControl.setMaxBig(position + 1, getActivity());
}
}
