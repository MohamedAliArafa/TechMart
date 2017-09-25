package com.a700apps.techmart.ui.screens.meeting;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;

import java.util.List;

public class MeetingActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    MettingPressenter mettingPressenter;
    private TextView dayTextView;
    private TextView tomorrowTextView;
    private TextView weekTextView;
    private TextView monthTextView;
    private TextView customTextView;
    private FrameLayout container;
    private View dayView;
    private View tomorrowView;
    private View weekView;
    private View monthView;
    private View customView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        initView();
    }

    private void initView() {
        dayTextView = (TextView) findViewById(R.id.day_text_view);
        tomorrowTextView = (TextView) findViewById(R.id.tomorrow_text_view);
        weekTextView = (TextView) findViewById(R.id.week_text_view);
        monthTextView = (TextView) findViewById(R.id.month_text_view);
        customTextView = (TextView) findViewById(R.id.custom_text_view);
        container = (FrameLayout) findViewById(R.id.container_framelayout);

        dayView = (View) findViewById(R.id.day_view);
        tomorrowView = (View) findViewById(R.id.tomorrow_view);
        weekView = (View) findViewById(R.id.week_view);
        monthView = (View) findViewById(R.id.month_view);
        customView = (View) findViewById(R.id.custom_view);

        openFirstView();

        dayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayView.setVisibility(View.VISIBLE);
                tomorrowView.setVisibility(View.GONE);
                weekView.setVisibility(View.GONE);
                monthView.setVisibility(View.GONE);
                customView.setVisibility(View.GONE);
                openFragment(FragmentDay.class, null);
            }
        });
        tomorrowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomorrowView.setVisibility(View.VISIBLE);
                dayView.setVisibility(View.GONE);
                weekView.setVisibility(View.GONE);
                monthView.setVisibility(View.GONE);
                customView.setVisibility(View.GONE);
                openFragment(FragmentTomorrow.class, null);
            }
        });
        weekTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekView.setVisibility(View.VISIBLE);
                dayView.setVisibility(View.GONE);
                tomorrowView.setVisibility(View.GONE);
                monthView.setVisibility(View.GONE);
                customView.setVisibility(View.GONE);
                openFragment(FragmentWeek.class, null);
            }
        });
        monthTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthView.setVisibility(View.VISIBLE);
                dayView.setVisibility(View.GONE);
                tomorrowView.setVisibility(View.GONE);
                weekView.setVisibility(View.GONE);
                customView.setVisibility(View.GONE);
                openFragment(FragmentMonth.class, null);
            }
        });
        customTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customView.setVisibility(View.VISIBLE);
                dayView.setVisibility(View.GONE);
                tomorrowView.setVisibility(View.GONE);
                weekView.setVisibility(View.GONE);
                monthView.setVisibility(View.GONE);
                openFragment(FragmentCustom.class, null);
            }
        });


    }

    private void openFirstView() {
        dayView.setVisibility(View.VISIBLE);
        tomorrowView.setVisibility(View.GONE);
        weekView.setVisibility(View.GONE);
        monthView.setVisibility(View.GONE);
        customView.setVisibility(View.GONE);
        openFragment(FragmentDay.class, null);
    }

    public void openFragment(Class fragmentClass, Bundle bundle) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.container_framelayout, fragment);
        transaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
