package com.a700apps.techmart.ui.screens.BoardMember.JoinRequests;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.ApprovalAdapter;
import com.a700apps.techmart.data.model.JoinGroupRequestsData;
import com.a700apps.techmart.ui.screens.home.HomeActivity;
import com.a700apps.techmart.ui.screens.notification.NotificationActivity;
import com.a700apps.techmart.ui.screens.profile.EditProfileFragment;
import com.a700apps.techmart.utils.ActivityUtils;
import com.a700apps.techmart.utils.EmptyRecyclerView;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinRequestsFragment extends Fragment implements RequestsView {

    ImageView mProfileImageView, mNotificationImageView, mBackImageView;

    EmptyRecyclerView rv;
    RequestsPresenter presenter;
    Dialog dialogsLoading;
    public JoinRequestsFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_join_requests, container, false);
        presenter = new RequestsPresenter();
        presenter.attachView(this);

        mProfileImageView = (ImageView) view.findViewById(R.id.new_message);
        mNotificationImageView = (ImageView) view.findViewById(R.id.new_profile);
        mBackImageView = (ImageView) view.findViewById(R.id.imageView4);

        rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openDrawer();
            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openFragment(EditProfileFragment.class , null);
            }
        });


        mNotificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(getActivity(), NotificationActivity.class, false);
            }
        });
        presenter.getGroupRequests(""+Globals.GROUP_ID, PreferenceHelper.getUserId(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
    }

    @Override
    public void updateData(List<JoinGroupRequestsData.Result> list) {
            if (list.size() == 0) {
                rv.setEmptyView(view.findViewById(R.id.tv_nodata));
                view.findViewById(R.id.tv_nodata).setVisibility(View.VISIBLE);
            }
        rv.setAdapter(new ApprovalAdapter(getActivity() , list , rv));
    }


}
