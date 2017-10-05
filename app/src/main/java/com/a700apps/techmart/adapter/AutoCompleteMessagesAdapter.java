package com.a700apps.techmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.AllMessageList;
import com.a700apps.techmart.data.model.MyConnectionList;
import com.a700apps.techmart.data.model.MyConnectionList.ResultEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaledbadawy on 9/12/2017.
 */

public class AutoCompleteMessagesAdapter extends ArrayAdapter<MyConnectionList.ResultEntity> {
    Context context;
    int resource;
    List<MyConnectionList.ResultEntity> items, tempItems, suggestions;

    public AutoCompleteMessagesAdapter(Context context, int resource, List<MyConnectionList.ResultEntity> items) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<MyConnectionList.ResultEntity>(items); // this makes the difference.
        suggestions = new ArrayList<MyConnectionList.ResultEntity>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_text_view, parent, false);
        }
        MyConnectionList.ResultEntity people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((MyConnectionList.ResultEntity)resultValue).getName();
        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (MyConnectionList.ResultEntity people : tempItems) {
                    if (people.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<MyConnectionList.ResultEntity> filterList = (ArrayList<MyConnectionList.ResultEntity>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (MyConnectionList.ResultEntity people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
