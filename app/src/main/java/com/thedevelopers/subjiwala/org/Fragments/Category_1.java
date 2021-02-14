package com.thedevelopers.subjiwala.org.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thedevelopers.subjiwala.org.Models.Accessory_item_model;
import com.thedevelopers.subjiwala.org.Others.Constant;
import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.Others.CustomDialog;
import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Activities.Shop;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Category_1 extends Fragment {

    private View view;
    private GridView gridView;
    ArrayList<Accessory_item_model> data,data_how;
    ArrayList<Accessory_item_model> data_price_sorted;
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private CustomDialog progressDialog;
    ImageView toolbar_back;
    Toolbar toolbar;
    RelativeLayout cart_float;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category_1,container,false);
        gridView = (GridView) view.findViewById(R.id.category_1_data_list);
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

        progressDialog = new CustomDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);

        toolbar_back = (ImageView) view.findViewById(R.id.toolbar_back);

        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard_Activity)getActivity()).bacPOP();
            }
        });

        data = new ArrayList<>();
        data_price_sorted = new ArrayList<>();

        gridView.setAdapter(new CategoryData_Adpater(data,R.layout.accessories_list_item));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((Shop)getActivity()).replace_Fragment(new Product(data_how.get(position)));

            }
        });

        String uri = "http://subjiwala.org/android_list_products.php";

        uri = uri.replaceAll(" ", "%20");

        GetResult(uri);

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

            Picasso.get().load(current_item.getImage()).placeholder(R.drawable.dummy_grocery).into(accessory_image);

            accessory_name.setText(current_item.getAccessory_name());
            accessory_price.setText(getString(R.string.Rs) + " " + current_item.getPrice());

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

    public void sort_Data(int i,int j)
    {
        if (i % 2 == 1 && j % 2 == 1) {
            Collections.sort(data_price_sorted);
            data_how = data_price_sorted;
            gridView.setAdapter(new CategoryData_Adpater(data_price_sorted, R.layout.accessories_list_item2));
        }
        else if (i % 2 == 0 && j % 2 == 1) {
            data_how = data;
            gridView.setAdapter(new CategoryData_Adpater(data, R.layout.accessories_list_item2));
        } else if (i % 2 == 1 && j % 2 == 0) {
            Collections.sort(data_price_sorted);
            data_how = data_price_sorted;
            gridView.setAdapter(new CategoryData_Adpater(data_price_sorted, R.layout.accessories_list_item));
        } else {
            data_how = data;
            gridView.setAdapter(new CategoryData_Adpater(data, R.layout.accessories_list_item));
        }

    }

    public void ReadJson(JSONObject jsonObject) {
        data.clear();
        try {
            JSONArray jsonArray =jsonObject.getJSONArray("response");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object= jsonArray.getJSONObject(i);

                int product_id = Integer.parseInt(object.getString("product_id"));
                String product_name = object.getString("product_name");
                int product_price = Integer.parseInt(object.getString("product_price"));
                String product_desc = object.getString("product_details");
                String manufacturer = object.getString("manufacturer");
                String image = "http://subjiwala.org/inventory_images/"+product_id+".jpg";
                String date = object.getString("product_date");
                String gm = object.getString("gm");

                data.add(new Accessory_item_model(image,product_id,product_name,product_price,product_desc,manufacturer,date,gm));
                data_price_sorted.add(new Accessory_item_model(image,product_id,product_name,product_price,product_desc,manufacturer,date,gm));
            }
            Log.e("DATA_SIZE",String.valueOf(data.size()));
            data_how = data;
            gridView.setAdapter(new CategoryData_Adpater(data,R.layout.accessories_list_item));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void GetResult(String mURL) {
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        mURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("TAG_DATA",response.toString());
                                progressDialog.dismiss();
                                if (response.toString() != null) {
                                    try {
                                        jsonObject = new JSONObject(response.toString());
                                        ReadJson(jsonObject);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage(error.toString())
                                        .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                String uri = "http://subjiwala.org/android_list_products.php";

                                                uri = uri.replaceAll(" ", "%20");

                                                GetResult(uri);
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        return super.getHeaders();
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        return params;
                    }
                };
        addToRequestQueue(jsonObjectRequest);
    }

    public void addToRequestQueue(Request request) {
        getRequestQueue().add(request);
    }
    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getActivity());
        return requestQueue;
    }

}
