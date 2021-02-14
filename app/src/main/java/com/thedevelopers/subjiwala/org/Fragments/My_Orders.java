package com.thedevelopers.subjiwala.org.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.Activities.Shop;
import com.thedevelopers.subjiwala.org.Models.my_orders_model;
import com.thedevelopers.subjiwala.org.Others.Constant;
import com.thedevelopers.subjiwala.org.Others.CustomDialog;
import com.thedevelopers.subjiwala.org.Others.Session;
import com.thedevelopers.subjiwala.org.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class My_Orders extends Fragment {

    private View view;
    ImageView back_button;
    String CUSTOMER_ID;
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private CustomDialog progressDialog;
    ArrayList<my_orders_model> data = new ArrayList<>();
    Session session;
    HashMap<String, String> user;
    ListView order_list;
    LinearLayout no_items;
    Button go_shopping;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_my__orders,container,false);

        back_button = (ImageView) view.findViewById(R.id.back_button);
        order_list = (ListView) view.findViewById(R.id.order_list);
        no_items = (LinearLayout) view.findViewById(R.id.no_items);
        go_shopping = (Button) view.findViewById(R.id.go_shopping);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard_Activity)getActivity()).bacPOP();
            }
        });


        progressDialog = new CustomDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);

        session = new Session(getActivity());
        user = session.getUserDetails();

        CUSTOMER_ID = user.get(Session.USER_ID_KEY);

        String uri = "http://subjiwala.org/android_my_orders.php?customer_id="+CUSTOMER_ID;

        uri = uri.replaceAll(" ", "%20");
        GetResult(uri);

        return view;
    }

    public void ReadJson(JSONObject jsonObject) {
            data.clear();
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    int order_id = Integer.parseInt(object.getString("order_id"));
                    int product_id = Integer.parseInt(object.getString("product_id"));
                    String qty = object.getString("qty");
                    String order_date = object.getString("order_date");
                    String product_name = object.getString("product_name");
                    int product_price = Integer.parseInt(object.getString("product_price"));
                    String product_image = "http://subjiwala.org/inventory_images/" + product_id + ".jpg";

                    data.add(new my_orders_model(order_id,product_id,qty,product_price,product_name,order_date,product_image));
                }

                if(data.size()==0)
                {   no_items.setVisibility(View.VISIBLE);
                    go_shopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                startActivity(new Intent(getActivity(),Shop.class));
                        }
                    });
                }
                order_list.setAdapter(new Cart_Adapter(data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    private class Cart_Adapter extends BaseAdapter {
        ArrayList<my_orders_model> data_list;

        public Cart_Adapter(ArrayList<my_orders_model> data_list) {
            this.data_list = data_list;
        }

        @Override
        public int getCount() {
            return data_list.size();
        }

        @Override
        public Object getItem(int position) {
            return data_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null) {

                view = LayoutInflater.from(getActivity()).inflate(R.layout.order_item, parent, false);
            }

            TextView order_name = (TextView) view.findViewById(R.id.order_name);
            ImageView order_image = (ImageView) view.findViewById(R.id.order_image);
            TextView order_qty = (TextView) view.findViewById(R.id.order_quantity);
            TextView order_date = (TextView) view.findViewById(R.id.order_date);
            TextView order_price = (TextView) view.findViewById(R.id.order_price);
            TextView order_id = (TextView) view.findViewById(R.id.order_id);
            Double cqty = 0.0;

            my_orders_model current_item = data_list.get(position);

            if(current_item.getQty().length()==1 || current_item.getQty().length()==2)
                cqty = Double.parseDouble(current_item.getQty());
            else
            {
                String str[] =  current_item.getQty().split(" ");

                if(str[0].equals("50"))
                    cqty = 0.05;
                else if(str[0].equals("100"))
                    cqty = 0.1;
                else if(str[0].equals("250"))
                    cqty = 0.25;
                else if(str[0].equals("500"))
                    cqty = 0.5;
                else
                    cqty = Double.parseDouble(str[0]);
            }
            order_name.setText(current_item.getProduct_name());
            order_qty.setText("(x"+current_item.getQty()+")");
            order_date.setText("Order Date : "+current_item.getOrder_date());
            order_price.setText(getString(R.string.Rs) + " " + current_item.getProduct_price()*cqty+"/-");
            order_id.setText("Order ID : "+current_item.getOrder_id());

            Picasso.get().load(current_item.getImage()).placeholder(R.drawable.dummy_grocery).into(order_image);


            return view;
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
                                Log.e("TAG_DATA", response.toString());
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
                                                String uri = "http://subjiwala.org/android_cart_products.php?customer_id=" + user.get(Session.USER_ID_KEY);

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
