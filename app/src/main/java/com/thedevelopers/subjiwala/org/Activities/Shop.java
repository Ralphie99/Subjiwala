package com.thedevelopers.subjiwala.org.Activities;

/*
By Nikhil
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thedevelopers.subjiwala.org.Others.Constant;
import com.thedevelopers.subjiwala.org.Fragments.Category_1;
import com.thedevelopers.subjiwala.org.Fragments.Category_2;
import com.thedevelopers.subjiwala.org.Fragments.My_Cart;
import com.thedevelopers.subjiwala.org.R;

public class Shop extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout changeView;
    int count_clicks_view_1 = 0;
    int count_clicks_view_2 = 0;
    int count_clicks_sort = 0;
    ImageView view_icon;
    Category_1 category_1;
    Category_2 category_2;
    int pos;
//    static TextView cart_counter;
//    static int cart_count=0;
    ImageView toolbar_back,sort_image;
    RelativeLayout cart_float;
    LinearLayout sort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shop);

        Constant.Category_flag=1;
        Constant.Cart_flag=1;

        viewPager = (ViewPager) findViewById(R.id.shop_category);
        tabLayout = (TabLayout) findViewById(R.id.shop_tabs);
        changeView = (LinearLayout) findViewById(R.id.change_view);
        view_icon = (ImageView) findViewById(R.id.view_icon);
//        cart_counter = (TextView) findViewById(R.id.cart_counter);
        toolbar_back = (ImageView) findViewById(R.id.toolbar_back);
        cart_float = (RelativeLayout) findViewById(R.id.cart_float);
        sort = (LinearLayout) findViewById(R.id.sort);
        sort_image = (ImageView) findViewById(R.id.sort_image);

        cart_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace_Fragment(new My_Cart());
            }
        });

        category_1 = new Category_1();
        category_2 = new Category_2();

        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bacPOP();
            }
        });

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos == 0)
                {
                    count_clicks_view_1++;
                    if (count_clicks_view_1 % 2 == 1)
                    {
                        view_icon.setImageResource(R.drawable.grid);
                        category_1.indicate_change(1);
                    }
                    else
                    {
                        view_icon.setImageResource(R.drawable.list);
                        category_1.indicate_change(2);
                    }
                }
                else
                {
                    count_clicks_view_2++;
                    if (count_clicks_view_2 % 2 == 1)
                    {
                        view_icon.setImageResource(R.drawable.grid);
                        category_2.indicate_change(1);
                    }
                    else
                    {
                        view_icon.setImageResource(R.drawable.list);
                        category_2.indicate_change(2);
                    }
                }

            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_clicks_sort++;
                category_1.sort_Data(count_clicks_sort,count_clicks_view_1);
                if (count_clicks_sort % 2 == 1)
                {
                    sort_image.setImageResource(R.drawable.sort);
                }
                else
                    sort_image.setImageResource(R.drawable.unsort);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pos = position;
                if(pos==0)
                {
                    if(count_clicks_view_1%2 == 1 )
                        view_icon.setImageResource(R.drawable.grid);
                    else
                        view_icon.setImageResource(R.drawable.list);
                }
                else
                {
                    if(count_clicks_view_2%2 == 1 )
                        view_icon.setImageResource(R.drawable.grid);
                    else
                        view_icon.setImageResource(R.drawable.list);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private class PagerAdapter extends FragmentStatePagerAdapter
    {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    return category_1;

                case 1:
                    return category_2;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position)
            {
                case 0:
                    return "Vegetables";
                case 1:
                    return "Food Grains";

                default:
                    return "Error";
            }
        }
    }

    public void replace_Fragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,null);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);

    }

//    public static void increase_count()
//    {
//        cart_count++;
//        if(cart_count==1)
//        cart_counter.setVisibility(View.VISIBLE);
//        cart_counter.setText(cart_count);
//    }
//
//    public static void decrease_count()
//    {
//        cart_count--;
//        if(cart_count==0)
//        cart_counter.setVisibility(View.INVISIBLE);
//        cart_counter.setText(cart_count);
//    }

    public void bacPOP() {
        super.onBackPressed();
    }
}
