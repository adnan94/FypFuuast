package com.example.ali.myapplication.Activities.Uc_Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ali.myapplication.R;

/**
 * Created by Sami Khan on 12/2/2017.
 */

public class About_Fragment extends android.support.v4.app.Fragment {

    public Button terms_and_cond_btn;
    public ImageView back_image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_frag_view,null);

        back_image = (ImageView)view.findViewById(R.id.back_image);
        terms_and_cond_btn = (Button)view.findViewById(R.id.terms_and_cond_btn);

        terms_and_cond_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
