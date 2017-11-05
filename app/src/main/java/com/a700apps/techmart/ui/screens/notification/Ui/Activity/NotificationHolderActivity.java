package com.a700apps.techmart.ui.screens.notification.Ui.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.NoficationData;
import com.a700apps.techmart.ui.screens.notification.Ui.Follow.ViewUserProfile;
import com.a700apps.techmart.ui.screens.notification.Ui.PostLikes.PostDetailsNotificationFragment;
import com.a700apps.techmart.ui.screens.profile.MemberProfile;
import com.a700apps.techmart.ui.screens.profile.MemberProfileFragment;

public class NotificationHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_holder);

        if (getIntent().getStringExtra("holder").equals("like")){
            Bundle bundle = new Bundle();
            bundle.putInt("itemid", getIntent().getIntExtra("itemid",0));
            bundle.putString("userid", getIntent().getStringExtra("userid"));
            bundle.putString("icon", getIntent().getStringExtra("icon"));
            bundle.putInt("type" , getIntent().getIntExtra("type" , 0));
            PostDetailsNotificationFragment fragment = new PostDetailsNotificationFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.holder , fragment).commit();
        }else if (getIntent().getStringExtra("holder").equals("follow")){
            Bundle bundle = new Bundle();
            bundle.putString("RelativId" , getIntent().getStringExtra("RelativId"));
            bundle.putString("GroupId" , getIntent().getStringExtra("GroupId" ));
            MemberProfileFragment fragment = new MemberProfileFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.holder , fragment).commit();
        }
    }
}
