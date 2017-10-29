package com.a700apps.techmart.ui.screens.PredefinedMessage;

import com.a700apps.techmart.data.model.PredifinedData;

import java.util.List;

/**
 * Created by samir salah on 8/16/2017.
 */

public interface PredifinedView {

    void showLoadingProgress();
    void dismissLoadingProgress();
    void showErrorDialog(int error);
    void showToast(String msg);

    void updateUi(List<PredifinedData.Result> list);
    void back();
}
