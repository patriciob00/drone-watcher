package com.patriciobruno.dronewatcher;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class drones extends AppCompatActivity {
    CollapsingToolbarLayout collapse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drones);
        setCollapseBar();
    }

    public void setCollapseBar () {
        collapse = (CollapsingToolbarLayout) findViewById(R.id.collapseDrone);
        collapse.setTitle("Drones");
    }
}
