package com.a700apps.techmart.ui.screens.grouptimeline;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.GroupViewPagerAdapter;
import com.a700apps.techmart.adapter.ViewPagerAdapter;
import com.a700apps.techmart.data.model.GroupTimeLine;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupActivity;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.GroupPagerFragment;
import com.a700apps.techmart.ui.screens.mygroup.MyGroubListActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.ui.screens.timeline.EventFragment;
import com.a700apps.techmart.ui.screens.timeline.GroupTimeLineFragment;
import com.a700apps.techmart.ui.screens.timeline.PostsFragment;
import com.a700apps.techmart.ui.screens.timeline.TimeLineMainFragment;
import com.a700apps.techmart.ui.screens.timeline.TimeLinePresenter;
import com.a700apps.techmart.ui.screens.timeline.TimelineFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.viewpagerindicator.CirclePageIndicator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by samir salah on 9/13/2017.
 */

public class GroupsTimLineActivity  extends AppCompatActivity implements View.OnClickListener ,GroupTimlineView{
    ImageView imageView4;
    ImageView mProfileImageView, mNotificationImageView;
    public AVLoadingIndicatorView indicatorView;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    CirclePageIndicator indicator;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private LinearLayout mTabContainer;
    private GroupTimeLinePresenter presenter;
    int intValue;

    void init() {
        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);
    }

    void getExtra() {
        Intent mIntent = getIntent();
         intValue = mIntent.getIntExtra("selectedCategory",0);
        presenter.getTimeline(PreferenceHelper.getUserId(this),intValue, "0",1,1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouptimeline);
        presenter = new GroupTimeLinePresenter();
        presenter.attachView(this);
        init();
        indicatorView= (AVLoadingIndicatorView) findViewById(R.id.avi);
        getExtra();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_timeline);
//        presenter.getTimeline(PreferenceHelper.getUserId(this), "0");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabContainer = (LinearLayout) findViewById(R.id.lin_category);
        for (int i = 0; i < mTabContainer.getChildCount(); i++) {
            mTabContainer.getChildAt(i).setOnClickListener(GroupsTimLineActivity.this);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                mTabContainer.getChildAt(position).setBackground(getResources().getDrawable(R.drawable.bt_1));
                for (int i = 0; i < mTabContainer.getChildCount(); i++) {
                    if (i != position)
                        mTabContainer.getChildAt(i).setBackground(null);
                }
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 3) {
                    Intent intent = new Intent(GroupsTimLineActivity.this, GroupActivity.class);
                    intent.putExtra("string_key" , intValue);
                    startActivity(intent);

//                    ActivityUtils.openActivity(GroupsTimLineActivity.this, GroupActivity.class, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imageView4 = (ImageView) findViewById(R.id.imageView4);

        mProfileImageView = (ImageView) findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) findViewById(R.id.new_profile);

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(GroupsTimLineActivity.this, EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(GroupsTimLineActivity.this, NotificationActivity.class, false);


            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.openActivity(GroupsTimLineActivity.this, HomeActivity.class, true);
                finish();

            }
        });
//
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        ActivityUtils.openActivity(GroupsTimLineActivity.this, HomeActivity.class, true);

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.tv_timeline:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_posts:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tv_event:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tv_group:
                mViewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void showLoadingProgress() {
        indicatorView.setVisibility(View.VISIBLE);

        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
        indicatorView.hide();
    }

    @Override
    public void updateUi(List<TimeLineData.ResultEntity> TimelineList) {
        mPager.setAdapter(new GroupViewPagerAdapter(GroupsTimLineActivity.this, TimelineList));

        final float density = getResources().getDisplayMetrics().density;
        indicator.setViewPager(mPager);
//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = TimelineList.size();

        // Auto start of viewpager
        final Handler handler;
        handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @Override
    public void updateUiMore(List<TimeLineData.ResultEntity> TimelineList) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
//                    return new TimeLineMainFragment();

                GroupTimelineFragment gouprTimeline =new GroupTimelineFragment();
                Bundle argumentsgroup = new Bundle();
                argumentsgroup.putInt( "string_key" , intValue);
                gouprTimeline.setArguments(argumentsgroup);
                return gouprTimeline;

                case 1:
//                    setupGroupPost();
                    GroupPostFragment POST =new GroupPostFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt( "string_key" , intValue);
                    POST.setArguments(arguments);
                    return POST;
//                    addFragment(arguments, new GroupPostFragment());
                case 2:
//                    setupGroupEvent();
                    GroupEventFragment Event =new GroupEventFragment();
                    Bundle argumentsEvent = new Bundle();
                    argumentsEvent.putInt( "string_key" , intValue);
                    Event.setArguments(argumentsEvent);
                    return Event;

                case 3:
                    return new GroupPagerFragment();

                default:
                    return new GroupTimeLineFragment();
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Timeline";
                case 1:
                    return "Posts";
                case 2:
                    return "Events";
                case 3:
                    return "Group Member";
            }
            return null;
        }
    }



    private void setupGroupEvent( ) {

        Bundle arguments = new Bundle();
        arguments.putInt( "string_key" , intValue);
        addFragment(arguments, new GroupEventFragment());
    }

    private void setupGroupPost( ) {

        Bundle arguments = new Bundle();
        arguments.putInt( "string_key" , intValue);
        addFragment(arguments, new GroupPostFragment());
    }

    protected void addFragment(Bundle bundle, Fragment fragment) {
        //get the transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(bundle != null)
            fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
