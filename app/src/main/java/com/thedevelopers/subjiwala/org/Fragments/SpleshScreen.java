package com.thedevelopers.subjiwala.org.Fragments;

import android.os.Bundle;
import android.os.Handler;
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

import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Others.Session;

public class SpleshScreen extends Fragment {

    private View view;
    ImageView logo;
    TextView tag_line;
    private Handler handler;
    Session session;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_splesh_screen,container,false);

        handler =new Handler();
        session=new Session(getActivity());

        logo = (ImageView) view.findViewById(R.id.logo);
        tag_line = (TextView) view.findViewById(R.id.tag_line);

        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.welcome_fade);
        logo.setAnimation(animation1);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.welcome_fade_2);
        tag_line.setAnimation(animation);

        Handlers();

        return view;
    }

    private void Handlers() {
        handler.postDelayed(new Runnable() {
            public void run() {
                session.checkLogin();

            }
        }, 2000);
    }
}
