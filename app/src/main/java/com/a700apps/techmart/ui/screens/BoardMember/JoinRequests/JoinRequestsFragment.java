package com.a700apps.techmart.ui.screens.BoardMember.JoinRequests;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.a700apps.techmart.R;
import com.a700apps.techmart.adapter.ApprovalAdapter;
import com.a700apps.techmart.data.model.JoinGroupRequestsData;
import com.a700apps.techmart.utils.Globals;
import com.a700apps.techmart.utils.PreferenceHelper;
import com.a700apps.techmart.utils.loadingDialog;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinRequestsFragment extends Fragment implements RequestsView{


    RecyclerView rv;
    RequestsPresenter presenter;
    Dialog dialogsLoading;
    public JoinRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_join_requests, container, false);
        presenter = new RequestsPresenter();
        presenter.attachView(this);


        rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        presenter.getGroupRequests(""+Globals.GROUP_ID, PreferenceHelper.getUserId(getActivity()));
        return view;
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
        rv.setAdapter(new ApprovalAdapter(getActivity() , list));
    }
}
