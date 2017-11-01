package com.a700apps.techmart.ui.screens.BoardMember.timeline;

import android.app.Dialog;
import android.os.Bundle;
import android.renderscript.Double2;
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
import com.a700apps.techmart.data.model.StatisticModel;
import com.a700apps.techmart.ui.screens.BoardMember.JoinRequests.JoinRequestsFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmemberapproval.BoardApprovalFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmemberevent.BoardEventFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembermembers.BoardMemberFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.TimelineBoardMemberFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.bordmemberpost.BoardPostFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.GroupPagerFragment;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationFragment;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;
import com.dinuscxj.progressbar.CircleProgressBar;

import butterknife.ButterKnife;

public class BoardMemberTimelineFragment extends Fragment implements View.OnClickListener, StatisticalView {

    ImageView mProfileImageView, mNotificationImageView, mBackImageView;
    TextView mTimline, mPost, mEvent, mMember, mMemberApprove;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private LinearLayout mTabContainer;
    CircleProgressBar postCircleProgressBar, eventCircleProgressBar, memberCircleProgressBar;
    Dialog dialogsLoading;

    int intValue;
    StatisticPresenter presenter;

    TextView dayTextView, weekTextView, monthTextView, yearTextView;
    ImageView dayImageView, weekImageView, monthImageView, yearImageView;

    TextView posts_lbl, events_lbl, member_lbl;

