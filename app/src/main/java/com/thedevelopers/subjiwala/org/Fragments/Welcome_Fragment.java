package com.thedevelopers.subjiwala.org.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedevelopers.subjiwala.org.Activities.MainActivity;
import com.thedevelopers.subjiwala.org.R;

public class Welcome_Fragment extends Fragment {

    private View view;
    TextView login_button,signup_button,tag_line;
    ImageView logo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_welcome_,container,false);

        MainActivity.flag=1;

        login_button = (TextView) view.findViewById(R.id.wbutton_login);
        signup_button = (TextView) view.findViewById(R.id.wbutton_signup);
        logo = (ImageView) view.findViewById(R.id.logo);
        tag_line = (TextView) view.findViewById(R.id.tag_line);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replace_Fragment(new Login());
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replace_Fragment(new SignUp());
            }
        });

        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.welcome_fade);
        logo.setAnimation(animation1);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.welcome_fade_2);
        login_button.setAnimation(animation);
        signup_button.setAnimation(animation);
        tag_line.setAnimation(animation);

        return view;
    }



}
