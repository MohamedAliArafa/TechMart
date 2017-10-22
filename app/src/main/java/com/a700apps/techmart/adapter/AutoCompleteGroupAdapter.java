package com.a700apps.techmart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.a700apps.techmart.R;
import com.a700apps.techmart.data.model.UserGroup;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteGroupAdapter extends ArrayAdapter<UserGroup> {
    private final String MY_DEBUG_TAG = "UserGroupAdapter";
    private List<UserGroup> items;
    private List<UserGroup> itemsAll;
    private List<UserGroup> suggestions;
    private int viewResourceId;
    RecyclerView recyclerView;
    Context context;


    public AutoCompleteGroupAdapter(Context context, int viewResourceId, List<UserGroup> items , RecyclerView recyclerView) {
        super(context, viewResourceId, items);
        this.context = context;
        this.items = items;
        this.itemsAll = items;
        this.suggestions = new ArrayList<UserGroup>();
        this.viewResourceId = viewResourceId;
        this.recyclerView  =recyclerView;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        UserGroup userGroup = items.get(position);
        if (userGroup != null) {
            TextView UserGroupNameLabel = (TextView) v.findViewById(R.id.lbl_name);
            if (UserGroupNameLabel != null) {
              Log.i(MY_DEBUG_TAG, "getView UserGroup Name:"+userGroup.Name);
                UserGroupNameLabel.setText(userGroup.Name);
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((UserGroup)(resultValue)).Name;
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                Log.i(MY_DEBUG_TAG, "getView UserGrou111p Name:"+constraint);
                suggestions.clear();
                for (UserGroup userGroup : itemsAll) {
                    if(userGroup.Name.toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(userGroup);
                        Log.e(MY_DEBUG_TAG, "getView UserGroup Name:"+userGroup.Name);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
                filterResults.count = 0;
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<UserGroup> filteredList = (ArrayList<UserGroup>) results.values;
            if(results != null ) {//&& results.count > 0
//                clear();
//                for (UserGroup c : filteredList) {
//                    add(c);
//                }
                recyclerView.setAdapter(new GroupsAdapter(context, filteredList));
                notifyDataSetChanged();
            }
        }
    };
}


//
///**
// * Created by khaledbadawy on 9/12/2017.
// */
//
//public class AutoCompleteGroupAdapter extends ArrayAdapter<UserGroup> {
//    Context context;
//    int resource;
//    List<UserGroup> items, tempItems, suggestions;
//
//    public AutoCompleteGroupAdapter(Context context, int resource, List<UserGroup> items) {
//        super(context, resource);
//        this.context = context;
//        this.resource = resource;
//        this.items = items;
//        tempItems = new ArrayList<UserGroup>(items); // this makes the difference.
//        suggestions = new ArrayList<UserGroup>();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.custom_text_view, parent, false);
//        }
//        UserGroup people = items.get(position);
//        if (people != null) {
//            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
//            if (lblName != null)
//                lblName.setText(people.Name);
//        }
//        return view;
//    }
//
//    @Override
//    public Filter getFilter() {
//        return nameFilter;
//    }
//
//    /**
//     * Custom Filter implementation for custom suggestions we provide.
//     */
//    Filter nameFilter = new Filter() {
//        @Override
//        public CharSequence convertResultToString(Object resultValue) {
//            return ((UserGroup)resultValue).Name;
//        }
//
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            Log.e("Const=rain" , ""+constraint);
//            if (constraint != null) {
//                suggestions.clear();
//                for (UserGroup people : tempItems) {
//                    if (people.Name.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                        suggestions.add(people);
//                        Log.e("Const=rain" , ""+people.Name);
//                    }
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                return new FilterResults();
//            }
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            List<UserGroup> filterList = (ArrayList<UserGroup>) results.values;
//            if (results != null && results.count > 0) {
//                clear();
//                for (UserGroup people : filterList) {
//                    add(people);
//                    notifyDataSetChanged();
//                }
//            }
//        }
//    };
//}



//package com.a700apps.techmart.adapter;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Filter;
//import android.widget.TextView;
//
//import com.a700apps.techmart.R;
//import com.a700apps.techmart.data.model.UserGroup;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//
//public class AutoCompleteGroupAdapter extends ArrayAdapter<UserGroup> {
//    private final String MY_DEBUG_TAG = "UserGroupAdapter";
//    private List<UserGroup> items;
//    private List<UserGroup> itemsAll;
//    private List<UserGroup> suggestions;
//    private int viewResourceId;
//
//    public AutoCompleteGroupAdapter(Context context, int viewResourceId, List<UserGroup> items) {
//        super(context, viewResourceId, items);
//        this.items = items;
//        this.itemsAll =  items;
//        this.suggestions = new ArrayList<UserGroup>();
//        this.viewResourceId = viewResourceId;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View v = convertView;
//        if (v == null) {
//            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v = vi.inflate(viewResourceId, null);
//        }
//        UserGroup UserGroup = items.get(position);
//        if (UserGroup != null) {
//            TextView UserGroupNameLabel = (TextView) v.findViewById(R.id.lbl_name);
//            if (UserGroupNameLabel != null) {
//                Log.i(MY_DEBUG_TAG, "getView UserGroup Name:"+UserGroup.Name);
//                UserGroupNameLabel.setText(UserGroup.Name);
//            }
//        }
//        return v;
//    }
//
//    @Override
//    public Filter getFilter() {
//        return nameFilter;
//    }
//
//    Filter nameFilter = new Filter() {
//        @Override
//        public String convertResultToString(Object resultValue) {
//            String str = ((UserGroup) (resultValue)).Name;
//            return str;
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            if (constraint != null) {
//                suggestions.clear();
//                for (UserGroup userGroup : itemsAll) {
//                    if (userGroup.Name.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                        suggestions.add(userGroup);
//                        Log.e("Constaasd",""+constraint);
//                        Log.e("Constaasd",""+constraint);
//                    }
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                return new FilterResults();
//            }
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            ArrayList<UserGroup> filteredList = (ArrayList<UserGroup>) results.values;
//            if (results != null && results.count > 0) {
//                clear();
//                for (UserGroup c : filteredList) {
//                    add(c);
//                }
//                notifyDataSetChanged();
//            }
//        }
//    };
//}


//public class AutoCompleteGroupAdapter extends ArrayAdapter<UserGroup> {
//    Context context;
//    int resource;
//    List<UserGroup> items, tempItems, suggestions;
//
//    public AutoCompleteGroupAdapter(Context context, int resource, List<UserGroup> items) {
//        super(context, resource);
//        this.context = context;
//        this.resource = resource;
//        this.items = items;
//        tempItems = new ArrayList<UserGroup>(items); // this makes the difference.
//        suggestions = new ArrayList<UserGroup>();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.custom_text_view, parent, false);
//        }
//        UserGroup people = items.get(position);
//        if (people != null) {
//            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
//            if (lblName != null)
//                lblName.setText(people.Name);
//        }
//        return view;
//    }
//
//    @Override
//    public Filter getFilter() {
//        return nameFilter;
//    }
//
//    /**
//     * Custom Filter implementation for custom suggestions we provide.
//     */
//    Filter nameFilter = new Filter() {
//        @Override
//        public CharSequence convertResultToString(Object resultValue) {
//            return ((UserGroup)resultValue).Name;
//        }
//
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            if (constraint != null) {
//                suggestions.clear();
//                for (UserGroup people : tempItems) {
//                    if (people.Name.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                        suggestions.add(people);
//                    }
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                return new FilterResults();
//            }
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            List<UserGroup> filterList = (ArrayList<UserGroup>) results.values;
//            if (results != null && results.count > 0) {
//                clear();
//                for (UserGroup people : filterList) {
//                    add(people);
//                    notifyDataSetChanged();
//                }
//            }
//        }
//    };
//}

