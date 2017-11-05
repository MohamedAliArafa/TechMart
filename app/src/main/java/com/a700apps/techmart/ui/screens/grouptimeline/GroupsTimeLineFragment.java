package com.a700apps.techmart.ui.screens.grouptimeline;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.GroupViewPagerAdapter;
import com.a700apps.techmart.adapter.ViewPagerAdapter;
import com.a700apps.techmart.data.model.GroupTimeLineData;
import com.a700apps.techmart.data.model.TimeLineData;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.GroupPagerFragment;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.ui.screens.timeline.GroupTimeLineFragment;
import com.a700apps.techmart.ui.screens.timelinedetails.DetailsActivity;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.ClickableViewPager;
import com.a700apps.techmart.utils.DisbledViewPagerScrolling;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsTimeLineFragment extends Fragment implements View.OnClickListener, GroupTimlineView {

    ImageView imageView4;
    ImageView mProfileImageView, mNotificationImageView;

    private static ClickableViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    CirclePageIndicator indicator;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private DisbledViewPagerScrolling mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private LinearLayout mTabContainer;
    private GroupTimeLinePresenter presenter;
    int intValue;
    Dialog dialogsLoading;

    void init(View view) {
        mPager = (ClickableViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
    }

    void getExtra() {
        Bundle bundle = getArguments();
        intValue = bundle.getInt("selectedCategory", 0);
        Log.e("KhaledInt", "" + intValue);
//        String.valueOf(intValue)
//        presenter.getGroupCategory(String.valueOf(intValue), "1f443dc9-43f7-4f92-9e3f-76805cd0823c");
        presenter.getTimeline(PreferenceHelper.getUserId(getActivity()), intValue, "0");

    }

    public GroupsTimeLineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Globals.mIndex = -1;
        Globals.R_Index_group=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups_timeline, container, false);
        presenter = new GroupTimeLinePresenter();
        presenter.attachView(this);
        init(view);
        getExtra();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (DisbledViewPagerScrolling) view.findViewById(R.id.vp_timeline);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabContainer = (LinearLayout) view.findViewById(R.id.lin_category);
        for (int i = 0; i < mTabContainer.getChildCount(); i += 2) {
            mTabContainer.getChildAt(i).setOnClickListener(this);
        }
        mTabContainer.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.bt_1));
        mTabContainer.getChildAt(1).setVisibility(View.INVISIBLE);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                mTabContainer.getChildAt(position * 2).setBackground(getResources().getDrawable(R.drawable.bt_1));
//                for (int i = 0; i < mTabContainer.getChildCount(); i++) {
//                    if (i != position * 2 && i % 2 == 0)
//                        mTabContainer.getChildAt(i).setBackground(null);

//                    mTabContainer.getChildAt(1).setVisibility(View.VISIBLE);
//                    mTabContainer.getChildAt(3).setVisibility(View.INVISIBLE);
//                    mTabContainer.getChildAt(5).setVisibility(View.INVISIBLE);
//                }

//                if (position == 1) {
//                    mTabContainer.getChildAt(1).setVisibility(View.INVISIBLE);
//                    mTabContainer.getChildAt(3).setVisibility(View.VISIBLE);
//                    mTabContainer.getChildAt(5).setVisibility(View.VISIBLE);
//                } else if (position == 2) {
//                    mTabContainer.getChildAt(1).setVisibility(View.INVISIBLE);
//                    mTabContainer.getChildAt(3).setVisibility(View.INVISIBLE);
//                    mTabContainer.getChildAt(5).setVisibility(View.VISIBLE);
//                } else if (position == 3) {
//                    mTabContainer.getChildAt(1).setVisibility(View.VISIBLE);
//                    mTabContainer.getChildAt(3).setVisibility(View.INVISIBLE);
//                    mTabContainer.getChildAt(5).setVisibility(View.INVISIBLE);
//                }
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 3) {
//                    Intent intent = new Intent(getActivity(), GroupActivity.class);
//                    intent.putExtra("string_key" , intValue);
//                    startActivity(intent);

                    Bundle bundle = new Bundle();
                    bundle.putInt("string_key", intValue);
                    ((HomeActivity) getActivity()).openFragment(GroupFragment.class, bundle);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imageView4 = (ImageView) view.findViewById(R.id.imageView4);

        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.openActivity(getActivity(), EditProfileActivity.class, false);
                ((HomeActivity) getActivity()).openFragment(EditProfileFragment.class , null);
            }
        });

        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);


            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.openActivity(getActivity(), HomeActivity.class, true);
                ((HomeActivity) getActivity()).openDrawer();

            }
        });
//

        return view;
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.tv_timeline:
                mViewPager.setCurrentItem(0);
                mTabContainer.getChildAt(2).setBackground(null);
                mTabContainer.getChildAt(4).setBackground(null);
                mTabContainer.getChildAt(1).setVisibility(View.INVISIBLE);
                mTabContainer.getChildAt(3).setVisibility(View.VISIBLE);
                mTabContainer.getChildAt(5).setVisibility(View.VISIBLE);

                mTabContainer.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.bt_1));
