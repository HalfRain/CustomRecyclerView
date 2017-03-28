package com.ddinfo.toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ddinfo.toolbar.cusview.CustomRecycleView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.custom_recycler_view)
    CustomRecycleView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ArrayList<String> names = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            names.add("我是机器人00" + i + "号");
        }
        recyclerView.refreshAllItems(names);
    }
}
