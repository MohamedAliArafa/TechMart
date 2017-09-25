package com.a700apps.techmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.a700apps.techmart.R;
import com.a700apps.techmart.utils.NavDrawerItem;

import java.util.ArrayList;

/**
 * Created by m.aboelazm on 12/6/2015.
 * Mohamed Abulazm
 */
public class NavDrawerAdapter extends StandardBaseListAdapter<NavDrawerItem> {
    public NavDrawerAdapter(ArrayList<NavDrawerItem> navDrawerItems, Context context) {
        super(navDrawerItems, context);
    }

    ViewHolder viewHolder;
    NavDrawerItem navDrawerItem;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_item_nav_drawer, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.tv_nav_drawer);
            viewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.iv_nav_drawer_item);

//            viewHolder.itemIcon.setColorFilter(ColorUtils.getInstance().getPrimaryColor());
//            viewHolder.itemTitle.setTextColor(ColorUtils.getInstance().getTextDarkPrimaryColor());
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        navDrawerItem = mList.get(position);
        viewHolder.itemTitle.setText(navDrawerItem.title);
        viewHolder.itemIcon.setImageResource(navDrawerItem.resourceId);
        return convertView;
    }

    public int getIndexForResourceId(int resId) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).resourceId == resId)
                return i;
        }
        return -1;
    }

//    public void removeSponsor() {
//        removeItemFromAdapter(getIndexForResourceId(R.drawable.ic_sponser));
//    }
//
//    public void removePlaceInfo() {
//        removeItemFromAdapter(getIndexForResourceId(R.drawable.ic_d_about_place));
//    }
//
//    public void removeSurvey() {
//        removeItemFromAdapter(getIndexForResourceId(R.drawable.ic_survey));
//    }
//
//    public void removeExhibitors() {
//        removeItemFromAdapter(getIndexForResourceId(R.drawable.ic_d_exhibitor));
//    }
//
//    public void removeLogout() {
//        removeItemFromAdapter(getIndexForResourceId(R.drawable.ic_d_logout));
//    }
//
//    public void removeDiscoverEvents() {
//        removeItemFromAdapter(getIndexForResourceId(R.drawable.ic_d_discover));
//    }


    public static class ViewHolder {
        TextView itemTitle;
        ImageView itemIcon;
    }
}
