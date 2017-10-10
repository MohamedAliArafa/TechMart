package com.a700apps.techmart.ui.screens.home;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.NavDrawerAdapter;
import com.a700apps.techmart.data.network.MainApi;
import com.a700apps.techmart.ui.screens.groupmemberdetails.GroupFragment;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimeLineFragment;
import com.a700apps.techmart.ui.screens.joingroup.JoinGroupFragment;
import com.a700apps.techmart.ui.screens.login.LoginActivity;
import com.a700apps.techmart.ui.screens.meeting.MeetingActivity;
import com.a700apps.techmart.ui.screens.message.MessageFragment;
import com.a700apps.techmart.ui.screens.mygroup.MyGroupsListFragment;
import com.a700apps.techmart.ui.screens.mygroup.RelativeGroupsFragment;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.ui.screens.profile.MemberProfileFragment;
import com.a700apps.techmart.ui.screens.setting.SettingFragment;
import com.a700apps.techmart.ui.screens.timeline.EventFragment;
import com.a700apps.techmart.ui.screens.timeline.RelativeEventFragment;
import com.a700apps.techmart.ui.screens.timeline.TimelineFragment;
import com.a700apps.techmart.utils.ATCPrefManager;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.NavDrawerItem;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by samir salah on 8/15/2017.
 */

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, HomeView {

    public DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private NavDrawerAdapter mNavDrawerAdapter;
    String[] titles;
    private boolean mIsActivityFeedsVisible = true;
    HomePresenter presenter;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mHasDrawerLayout = false;
    private View mNavigationDrawerListHeader;
    private Toolbar mToolbar;
    ImageView mUserProfile;
    TextView mUserProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initializeToolbar();
        initializeViews();
        presenter = new HomePresenter();
        presenter.attachView(this);

        if (getIntent().getBooleanExtra("groupLayout", false)) {
            // show group relative
            RelativeGroupsFragment fragment = new RelativeGroupsFragment();
            Bundle bundle = new Bundle();

            bundle.putString("RelativId", getIntent().getStringExtra("RelativId"));
//            bundle.putString("GroupId", getIntent().getStringExtra("GroupId"));
            fragment.setArguments(bundle);
            addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, fragment, false, true);

            //show event relativs
        } else if (getIntent().getStringExtra("holder") != null) {

//            GroupsTimeLineFragment fragment = new GroupsTimeLineFragment();
            Bundle bundle = new Bundle();
            bundle.putString("selectedCategory", getIntent().getStringExtra("selectedCategory"));
//            fragment.setArguments(bundle);

            openFragment(GroupsTimeLineFragment.class, bundle);
//            addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, fragment, false
//                    , true);

        }

//        else if (getIntent().getStringExtra("RelativId") != null) {
//
//            MemberProfileFragment fragment = new MemberProfileFragment();
//            Bundle bundle = new Bundle();
//
//            bundle.putString("RelativId", getIntent().getStringExtra("RelativId"));
//            bundle.putString("GroupId", getIntent().getStringExtra("GroupId"));
//            fragment.setArguments(bundle);
//
//            addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, fragment, false
//                    , true);
//
//        }

        else {
            openTimeLine();
//            openFragment(TimelineFragment.class, null);

        }
        Timber.d("on create");
    }

    public void setToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_opened_desc, R.string.drawer_closed_desc);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_side_menu_logo, this.getTheme());
        mDrawerToggle.setHomeAsUpIndicator(drawable);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }


    private void initializeViews() {


        if (findViewById(R.id.drawer_layout) != null) {
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mHasDrawerLayout = true;
//            setupDrawerToggle();
        }
        setToolbar((Toolbar) findViewById(R.id.toolbar));
//        if (mHasDrawerLayout) getToolbar().setNavigationIcon(R.drawable.ic_side_menu);
        mDrawerListView = (ListView) findViewById(R.id.nav_drawer_listview);
        mNavigationDrawerListHeader = inflateAndAddNavDrawerHeader();
        mNavDrawerAdapter = new NavDrawerAdapter(getNavDrawerItems(), this);
        mDrawerListView.setAdapter(mNavDrawerAdapter);
        mUserProfile = mNavigationDrawerListHeader.findViewById(R.id.iv_user_nav_image);
        mUserProfileName = mNavigationDrawerListHeader.findViewById(R.id.tv_user_nav_name);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onNavDrawerItemClick(parent, position);
            }
        });

        mUserProfileName.setText(PreferenceHelper.getSavedUser(this).Name);
        String image = MainApi.IMAGE_IP + PreferenceHelper.getSavedUser(this).Photo;
