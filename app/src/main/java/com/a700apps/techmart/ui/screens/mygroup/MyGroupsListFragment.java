package com.a700apps.techmart.ui.screens.mygroup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.AutoCompleteGroupAdapter;
import com.a700apps.techmart.adapter.GroupsAdapter;
import com.a700apps.techmart.data.model.UserGroup;
import com.a700apps.techmart.data.model.UserGroupData;
import com.a700apps.techmart.ui.screens.grouptimeline.GroupsTimeLineFragment;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PaginationScrollListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroupsListFragment extends Fragment implements GroupView {
    View view;
    private MyGroupPresenter mPresenter;
    EmptyRecyclerView rv;
    EditText searchCompleteTextView;
    ImageView mProfileImageView, mNotificationImageView, mSideMenuImageView;
    public AVLoadingIndicatorView indicatorView;
    UserGroupData data;
    private List<UserGroup> suggestions = new ArrayList<>();
    TextView empty;
    GroupsAdapter adapter;


    // Index from which pagination should start (0 is 1st page in our case)
    int PageNumber = 1;
    private boolean isLoading = false;
    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    public MyGroupsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_groups_list, container, false);
        indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        mPresenter = new MyGroupPresenter();
        mPresenter.attachView(this);
        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        mSideMenuImageView = (ImageView) view.findViewById(R.id.imageView4);
        empty = (TextView) view.findViewById(R.id.empty);
        searchCompleteTextView = view.findViewById(R.id.edt_search);
//        searchCompleteTextView.setThreshold(2);

        mSideMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openDrawer();

            }
        });

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

        mPresenter.getMyGroup(getActivity(),PageNumber , Globals.PAGE_SIZE);
        rv = (EmptyRecyclerView) view.findViewById(R.id.recyclerView);


        return view;
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {
//
    }

    @Override
    public void updateUi(final UserGroupData data) {
        this.data = data;
        if (data.userGroup.size() == 0) {
            rv.setEmptyView(view.findViewById(R.id.tv_nodata));
        }
        adapter = new GroupsAdapter(getActivity(), data.userGroup);

        rv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);

        searchCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                suggestions.clear();
                for (int i = 0; i < data.userGroup.size(); i++) {
                    if (data.userGroup.get(i).Name.toLowerCase().startsWith(s.toString().toLowerCase())) {
                        suggestions.add(data.userGroup.get(i));
                    }
                }

                if (suggestions.size() >0){
                    empty.setVisibility(View.GONE);
                }else {
                    empty.setVisibility(View.VISIBLE);
                }

                rv.setAdapter(new GroupsAdapter(getActivity(), suggestions));
            }
        });

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLastPage){
                    isLoading = true;
                    PageNumber += 1; //Increment page index to load the next one
                    adapter.addLoadingFooter();
                    loadNextPage();
                }

            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    @Override
    public void updateUiMore(UserGroupData data) {
        adapter.removeLoadingFooter();
        isLoading = false;

        if (data.userGroup.size()==0){
            isLastPage=true;
        }
        adapter.addAll(data.userGroup);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateRelativeUi(UserGroupData data) {

    }

    private void loadNextPage(){
        mPresenter.getMyGroupMore(PageNumber , Globals.PAGE_SIZE);
    }

}
