package com.a700apps.techmart.utils;


import com.a700apps.techmart.R;

/**
 * Created by m.aboelazm on 12/6/2015.
 * Mohamed Abulazm
 */
public class NavDrawerItem {
    public static final int NAV_DRAWER_PROFILE_POSITION = 0;

    public static final int[] NAV_DRAWER_ICONS = {
            R.drawable.ic_timeline,
            R.drawable.ic_my_group,
            R.drawable.ic_group,
            R.drawable.ic_message,
            R.drawable.ic_schedule,
            R.drawable.ic_setting,
            R.drawable.ic_logout,
         };



    public String title;
    public int resourceId;

    public NavDrawerItem(String title, int resId) {
        this.title = title;
        this.resourceId = resId;
    }

    @Override
    public String toString() {
        return " " + title;
    }


}