//
//        Glide.with(HomeActivity.this)
//                .load("https://camo.mybb.com/e01de90be6012adc1b1701dba899491a9348ae79/687474703a2f2f7777772e6a71756572797363726970742e6e65742f696d616765732f53696d706c6573742d526573706f6e736976652d6a51756572792d496d6167652d4c69676874626f782d506c7567696e2d73696d706c652d6c69676874626f782e6a7067").placeholder(R.drawable.ic_profile)
//                .into(mUserProfile);


        Glide.with(HomeActivity.this)
                .load(image).placeholder(R.drawable.ic_profile)
                .listener(new LoggingListener<String, GlideDrawable>())
                .into(mUserProfile);

        Log.e("profileImage", image);
        mUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityUtils.openActivity(HomeActivity.this, EditProfileActivity.class, false);

//                addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, new EditProfileFragment(), false
//                        , false);
                openFragment(EditProfileFragment.class, null);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

    }


    //    public void loadProfileImage(){
//        Glide.with(HomeActivity.this)
//                .load(image).placeholder(R.drawable.ic_profile)
//                .listener(new LoggingListener<String, GlideDrawable>())
//                .into(mUserProfile);
//    }
    public class LoggingListener<T, R> implements RequestListener<T, R> {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            android.util.Log.d("GLIDE1", String.format(Locale.ROOT,
                    "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            android.util.Log.d("GLIDE2", String.format(Locale.ROOT,
                    "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
            return false;
        }
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.START);
    }


    private void initializeToolbar() {

    }

    private ArrayList<NavDrawerItem> getNavDrawerItems() {
        titles = getResources().getStringArray(R.array.nav_drawer_titles);
        ArrayList<NavDrawerItem> normalNavDrawerItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            normalNavDrawerItems.add(new NavDrawerItem(titles[i], NavDrawerItem.NAV_DRAWER_ICONS[i]));
        }
        return normalNavDrawerItems;
    }

    private View inflateAndAddNavDrawerHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.navigation_header_layout, mDrawerListView, false);
        mDrawerListView.addHeaderView(headerView);
        return headerView;
    }

    public void attachViewsListeners() {
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onNavDrawerItemClick(parent, position);
            }
        });


        mDrawerListView.setOnItemClickListener(HomeActivity.this);
    }

    @Override
    public void replaceFragment(Fragment fragment, @StringRes int toolbarTitle) {

    }

    @Override
    public void showUserDataInNavigationView() {

    }

    @Override
    public void setToolbarTitle(@StringRes int title) {

    }

    @Override
    public void setNotificationsCount(String count) {

    }

    @Override
    public void openHomeActivity() {

    }

    @Override
    public void showErrorDialog(@StringRes int error) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onNavDrawerItemClick(parent, position);
    }

    private void onNavDrawerItemClick(AdapterView adapterView, int position) {
        NavDrawerItem navDrawerItem = (NavDrawerItem) adapterView.getItemAtPosition(position);
        if (mHasDrawerLayout) mDrawerLayout.closeDrawers();
        if (position == NavDrawerItem.NAV_DRAWER_PROFILE_POSITION) return;
        int resourceId = navDrawerItem.resourceId;

        switch (resourceId) {
            case R.drawable.ic_timeline:


                openTimeLine();
//                openFragment(TimelineFragment.class, null);


                break;

            case R.drawable.ic_group:
//                addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, new JoinGroupFragment(), false
//                        , true);
                openFragment(JoinGroupFragment.class, null);


                break;
            case R.drawable.ic_my_group:
//                addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, new MyGroupFragment(), false
//                        , true);
                openFragment(MyGroupsListFragment.class, null);

//                ((HomeActivity) getActivity()).addFragmentToBackStack(getFragmentManager(), R.id.fragment_container, new MyGroupsListFragment(), false, false);

                break;

            case R.drawable.ic_message:
//                ActivityUtils.openActivity(HomeActivity.this, MessagesActivity.class, false);
                openMessage();

                break;

            case R.drawable.ic_schedule:
                ActivityUtils.openActivity(HomeActivity.this, MeetingActivity.class, false);

                break;

            case R.drawable.ic_setting:
                addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, new SettingFragment(), false
                        , true);
                break;
            case R.drawable.ic_logout:
                showConfirmDialog();
//                ATCPrefManager.setIsUserLoggedIn(HomeActivity.this, false);
//                ActivityUtils.openActivity(HomeActivity.this, LoginActivity.class, true);
                break;
            default:
                break;
        }
    }

    private void showConfirmDialog() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(HomeActivity.this);

        dialog.setContentView(R.layout.logout_confirm_dialog);

        // set values for custom dialog components - text, image and button
        Button logout = (Button) dialog.findViewById(R.id.logout);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);

        dialog.show();

        Button declineButton = (Button) dialog.findViewById(R.id.cancel);
        // if decline button is clicked, close the custom dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ATCPrefManager.setIsUserLoggedIn(HomeActivity.this, false);
                ActivityUtils.openActivity(HomeActivity.this, LoginActivity.class, true);
            }
        });

