package com.thedevelopers.subjiwala.org.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.style.IconMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.R;

public class About_Us extends Fragment {

    private View view;
    TextView t,visit_us;
    ImageView facebook,instagram,youtube,back_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_about__us,container,false);

        t = (TextView) view.findViewById(R.id.text_about);
        facebook = (ImageView) view.findViewById(R.id.facebook);
        instagram = (ImageView) view.findViewById(R.id.instagram);
        youtube = (ImageView) view.findViewById(R.id.youtube);
        visit_us = (TextView) view.findViewById(R.id.visit_us);
        back_button = (ImageView) view.findViewById(R.id.back_button);

        t.setText("Tired of visiting vegetable market bringing and bargaining?" +
                " Dealing with traffic, parking worries, heavy shopping bags," +
                " scorching heat and other unfavorable weather conditions" +
                " and getting an overview of vegetable lorry and get to" +
                " grab the required product from there? The alternate " +
                "is our search bar Subjiwala where you can search from all possible " +
                "type of vegetables. The online shopping of vegetable is a blessing " +
                "to get the required and desired choices over a few clicks. " +
                "Here at Subjiwala we provide our customers with easy return " +
                "and exchange facility with free delivery charge over the purchase of" +
                "  Rs. 50/- available online on our website and application. If you are" +
                " in a mood to mingle with friends but you got a vegetable list in your" +
                " pocket, just visit Subjiwala and order the vegetables to get it delivered " +
                "right at your doorstep while you get yourself entertained with friends." +
                " Want to cook your favorite veggie at lunch no need to worry to buy everything" +
                " is possible with your single click to prepare a mouthwatering dish. We will " +
                "provide you the products through express delivery within few hours. " +
                "You need to stay at your home, Just visit www.subjiwala.org " +
                " through your laptop, smartphone or even from phone application.");

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.facebook.com/subjiwala.org/?ref=br_rs");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard_Activity)getActivity()).bacPOP();
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.instagram.com/subjiwala06/");
                Intent intents = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intents);

            }
        });

        visit_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.thedevelopers.in");
                Intent intents = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intents);

            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://youtube.com");
                Intent intentss = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intentss);

            }
        });

        return view;
    }
}
