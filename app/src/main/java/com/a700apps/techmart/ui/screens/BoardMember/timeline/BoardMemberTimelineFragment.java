package com.a700apps.techmart.ui.screens.BoardMember.timeline;

import android.os.Bundle;
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

import com.a700apps.techmart.R;
import com.a700apps.techmart.ui.screens.BoardMember.JoinRequests.JoinRequestsFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmemberapproval.BoardApprovalFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmemberevent.BoardEventFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembermembers.BoardMemberFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.boardmembertimeline.TimelineBoardMemberFragment;
import com.a700apps.techmart.ui.screens.BoardMember.timeline.bordmemberpost.BoardPostFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.mygroup.GroupPagerFragment;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.utils.ActivityUtils;

import butterknife.ButterKnife;

public class BoardMemberTimelineFragment extends Fragment implements View.OnClickListener {

    ImageView mProfileImageView, mNotificationImageView, mBackImageView;
    TextView mTimline, mPost, mEvent, mMember, mMemberApprove;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private LinearLayout mTabContainer;

    int intValue;

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


        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        mBackImageView = (ImageView) view.findViewById(R.id.imageView4);

        mMember = (TextView) view.findViewById(R.id.tv_member);
        mMemberApprove = (TextView) view.findViewById(R.id.tv_member_approve);
        mTimline = (TextView) view.findViewById(R.id.tv_timeline);
        mPost = (TextView) view.findViewById(R.id.tv_posts);
        mEvent = (TextView) view.findViewById(R.id.tv_event);
        mTimline.setBackground(getResources().getDrawable(R.drawable.bt_1));

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

//        presenter = new TimeLinePresenter();
//        presenter.attachView(this);
        Log.e("Resume", "on view created");
        init(view);


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
//                    ActivityUtils.openActivity(getActivity(), MyGroubListActivity.class, false);
//                    ((HomeActivity) getActivity()).addFragmentToBackStack(getFragmentManager(), R.id.fragment_container, new BoardApprovalFragment(), false, false);
                    ((HomeActivity) getActivity()).openFragment(JoinRequestsFragment.class,null);
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
            case R.id.tv_member:
                mViewPager.setCurrentItem(3);
            case R.id.tv_member_approve:
                mViewPager.setCurrentItem(4);
                break;
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
//                    ActivityUtils.openActivity(getActivity() , JoinRequestsActivity.class , false);
                    JoinRequestsFragment joinRequestsFragment = new JoinRequestsFragment();

                    return joinRequestsFragment;

                default:
                    return new JoinRequestsFragment();
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