    public BoardMemberTimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board_member_timeline, container, false);
    }

    void init(View view) {

        Bundle bundle = getArguments();
        intValue = bundle.getInt("selectedCategory", 0);

        mPager = (ViewPager) view.findViewById(R.id.pager);

//        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.vp_timeline);


        postCircleProgressBar = (CircleProgressBar) view.findViewById(R.id.postCircleProgressBar);
//        postCircleProgressBar.setProgress(40);

        eventCircleProgressBar = (CircleProgressBar) view.findViewById(R.id.eventCircleProgressBar);
//        eventCircleProgressBar.setProgress(40);

        memberCircleProgressBar = (CircleProgressBar) view.findViewById(R.id.memberCircleProgressBar);
//        memberCircleProgressBar.setProgress(40);


        dayTextView = view.findViewById(R.id.tv_day);
        monthTextView = view.findViewById(R.id.tv_month);
        weekTextView = view.findViewById(R.id.tv_week);
        yearTextView = view.findViewById(R.id.tv_year);

        dayImageView = view.findViewById(R.id.iv_today);
        weekImageView = view.findViewById(R.id.iv_week);
        monthImageView = view.findViewById(R.id.iv_month);
        yearImageView = view.findViewById(R.id.iv_year);


        posts_lbl = view.findViewById(R.id.post_lbl);
        events_lbl = view.findViewById(R.id.events_lbl);
        member_lbl = view.findViewById(R.id.member_lbl);

        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        mBackImageView = (ImageView) view.findViewById(R.id.imageView4);

        mMember = (TextView) view.findViewById(R.id.tv_member);
        mMemberApprove = (TextView) view.findViewById(R.id.tv_member_approve);
        mTimline = (TextView) view.findViewById(R.id.tv_timeline);
        mPost = (TextView) view.findViewById(R.id.tv_posts);
        mEvent = (TextView) view.findViewById(R.id.tv_event);

        mTimline.setBackground(getResources().getDrawable(R.drawable.bt_1));
        dayImageView.setBackground(getResources().getDrawable(R.drawable.arrow_signup));
        dayTextView.setTextColor(getResources().getColor(R.color.tab_menu_text_color));

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

//        presenter = new TimeLinePresenter();
//        presenter.attachView(this);
        Log.e("Resume", "on view created");
        init(view);
        presenter = new StatisticPresenter();
        presenter.attachView(this);


        presenter.getStatistic(PreferenceHelper.getUserId(getActivity()), Globals.GROUP_ID, 1);
//        if (!AppUtils.isInternetAvailable(getActivity())) {
//            Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
//        } else {
//            presenter.getTimeline(PreferenceHelper.getUserId(getActivity()), "0", getActivity());
//        }
        setViewPager(view);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openDrawer();
            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.CAME_FROM_BOARD_MEMBER= true;
                ((HomeActivity) getActivity()).openFragment(EditProfileFragment.class , null);
            }
        });


        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);
            }
        });

        dayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayTextView.setTextColor(getResources().getColor(R.color.tab_menu_text_color));
                weekTextView.setTextColor(getResources().getColor(R.color.white));
                monthTextView.setTextColor(getResources().getColor(R.color.white));
                yearTextView.setTextColor(getResources().getColor(R.color.white));

                dayImageView.setBackground(getResources().getDrawable(R.drawable.arrow_signup));
                weekImageView.setBackground(null);
                monthImageView.setBackground(null);
                yearImageView.setBackground(null);

                presenter.getStatistic(PreferenceHelper.getUserId(getActivity()), Globals.GROUP_ID, 1);
            }
        });

        weekTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekTextView.setTextColor(getResources().getColor(R.color.tab_menu_text_color));
                dayTextView.setTextColor(getResources().getColor(R.color.white));
                monthTextView.setTextColor(getResources().getColor(R.color.white));
                yearTextView.setTextColor(getResources().getColor(R.color.white));

                weekImageView.setBackground(getResources().getDrawable(R.drawable.arrow_signup));
                dayImageView.setBackground(null);
                monthImageView.setBackground(null);
                yearImageView.setBackground(null);

                presenter.getStatistic(PreferenceHelper.getUserId(getActivity()), Globals.GROUP_ID, 7);
            }
        });

        monthTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthTextView.setTextColor(getResources().getColor(R.color.tab_menu_text_color));
                dayTextView.setTextColor(getResources().getColor(R.color.white));
                weekTextView.setTextColor(getResources().getColor(R.color.white));
                yearTextView.setTextColor(getResources().getColor(R.color.white));

                monthImageView.setBackground(getResources().getDrawable(R.drawable.arrow_signup));
                dayImageView.setBackground(null);
                weekImageView.setBackground(null);
                yearImageView.setBackground(null);
                presenter.getStatistic(PreferenceHelper.getUserId(getActivity()), Globals.GROUP_ID, 30);
            }
        });

        yearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearTextView.setTextColor(getResources().getColor(R.color.tab_menu_text_color));
                dayTextView.setTextColor(getResources().getColor(R.color.white));
                weekTextView.setTextColor(getResources().getColor(R.color.white));
                monthTextView.setTextColor(getResources().getColor(R.color.white));

                yearImageView.setBackground(getResources().getDrawable(R.drawable.arrow_signup));
                dayImageView.setBackground(null);
                weekImageView.setBackground(null);
                monthImageView.setBackground(null);
                presenter.getStatistic(PreferenceHelper.getUserId(getActivity()), Globals.GROUP_ID, 365);
            }
        });

    }

    private void setupPagerData() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setOffscreenPageLimit(5);
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

                if (position == 4) {
                    ((HomeActivity) getActivity()).openFragment(JoinRequestsFragment.class, null);
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
//                mTabContainer.getChildAt(1).setVisibility(View.GONE);
//                mTabContainer.getChildAt(3).setVisibility(View.VISIBLE);
//                mTabContainer.getChildAt(5).setVisibility(View.VISIBLE);
//                mTabContainer.getChildAt(7).setVisibility(View.VISIBLE);
                break;
            case R.id.tv_posts:
                mViewPager.setCurrentItem(1);
//                mTabContainer.getChildAt(1).setVisibility(View.GONE);
//                mTabContainer.getChildAt(3).setVisibility(View.GONE);
//                mTabContainer.getChildAt(5).setVisibility(View.VISIBLE);
//                mTabContainer.getChildAt(7).setVisibility(View.VISIBLE);
                break;
            case R.id.tv_event:
                mViewPager.setCurrentItem(2);
//                mTabContainer.getChildAt(1).setVisibility(View.VISIBLE);
//                mTabContainer.getChildAt(3).setVisibility(View.GONE);
//                mTabContainer.getChildAt(5).setVisibility(View.GONE);
//                mTabContainer.getChildAt(7).setVisibility(View.VISIBLE);
                break;
            case R.id.tv_member:
                mViewPager.setCurrentItem(3);
//                mTabContainer.getChildAt(1).setVisibility(View.VISIBLE);
//                mTabContainer.getChildAt(3).setVisibility(View.VISIBLE);
//                mTabContainer.getChildAt(5).setVisibility(View.GONE);
//                mTabContainer.getChildAt(7).setVisibility(View.GONE);
                break;
            case R.id.tv_member_approve:
                mViewPager.setCurrentItem(4);
                break;
        }
    }

    @Override
    public void showLoadingProgress() {
        dialogsLoading = new loadingDialog().showDialog(getActivity());
    }

    @Override
    public void dismissLoadingProgress() {
        dialogsLoading.dismiss();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void updateUi(StatisticModel model) {

        if (model.getResult().getTotalPostCount() > 0) {
            double percentage = (double) model.getResult().getApprovedPostCount() / (double) model.getResult().getTotalPostCount() * 100;
            posts_lbl.setText(round(percentage  ,2) + " %");
            postCircleProgressBar.setProgress((int) percentage);
            Log.e("percentage", "" + percentage);
        } else {
            postCircleProgressBar.setProgress(0);
            posts_lbl.setText(0 + " %");
        }

        if (model.getResult().getTotalEventCount() > 0) {
            double percentage = (double) model.getResult().getApprovedEventsCount() / (double) model.getResult().getTotalEventCount() * 100;
            events_lbl.setText(round(percentage  ,2) + " %");
            eventCircleProgressBar.setProgress((int) percentage);
        } else {
            eventCircleProgressBar.setProgress(0);
            events_lbl.setText(0 + " %");
        }

        if (model.getResult().getTotalJoinRequestCount() > 0) {
            double percentage = (double) model.getResult().getApprovedJoinrequestCount() / (double) model.getResult().getTotalJoinRequestCount() * 100;
            member_lbl.setText(round(percentage  ,2) + " %");
            memberCircleProgressBar.setProgress((int) percentage);
        } else {
            memberCircleProgressBar.setProgress(0);
            member_lbl.setText(0 + " %");
        }

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
                    TimelineBoardMemberFragment TimelineBoard = new TimelineBoardMemberFragment();
                    Bundle argumentsgroup = new Bundle();
                    argumentsgroup.putInt("string_key", intValue);
                    TimelineBoard.setArguments(argumentsgroup);
                    return TimelineBoard;

                case 1:
                    BoardPostFragment postBoard = new BoardPostFragment();
                    Bundle argumentspost = new Bundle();
                    argumentspost.putInt("string_key", intValue);
                    postBoard.setArguments(argumentspost);
                    return postBoard;

                case 2:
                    BoardEventFragment eventBoard = new BoardEventFragment();
                    Bundle argumentsevent = new Bundle();
                    argumentsevent.putInt("string_key", intValue);
                    eventBoard.setArguments(argumentsevent);
                    return eventBoard;

                case 3:
                    BoardMemberFragment UsersBoard = new BoardMemberFragment();
                    Bundle argumentsusers = new Bundle();
                    argumentsusers.putInt("string_key", intValue);
                    UsersBoard.setArguments(argumentsusers);
                    return UsersBoard;
                case 4:
                    JoinRequestsFragment joinRequestsFragment = new JoinRequestsFragment();
                    return joinRequestsFragment;
                default:
//                    BoardMemberFragment UsersBoard2 = new BoardMemberFragment();
//                    Bundle argumentsusers2 = new Bundle();
//                    argumentsusers2.putInt("string_key", intValue);
//                    UsersBoard2.setArguments(argumentsusers2);
//                    return UsersBoard2;
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 5;
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
                    return "Members";
                case 4:
                    return "Membership Approvals";
            }
            return null;
        }
    }
}
