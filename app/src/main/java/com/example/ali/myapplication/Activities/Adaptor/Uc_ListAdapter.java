package com.example.ali.myapplication.Activities.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ali.myapplication.Activities.ModelClasses.Polio_Team;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;
import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AST on 10/9/2017.
 */

public class Uc_ListAdapter extends BaseAdapter {

    public ArrayList<UC_Object> userModels;
    public Context mContext;
    public int mCurrentfilterLength;
    public ArrayList<UC_Object> backUplist;

    public Uc_ListAdapter(ArrayList<UC_Object> userModels, Context mContext) {
        this.userModels = userModels;
        this.mContext = mContext;
        this.backUplist = new ArrayList<>(userModels);
    }

    @Override
    public int getCount() {
        return userModels.size();
    }

    @Override
    public Object getItem(int i) {
        return userModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //    View custom_view = layoutInflater.inflate(R.layout.cust_list_adapter,null);


        ViewHolder viewHolder ;
        if(view == null){
            view =  layoutInflater.inflate(R.layout.uc_member_list,null);
            viewHolder = new ViewHolder();
            viewHolder.cust_adapter_image = (CircleImageView)view.findViewById(R.id.cust_adapter_image);
            viewHolder.cust_adapter_user_name = (TextView)view.findViewById(R.id.team_area);
            viewHolder.cust_adapter_user_app = (TextView)view.findViewById(R.id.team_name);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(mContext).load(userModels.get(i).getUc_member_piture()).asBitmap().into(viewHolder.cust_adapter_image);

        viewHolder.cust_adapter_user_name.setText(userModels.get(i).getUc_area());
        viewHolder.cust_adapter_user_app.setText(userModels.get(i).getUc_membern());

        return view;
    }

    public void add(UC_Object add_customer_object){
        backUplist.add(add_customer_object);
    }

    public void filterCustomer(String s) {
//        int filterLength = s.length();
//        s = s.toLowerCase();
//        if (filterLength == 0 || filterLength < mCurrentfilterLength) {
//            mCurrentfilterLength = filterLength;
//            userModels.clear();
//            userModels.addAll(backUplist);
//            if (filterLength == 0) {
//                notifyDataSetChanged();
//                return;
//            }
//        }
//        int i = 0;
//        while (i < userModels.size()) {
//            if (!userModels.get(i).getCust_name().toLowerCase().contains(s) || !userModels.get(i).getCust_name().toLowerCase().startsWith(String.valueOf(s.charAt(0))))
//            //  || !mValues.get(i).getName().toLowerCase().startsWith(String.valueOf(s.charAt(0)))) {
//            {
//                userModels.remove(i);
//            } else {
//                i++;
//            }
//
//        }
//        mCurrentfilterLength = filterLength;
//        notifyDataSetChanged();
    }


    private class ViewHolder{
        CircleImageView cust_adapter_image;
        TextView cust_adapter_user_name;
        TextView cust_adapter_user_app;

    }
}