//        new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Log out")
//                .setMessage("Are you sure you want to Log out from your account?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ATCPrefManager.setIsUserLoggedIn(HomeActivity.this, false);
//                        ActivityUtils.openActivity(HomeActivity.this, LoginActivity.class, true);
//                    }
//
//                })
//                .setNegativeButton("No", null).show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null) mDrawerLayout.closeDrawers();
//


        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof GroupsTimeLineFragment &&
                Globals.CAME_FROM_NOTIFICATION_TO_GROUP) {
            finish();
        }

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof GroupsTimeLineFragment) {
            openFragment(MyGroupsListFragment.class, null);
        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof TimelineFragment) {
            finish();
        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof MyGroupsListFragment) {
            Log.e("BACK", "back");
            Globals.RETURN_POSITION = true;
            openTimeLine();
//            openFragment(TimelineFragment.class, null);


        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof EventFragment) {
            Bundle bundle = new Bundle();
            bundle.putString("RelativId", Globals.userId);
            Globals.RETURN_POSITION = true;
            openFragment(MemberProfileFragment.class, bundle);
        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof RelativeGroupsFragment) {
            Bundle bundle = new Bundle();
            bundle.putString("RelativId", Globals.userId);
            Globals.RETURN_POSITION = true;
            openFragment(MemberProfileFragment.class, bundle);
        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof GroupFragment) {
            Bundle bundle = new Bundle();
            bundle.putInt("selectedCategory", Globals.GROUP_ID);
            openFragment(GroupsTimeLineFragment.class, bundle);
        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof MemberProfileFragment) {
            Bundle bundle = new Bundle();
            bundle.putInt("string_key", Globals.GROUP_ID);
            openFragment(GroupFragment.class, bundle);
        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof RelativeEventFragment) {
            Bundle bundle = new Bundle();
            bundle.putInt("GroupId", Globals.groupId);
            bundle.putString("RelativId", Globals.relativeId);
            openFragment(MemberProfileFragment.class, bundle);
        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof RelativeGroupsFragment) {
            Bundle bundle = new Bundle();
            bundle.putInt("GroupId", Globals.groupId);
            bundle.putString("RelativId", Globals.relativeId);
            openFragment(MemberProfileFragment.class, bundle);
        } else {
            openTimeLine();
//            openFragment(TimelineFragment.class, null);
        }
//
    }


    void openTimeLine() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof TimelineFragment) {

        } else {
            Fragment fragment = findifFragmentExist(getSupportFragmentManager(), new TimelineFragment());
            if (fragment == null) {
                openFragment(TimelineFragment.class, null);

//            addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, new TimelineFragment(), false
//                    , true);
            } else {
                addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, fragment, false
                        , true);
            }
        }


    }


    void openMessage() {
        addFragmentToBackStack(getSupportFragmentManager(), R.id.fragment_container, new MessageFragment(), false
                , true);
    }


    private Fragment findifFragmentExist(FragmentManager fragmentManager, Fragment fragment) {
        String name = fragment.getClass().getSimpleName();
        if (fragmentManager.findFragmentByTag(name) != null) {
            Log.e("Fragment", "Fragment Exist before");
            return fragmentManager.findFragmentByTag(name);
        }
        return null;
    }

    public void addFragmentToBackStack(FragmentManager fragmentManager,
                                       int layout, Fragment fragment,
                                       boolean shouldAnimate, boolean shouldAddToBackStack) {
        mIsActivityFeedsVisible = false;
        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String name = fragment.getClass().getSimpleName();
        if (fragmentManager.popBackStackImmediate(name, 0)) return;

        if (fragmentManager.findFragmentByTag(name) != null) {
            Log.e("Fragment", "Fragment Exist before");
            fragment = (Fragment) fragmentManager.findFragmentByTag(name);
            fragmentTransaction.replace(layout, fragment, name);
        } else {
            Log.e("Fragment", "Fragment not exist before");
            fragmentTransaction.replace(layout, fragment, name);
        }

//        if (shouldAddToBackStack) {
//            fragmentTransaction.addToBackStack(name);
//            Log.e("class", "Add To Back Stack");
//        }

        fragmentTransaction.commit();

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
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);

    }

}
