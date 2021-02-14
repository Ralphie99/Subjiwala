package com.thedevelopers.subjiwala.org.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thedevelopers.subjiwala.org.Models.Accessory_item_model;
import com.thedevelopers.subjiwala.org.Others.CustomDialog;
import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Others.Session;
import com.thedevelopers.subjiwala.org.Activities.Shop;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Product extends Fragment {

    private View view;
    Accessory_item_model data;
    ImageView product_image;
    TextView product_name,product_price,product_desc,manufacturer;
    LinearLayout buy_now,add_to_cart;
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private CustomDialog progressDialog;
    HashMap<String, String> user;
    Session session;
    TextView qty;
    Toast toast;
    int button_decide=-1;

    public Product()
    {

    }

    @SuppressLint("ValidFragment")
    public Product(Accessory_item_model accessory_item_model) {
        this.data = accessory_item_model;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_product,container,false);

        progressDialog = new CustomDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);

        session = new Session(getActivity());
        user = session.getUserDetails();


        View layout = inflater.inflate(R.layout.red_city_toast,(ViewGroup)view.findViewById(R.id.toast_parent));
        ImageView toast_image = (ImageView) layout.findViewById(R.id.toast_image);
        TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
        toast_image.setImageResource(R.drawable.already_added);
        toast_text.setText("Product Already Added!");
        toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);



        product_image = (ImageView) view.findViewById(R.id.product_image);
        product_name = (TextView) view.findViewById(R.id.accessory_name);
        product_price = (TextView) view.findViewById(R.id.accessory_price);
        product_desc = (TextView) view.findViewById(R.id.accessory_desc);
        manufacturer = (TextView) view.findViewById(R.id.manufacturer);
        buy_now = (LinearLayout) view.findViewById(R.id.buy_now);
        add_to_cart = (LinearLayout) view.findViewById(R.id.add_to_cart);

        Picasso.get().load(data.getImage()).placeholder(R.drawable.dummy_grocery).into(product_image);
        product_name.setText(data.getAccessory_name());
        product_price.setText(getString(R.string.Rs) + " " + data.getPrice());
        product_desc.setText(data.getDescription());
        manufacturer.setText(data.getManufacturer());


        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_decide = 1;
                String uri = "http://subjiwala.org/android_my_cart.php?product_id="+data.getAccessory_id()+
                        "&customer_id="+user.get(Session.USER_ID_KEY)+
                        "&product_name="+data.getAccessory_name()+
                        "&details="+data.getDescription()+
                        "&product_price="+data.getPrice()+
                        "&qty=1"+
                        "&date="+data.getProduct_date()+
                        "&gm="+data.isIfgm();

                uri = uri.replaceAll(" ", "%20");
                GetResult(uri);
            }
        });

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_decide = 2;

                String uri = "http://subjiwala.org/android_my_cart.php?product_id="+data.getAccessory_id()+
                        "&customer_id="+user.get(Session.USER_ID_KEY)+
                        "&product_name="+data.getAccessory_name()+
                        "&details="+data.getDescription()+
                        "&product_price="+data.getPrice()+
                        "&qty=1"+
                        "&date="+data.getProduct_date()+
                        "&gm="+data.isIfgm();

                uri = uri.replaceAll(" ", "%20");
                GetResult(uri);
            }
        });

        return view;
    }

    public void ReadJson(  JSONObject jsonObject) {
        try {
            int response = jsonObject.getInt("Response");

            if(response == 2 && button_decide == 1)
            {
                toast.show();
            }
            else if(response == 2 && button_decide == 2)
            {
                //direct to my_cart
                ((Shop)getActivity()).replace_Fragment(new My_Cart());
            }
            else if(response == 1 && button_decide == 1)
            {
//                Shop.increase_count();
            }
            else  if(response == 1 && button_decide == 2)
            {
//                Shop.increase_count();
                //dierct to my_cart
                ((Shop)getActivity()).replace_Fragment(new My_Cart());
            }
            else
            {
                Toast.makeText(getActivity(),"Error adding product to cart!",Toast.LENGTH_SHORT).show();
            }
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

                                                String uri = "http://subjiwala.org/android_my_cart.php?product_id="+data.getAccessory_id()+
                                                        "&customer_id="+user.get(Session.USER_ID_KEY)+
                                                        "&product_name="+data.getAccessory_name()+
                                                        "&details="+data.getDescription()+
                                                        "&product_price="+data.getPrice()+
                                                        "&qty=1"+
                                                        "&date="+data.getProduct_date()+
                                                        "&gm="+data.isIfgm();

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
