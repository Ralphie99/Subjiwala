package com.thedevelopers.subjiwala.org.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thedevelopers.subjiwala.org.Models.Cart_Item_Model;
import com.thedevelopers.subjiwala.org.Others.Constant;
import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.Others.CustomDialog;
import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Others.Session;
import com.thedevelopers.subjiwala.org.Activities.Shop;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_Cart extends Fragment {

    private View view;
    ListView listView;
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private CustomDialog progressDialog;
    ArrayList<Cart_Item_Model> data = new ArrayList<>();
    HashMap<String, String> user;
    Session session;
    LinearLayout Order;
    ImageView back_button;
    int flag = 0;
    RelativeLayout cart_head;
    Toast toast;
    private Handler handler;
    LinearLayout no_items;
    Button go_shopping;
    static ArrayList<String> qty = new ArrayList<>();
    List<String> qty_list,qty_list2;
    String CUSTOMER_ID;
    int order_complete_flag=0,j;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my__cart, container, false);
        cart_head = (RelativeLayout) view.findViewById(R.id.cart_head);
        no_items = (LinearLayout) view.findViewById(R.id.no_items);
        go_shopping = (Button) view.findViewById(R.id.go_shopping);

        if(Constant.Category_flag == 0)
            cart_head.setVisibility(View.VISIBLE);
        else
            cart_head.setVisibility(View.GONE);

        handler =new Handler();

        listView = (ListView) view.findViewById(R.id.order_list);
        final_price = (TextView) view.findViewById(R.id.final_price);
        Order = (LinearLayout) view.findViewById(R.id.continue_to_payment);
        back_button = (ImageView) view.findViewById(R.id.back_button);

        progressDialog = new CustomDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);

        session = new Session(getActivity());
        user = session.getUserDetails();

        CUSTOMER_ID = user.get(Session.USER_ID_KEY);

        flag = 0;

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard_Activity) getActivity()).bacPOP();
            }
        });

        qty_list = new ArrayList<String>();
        qty_list.add("50 gm");
        qty_list.add("100 gm");
        qty_list.add("250 gm");
        qty_list.add("500 gm");
        qty_list.add("1 Kg");
        qty_list.add("2 Kg");
        qty_list.add("3 Kg");
        qty_list.add("4 Kg");
        qty_list.add("5 Kg");
        qty_list.add("6 Kg");
        qty_list.add("7 Kg");
        qty_list.add("8 Kg");
        qty_list.add("9 Kg");
        qty_list.add("10 Kg");

        qty_list2 = new ArrayList<String>();
        qty_list2.add("1 Pc");
        qty_list2.add("2 Pc");
        qty_list2.add("3 Pc");
        qty_list2.add("4 Pc");
        qty_list2.add("5 Pc");
        qty_list2.add("6 Pc");
        qty_list2.add("7 Pc");
        qty_list2.add("8 Pc");
        qty_list2.add("9 Pc");
        qty_list2.add("10 Pc");


        String uri = "http://subjiwala.org/android_cart_products.php?customer_id=" + user.get(Session.USER_ID_KEY);

        uri = uri.replaceAll(" ", "%20");

        GetResult(uri);

        View layout = inflater.inflate(R.layout.order_complete_toast,(ViewGroup)view.findViewById(R.id.toast_parent));

        toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        final Date dateObj = Calendar.getInstance().getTime();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        final String Date = sf.format(dateObj);


        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<data.size();i++)
                {
                    String cqty;
                    if(qty.get(i).equals("0.25"))
                        cqty = "250 gm";
                    else if(qty.get(i).equals("0.5"))
                        cqty = "500 gm";
                    else if(qty.get(i).equals("0.05"))
                        cqty = "50 gm";
                    else if(qty.get(i).equals("0.1"))
                        cqty = "100 gm";
                    else
                        cqty = qty.get(i);
                    String uri = "http://subjiwala.org/android_order_complete.php?customer_id="+CUSTOMER_ID+
                                                                                    "&product_id="+data.get(i).getProduct_id()+
                                                                                    "&qty="+cqty+
                                                                                    "&date="+Date+
                                                                                    "&product_price="+data.get(i).getProduct_price()+
                                                                                    "&product_name="+data.get(i).getProduct_name();

                    uri = uri.replaceAll(" ", "%20");

                    GetResult2(uri);

                }



            }
        });


        return view;
    }

    private void Handlers(int time, final int c) {
        handler.postDelayed(new Runnable() {
            public void run() {
                if(c==2)
                getActivity().finish();
                else
                    ((Dashboard_Activity)getActivity()).bacPOP();

            }
        }, time);
    }


    private class Cart_Adapter extends BaseAdapter {
        ArrayList<Cart_Item_Model> data_list;

        public Cart_Adapter(ArrayList<Cart_Item_Model> data_list) {
            this.data_list = data_list;

            for(int i=0;i<data.size();i++)
                qty.add(i,"1");
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null) {

                view = LayoutInflater.from(getActivity()).inflate(R.layout.my_cart_item, parent, false);
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,qty_list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,qty_list2);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            TextView product_name = (TextView) view.findViewById(R.id.cart_order_name);
            ImageView product_image = (ImageView) view.findViewById(R.id.cart_order_image);
            TextView product_price = (TextView) view.findViewById(R.id.cart_order_price);
            Spinner qty_selector = (Spinner) view.findViewById(R.id.qty_selector);
            ImageView remove = (ImageView) view.findViewById(R.id.cart_remove);


            final Cart_Item_Model current_item = data_list.get(position);
            Log.e("TAg", current_item.getImage());

            Picasso.get().load(current_item.getImage()).placeholder(R.drawable.dummy_grocery).into(product_image);

            product_name.setText(current_item.getProduct_name());
            product_price.setText(getString(R.string.Rs) + " " + current_item.getProduct_price()+"/Kg");

            if(current_item.getIfgm().equals("/kg"))
            {
                qty_selector.setAdapter(arrayAdapter);
                product_price.setText(getString(R.string.Rs) + " " + current_item.getProduct_price()+"/Kg");
            }
            else
            {
                qty_selector.setAdapter(arrayAdapter2);
                product_price.setText(getString(R.string.Rs) + " " + current_item.getProduct_price()+"/Pc");
            }


                qty_selector.setSelection(0);


            qty_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position_n, long id) {

                    if(current_item.getIfgm().equals("/kg"))
                    {
                        if(position_n == 0)
                            qty.set(position,"0.05");
                        else if(position_n == 1)
                            qty.set(position,"0.1");
                        else if(position_n == 2)
                            qty.set(position,"0.25");
                        else if(position_n == 3)
                            qty.set(position,"0.5");
                        else
                        {
                            String str[] =  qty_list.get(position_n).split(" ");
                            qty.set(position,str[0]);
                        }

                    }
                    else
                    {
                        String str[] =  qty_list2.get(position_n).split(" ");
                        qty.set(position,str[0]);
                    }

                    //Toast.makeText(getActivity(),"QTY="+data.get(position).getProduct_name()+" : "+qty.get(position),Toast.LENGTH_LONG).show();
                    setPrice();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyDataSetChanged();
                    flag = 1;
                    String uri = "http://subjiwala.org/android_remove_cart.php?customer_id=" + user.get(Session.USER_ID_KEY) +
                            "&product_id=" + current_item.getProduct_id();

                    uri = uri.replaceAll(" ", "%20");

                    GetResult(uri);
                    setPrice();
                }
            });

            return view;
        }
    }

    public void ReadJson(JSONObject jsonObject) {
        if (flag == 0) {
            data.clear();
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    int product_id = Integer.parseInt(object.getString("product_id"));
                    String product_name = object.getString("product_name");
                    int product_price = Integer.parseInt(object.getString("product_price"));
                    int quantity = Integer.parseInt(object.getString("qty"));
//                String product_desc = object.getString("product_details");
                    String image = "http://subjiwala.org/inventory_images/" + product_id + ".jpg";
                    String gm = object.getString("gm");

                    data.add(new Cart_Item_Model(image, product_name, product_price, quantity, product_id,gm));

                }
                Log.e("DATA_SIZE", String.valueOf(data.size()));
                if(data.size()==0)
                {   no_items.setVisibility(View.VISIBLE);
                    go_shopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(Constant.Category_flag == 0)
                                startActivity(new Intent(getActivity(),Shop.class));
                            else
                            {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                Log.e("FM_COUNT",String.valueOf(fm.getBackStackEntryCount()));
                                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                    fm.popBackStack();
                                }
                            }
                        }
                    });
                }
                listView.setAdapter(new Cart_Adapter(data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                int response = jsonObject.getInt("Response");

                if(data.size()==0)
                {   no_items.setVisibility(View.VISIBLE);
                    go_shopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(Constant.Category_flag == 0)
                                startActivity(new Intent(getActivity(),Shop.class));
                            else
                            {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                Log.e("FM_COUNT",String.valueOf(fm.getBackStackEntryCount()));
                                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                    fm.popBackStack();
                                }
                            }
                        }
                    });
                }

                    if(response == 0)
                        Toast.makeText(getActivity(),"Cannot remove product!",Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                                                GetResult("http://subjiwala.org/android_cart_products.php?customer_id=" + user.get(Session.USER_ID_KEY));
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

    public void ReadJson2(JSONObject jsonObject) {
            try {
                progressDialog.dismiss();
                j++;
                int Response = jsonObject.getInt("Response");
                if(Response==1)
                    order_complete_flag++;

                if(j==data.size())
                    if(order_complete_flag==data.size())
                    {
                        toast.show();j=0;order_complete_flag=0;
                        for(int i=0;i<data.size();i++)
                        {
                            String uri = "http://subjiwala.org/android_remove_cart.php?customer_id=" + user.get(Session.USER_ID_KEY) +
                                    "&product_id=" + data.get(i).getProduct_id();

                            uri = uri.replaceAll(" ", "%20");

                            GetResult(uri);
                        }
                        if(Constant.Category_flag == 0) {
                            Handlers(1000,1);
                        }
                        else
                            Handlers(1500,2);
                    }
                        else
                            Toast.makeText(getActivity(),"Cannot complete your order!",Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    public void GetResult2(String mURL) {
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        mURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("TAG_DATA", response.toString());

                                if (response.toString() != null) {
                                    try {
                                        jsonObject = new JSONObject(response.toString());
                                        ReadJson2(jsonObject);
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

    float net_price;
    TextView final_price;

    private void setPrice() {
        net_price = 0;

        for (int i = 0; i < data.size(); i++) {
            net_price += data.get(i).getProduct_price()*Float.parseFloat(qty.get(i));
        }
        final_price.setText(getString(R.string.Rs) + " " + net_price + " /-");
    }
}
