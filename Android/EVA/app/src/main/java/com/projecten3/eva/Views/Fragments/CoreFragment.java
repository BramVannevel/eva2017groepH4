package com.projecten3.eva.Views.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projecten3.eva.Adapters.ChallengeDaysAdapter;
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
        return view;
    }

}
