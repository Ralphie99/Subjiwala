package com.thedevelopers.subjiwala.org.Activities;

/*
By Nikhil
 */

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thedevelopers.subjiwala.org.Fragments.About_Us;
import com.thedevelopers.subjiwala.org.Fragments.My_Account;
import com.thedevelopers.subjiwala.org.Fragments.My_Orders;
import com.thedevelopers.subjiwala.org.Others.Constant;
import com.thedevelopers.subjiwala.org.Fragments.Category_1;
import com.thedevelopers.subjiwala.org.Fragments.Category_2;
import com.thedevelopers.subjiwala.org.Fragments.My_Cart;
import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Others.Session;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard_Activity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    LinearLayout shop,offers,my_cart,my_orders;
    ImageView hamburger;
    TextView user_email;
    Session session;
    HashMap<String, String> user;
    LinearLayout sign_out;
    ImageView nav_drop;
    LinearLayout nav_categories,nav_home,my_account,about_us;
    int count=0;
    TextView nav_vegetables,nav_food_grains;
    static int flag= 0;
    FragmentManager mFragmentManager;
    CircleImageView user_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        shop = (LinearLayout) findViewById(R.id.shop);
        my_orders = (LinearLayout) findViewById(R.id.my_orders);
        offers = (LinearLayout) findViewById(R.id.offers);
        my_cart = (LinearLayout) findViewById(R.id.my_cart);
        user_email = (TextView) findViewById(R.id.user_email);
        session = new Session(this);
        user = session.getUserDetails();
        sign_out = (LinearLayout) findViewById(R.id.sign_out);
        nav_drop = (ImageView) findViewById(R.id.nav_drop);
        nav_categories = (LinearLayout) findViewById(R.id.nav_categories);
        nav_vegetables = (TextView) findViewById(R.id.nav_vegetabless);
        nav_food_grains = (TextView) findViewById(R.id.nav_food_grains);
        nav_home = (LinearLayout) findViewById(R.id.nav_home);
        my_account = (LinearLayout) findViewById(R.id.my_account);
        user_photo = (CircleImageView) findViewById(R.id.user_photo);
        about_us = (LinearLayout) findViewById(R.id.about_us);

        mFragmentManager = getSupportFragmentManager();

        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                drawerLayout.closeDrawer(Gravity.START);
            }
        });


        nav_vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.Category_flag=0;
                flag++;
                replace_Fragment(new Category_1());
                drawerLayout.closeDrawer(Gravity.START);
            }
        });

//        nav_food_grains.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Constant.Category_flag=0;
//                flag++;
//                replace_Fragment(new Category_2());
//                drawerLayout.closeDrawer(Gravity.START);
//            }
//        });

        nav_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count%2==0)
                    nav_categories.setVisibility(View.GONE);
                else
                    nav_categories.setVisibility(View.VISIBLE);
            }
        });

        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag++;
                replace_Fragment(new About_Us());
                drawerLayout.closeDrawer(Gravity.START);
            }
        });




        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });

        user_email.setText(user.get(Session.EMAIL_KEY));

        hamburger = (ImageView) findViewById(R.id.hamburger);

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard_Activity.this,Shop.class));
            }
        });

        my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace_Fragment(new My_Orders());
            }
        });

        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag++;
                drawerLayout.closeDrawer(Gravity.START);
                replace_Fragment(new My_Account());
            }
        });

        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag++;
                drawerLayout.closeDrawer(Gravity.START);
                replace_Fragment(new My_Account());
            }
        });

        setSupportActionBar(toolbar);

        my_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.Cart_flag=0;
                flag++;
                replace_Fragment(new My_Cart());
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if(flag==0) {
            if (doubleBackToExitPressedOnce) {
                finish();
                moveTaskToBack(true);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
        else
            bacPOP();
    }

    public void replace_Fragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,null);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);

    }

    public void bacPOP() {
        --flag;
        super.onBackPressed();
    }
}
