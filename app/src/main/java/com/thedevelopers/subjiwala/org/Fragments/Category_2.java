package com.thedevelopers.subjiwala.org.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thedevelopers.subjiwala.org.Models.Accessory_item_model;
import com.thedevelopers.subjiwala.org.Others.Constant;
import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Activities.Shop;

import java.util.ArrayList;

public class Category_2 extends Fragment {

    private View view;
    private GridView gridView;
    ArrayList<Accessory_item_model> data;
    ImageView toolbar_back;
    Toolbar toolbar;
    RelativeLayout cart_float;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category_2,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        if(Constant.Category_flag == 0)
        {
            toolbar.setVisibility(View.VISIBLE);
            cart_float = (RelativeLayout) view.findViewById(R.id.cart_float);
            cart_float.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Dashboard_Activity)getActivity()).replace_Fragment(new My_Cart());
                }
            });
        }
        else
            toolbar.setVisibility(View.GONE);

//        gridView = (GridView) view.findViewById(R.id.category_2_data_list);

        toolbar_back = (ImageView) view.findViewById(R.id.toolbar_back);

        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard_Activity)getActivity()).bacPOP();
            }
        });

//        data = new ArrayList<>();
//        data.add(new Accessory_item_model());
//        data.add(new Accessory_item_model());
//        data.add(new Accessory_item_model());
//        data.add(new Accessory_item_model());
//        data.add(new Accessory_item_model());
//        data.add(new Accessory_item_model());
//
//        gridView.setAdapter(new CategoryData_Adpater(data,R.layout.accessories_list_item));
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                ((Shop)getActivity()).replace_Fragment(new Product());
//
//            }
//        });


        return view;
    }

    private class CategoryData_Adpater extends BaseAdapter {

        ArrayList<Accessory_item_model> data;
        int layout;

        public CategoryData_Adpater(ArrayList<Accessory_item_model> data, int layout) {
            this.data = data;
            this.layout = layout;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(layout, viewGroup, false);
            }

            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.null_tofade);
            animation.setDuration(500);
            view.setAnimation(animation);

            ImageView accessory_image;
            TextView accessory_name, accessory_price;

            accessory_image = (ImageView) view.findViewById(R.id.accessory_image);
            accessory_name = (TextView) view.findViewById(R.id.accessory_name);
            accessory_price = (TextView) view.findViewById(R.id.accessory_price);


            Accessory_item_model current_item = data.get(i);

//            if (current_item.getImages().length > 0)
//                Picasso.get().load(BaseClass.BaseUrl + current_item.getImages()[0]).placeholder(R.drawable.dummy_rectangle_image).into(accessory_image);
//
//            accessory_name.setText(current_item.getAccessory_name());
//            accessory_price.setText(getString(R.string.Rs) + " " + current_item.getPrice());

            return view;
        }
    }

    public void indicate_change(int i)
    {
        if(i==1)
        {
            gridView.setAdapter(new CategoryData_Adpater(data, R.layout.accessories_list_item2));
            gridView.setNumColumns(1);
        }
        else
        {
            gridView.setAdapter(new CategoryData_Adpater(data, R.layout.accessories_list_item));
            gridView.setNumColumns(2);
        }
    }
}
