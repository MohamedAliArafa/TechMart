package com.a700apps.techmart.ui.screens.timeline;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.ViewPagerAdapter;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.GroupPagerFragment;
import com.a700apps.techmart.ui.screens.mygroup.MyGroupsListFragment;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.AppUtils;
import com.a700apps.techmart.utils.DialogCreator;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.viewpagerindicator.CirclePageIndicator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * Created by samir salah on 8/17/2017.
 */

public class TimelineFragment extends Fragment implements View.OnClickListener, TimeLineView {

    ImageView mProfileImageView, mNotificationImageView, imageView4;
    TextView mGroup, mTimline, mPost, mEvent;
    private TimeLinePresenter presenter;
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




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_timeline, container, false);
    }

    void init(View view) {
        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.vp_timeline);


        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        imageView4 = (ImageView) view.findViewById(R.id.imageView4);

        mGroup = (TextView) view.findViewById(R.id.tv_group);
        mTimline = (TextView) view.findViewById(R.id.tv_timeline);
        mPost = (TextView) view.findViewById(R.id.tv_posts);
        mEvent = (TextView) view.findViewById(R.id.tv_event);
        mTimline.setBackground(getResources().getDrawable(R.drawable.bt_1));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter = new TimeLinePresenter();
        presenter.attachView(this);
        Log.e("Resume", "on view created");
        init(view);


        if (!AppUtils.isInternetAvailable(getActivity())) {
            Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        } else {
            presenter.getTimeline(PreferenceHelper.getUserId(getActivity()), "0", getActivity());
        }
        setViewPager(view);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openDrawer();
            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), EditProfileActivity.class, false);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setupPagerData() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
    }

    void setViewPager(View view) {

        setupPagerData();
        mTabContainer = (LinearLayout) view.findViewById(R.id.lin_category);
        for (int i = 0; i < mTabContainer.getChildCount(); i++) {
            mTabContainer.getChildAt(i).setOnClickListener(this);
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
//                    ActivityUtils.openActivity(getActivity(), MyGroubListActivity.class, false);

                    ((HomeActivity) getActivity()).addFragmentToBackStack(getFragmentManager(), R.id.fragment_container, new MyGroupsListFragment(), false, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
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
//        indicatorView.setVisibility(View.VISIBLE);
//        indicatorView.show();
    }

    @Override
    public void dismissLoadingProgress() {
//        indicatorView.hide();
    }

    @Override
    public void updateUi(final List<TimeLineData.ResultEntity> TimelineList) {


        mPager.setAdapter(new ViewPagerAdapter(getActivity(), TimelineList));

        final float density = getResources().getDisplayMetrics().density;
        indicator.setViewPager(mPager);
//Set circle indicator radius
        indicator.setRadius(3 * density);

//        NUM_PAGES = TimelineList.size();
        if (TimelineList.size()>5){
            NUM_PAGES = 5;
        }else {
            NUM_PAGES = TimelineList.size();
        }

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
    public void showErrorDialog(int error) {
        DialogCreator.showOneButtonDialog(getActivity(), R.string.check_internet, "Error", null);

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
            Log.e("positonview", position + "");
            switch (position) {

                case 0:
                    return new TimeLineMainFragment();
                case 1:
                    return new PostsFragment();
                case 2:
                    return new EventFragment();
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
                    return "Groups";
            }
            return null;
        }
    }

}
