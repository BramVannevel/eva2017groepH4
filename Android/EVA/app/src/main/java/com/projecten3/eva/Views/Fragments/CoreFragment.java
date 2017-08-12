package com.projecten3.eva.Views.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projecten3.eva.Adapters.ChallengeDaysAdapter;
import com.projecten3.eva.Adapters.CoreAdapter;
import com.projecten3.eva.Helpers.CreateCoreButtons;
import com.projecten3.eva.Model.CoreButtons;
import com.projecten3.eva.Model.Day;
import com.projecten3.eva.R;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoreFragment extends Fragment {
    private static final String TAG = "CoreFragment";
    private ArrayList<CoreButtons> buttonList;
    private GridLayoutManager gridLayoutManager;
    private CoreAdapter coreAdapter;

    @BindView(R.id.rv_core_buttons)
    RecyclerView buttons;

    /**
     * required empty constructor
     */
    public CoreFragment() {
    }

    public static CoreFragment newInstance() {
        return new CoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_core, container, false);
        ButterKnife.bind(this, view);
        initCoreButtonsUI();
        return view;
    }

    /**
     * init of the recyclerview with core buttons.
     */
    private void initCoreButtonsUI() {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        buttonList = initCoreButtons();
        coreAdapter = new CoreAdapter(buttonList);
        buttons.setHasFixedSize(true);
        buttons.setLayoutManager(gridLayoutManager);
        buttons.setAdapter(coreAdapter);
    }

    /**
     * using the helper class createcorebuttons so that, even if static in this context, the buttons can be added
     * dynamically on one place only.
     * Not using a static final list because we need to pass the context to acces the resource files,
     * making it static would cause a memory leak (holding the context in memory when the fragment could be destroyed).
     * @return
     */
    private ArrayList<CoreButtons> initCoreButtons() {
        return new CreateCoreButtons(getContext()).getButtons();
    }
}