//                for (int i = 0; i < mTabContainer.getChildCount(); i++) {
//                    if (i != position)
//                        mTabContainer.getChildAt(i).setBackground(null);
//                }
                break;
            case R.id.tv_posts:
                mViewPager.setCurrentItem(1);

                mTabContainer.getChildAt(0).setBackground(null);
                mTabContainer.getChildAt(4).setBackground(null);
                mTabContainer.getChildAt(2).setBackground(getResources().getDrawable(R.drawable.bt_1));

                mTabContainer.getChildAt(1).setVisibility(View.INVISIBLE);
                mTabContainer.getChildAt(3).setVisibility(View.INVISIBLE);
                mTabContainer.getChildAt(5).setVisibility(View.VISIBLE);

                break;
            case R.id.tv_event:
                mViewPager.setCurrentItem(2);
                mTabContainer.getChildAt(0).setBackground(null);
                mTabContainer.getChildAt(2).setBackground(null);
                mTabContainer.getChildAt(4).setBackground(getResources().getDrawable(R.drawable.bt_1));
//                for (int i = 0; i < mTabContainer.getChildCount(); i++) {
//                    if (i != pos3)
//                        mTabContainer.getChildAt(i).setBackground(null);
//                }
                mTabContainer.getChildAt(1).setVisibility(View.VISIBLE);
                mTabContainer.getChildAt(3).setVisibility(View.INVISIBLE);
                mTabContainer.getChildAt(5).setVisibility(View.INVISIBLE);

                break;
            case R.id.tv_group:
                mViewPager.setCurrentItem(3);
//                mTabContainer.getChildAt(1).setBackground(null);
//                mTabContainer.getChildAt(2).setBackground(null);
//                mTabContainer.getChildAt(0).setBackground(null);
//                mTabContainer.getChildAt(pos4).setBackground(getResources().getDrawable(R.drawable.bt_1));
//                for (int i = 0; i < mTabContainer.getChildCount(); i++) {
//                    if (i != pos4)
//                        mTabContainer.getChildAt(i).setBackground(null);
//                }
                break;
        }
    }

    @Override
    public void showLoadingProgress() {
//        indicatorView.setVisibility(View.VISIBLE);
//
//        indicatorView.show();
        dialogsLoading = new loadingDialog().showDialog(getActivity());
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
//        indicatorView.hide();
    }


    static void openDetails(Context context, String type, List<TimeLineData.ResultEntity> mTimeLineList, int index) {
//        ActivityUtils.openActivity(context, DetailsActivity.class, false);
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("Type", type);
        intent.putExtra("Index", index);
        intent.putParcelableArrayListExtra("Timeline", (ArrayList<? extends Parcelable>) mTimeLineList);
        context.startActivity(intent);
    }


    @Override
    public void updateUi(final List<TimeLineData.ResultEntity> TimelineList) {
//        mPager.setAdapter(new GroupViewPagerAdapter(getActivity(), TimelineList));
        mPager.setAdapter(new ViewPagerAdapter(getActivity(), TimelineList));



        mPager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openDetails(getActivity(), String.valueOf(TimelineList.get(currentPage).getType()), TimelineList, currentPage);
            }
        });


        final float density = getResources().getDisplayMetrics().density;
        indicator.setViewPager(mPager);
//Set circle indicator radius
        indicator.setRadius(3 * density);


        if (TimelineList.size() > 5) {
            NUM_PAGES = 5;
        } else {
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
    public void onResume() {
        super.onResume();
        if (Globals.mIndex != -1)
            mViewPager.setCurrentItem(Globals.mIndex);
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

                    GroupTimelineFragment gouprTimeline = new GroupTimelineFragment();
                    Bundle argumentsgroup = new Bundle();
                    argumentsgroup.putInt("string_key", intValue);
                    gouprTimeline.setArguments(argumentsgroup);
                    return gouprTimeline;

                case 1:
//                    setupGroupPost();
                    GroupPostFragment POST = new GroupPostFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("string_key", intValue);
                    POST.setArguments(arguments);
                    return POST;
//                    addFragment(arguments, new GroupPostFragment());
                case 2:
//                    setupGroupEvent();
                    GroupEventFragment Event = new GroupEventFragment();
                    Bundle argumentsEvent = new Bundle();
                    argumentsEvent.putInt("string_key", intValue);
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
                    return "Group Members";
            }
            return null;
        }
    }

    protected void addFragment(Bundle bundle, Fragment fragment) {
        //get the transaction
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (bundle != null)
            fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}


///////////////////////////////


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        ActivityUtils.openActivity(getActivity(), HomeActivity.class, true);
//
//    }




