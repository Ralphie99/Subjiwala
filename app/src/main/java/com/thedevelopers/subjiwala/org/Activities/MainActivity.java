package com.thedevelopers.subjiwala.org.Activities;

/*
By Nikhil
 */

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.thedevelopers.subjiwala.org.Fragments.SpleshScreen;
import com.thedevelopers.subjiwala.org.Fragments.Welcome_Fragment;
import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Others.Session;

public class MainActivity extends AppCompatActivity {

    Session session;
    public static int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(this);

        if (session.isLoggedIn())
            replace_Fragment(new SpleshScreen());
        else
            replace_Fragment(new Welcome_Fragment());
    }

    public void replace_Fragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, null);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (flag == 1) {

            if (doubleBackToExitPressedOnce) {
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else
            super.onBackPressed();
    }

    public void bacPOP() {
        super.onBackPressed();
    }
}
