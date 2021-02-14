package com.thedevelopers.subjiwala.org.Others;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.Activities.MainActivity;
import com.thedevelopers.subjiwala.org.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Reset_Dialog extends Dialog {


    private final Context context;
    private AlertDialog alertDialog;
    private PopupWindow popupWindow;
    int layout;
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private CustomDialog progressDialog;
    LayoutInflater inflater;
    View toast_layout;
    Toast toast;
    TextView text;
    ImageView image;
    int flag;
    HashMap<String, String> user;
    Session session;
    String password_new,address_new,email_new,phone_new;
    String CUSTOMER_ID;

    public Reset_Dialog(Context context, int layout, int flag, CustomDialog progressDialog, View toast_layout, String CUSTOMER_ID) {
        super(context);
        this.context=context;
        this.layout = layout;
        this.progressDialog = progressDialog;
        this.inflater = inflater;
        this.toast_layout = toast_layout;
        this.flag = flag;
        this.CUSTOMER_ID = CUSTOMER_ID;

        toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toast_layout);

        text = (TextView) toast_layout.findViewById(R.id.toast_text);
        image = (ImageView) toast_layout.findViewById(R.id.toast_image);

        session = new Session(context);
        user = session.getUserDetails();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
    }

    public void showAlertDialog(LayoutInflater inflater, boolean t) {
        if (t) {
            alertDialog = new AlertDialog.Builder(context).create();
            final View alertLayout = inflater.inflate(layout, null);
            alertDialog.setView(alertLayout);

            final Button cancel = (Button)alertLayout.findViewById(R.id.cancel);
            final Button confirm = (Button)alertLayout.findViewById(R.id.confirm);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uri,s_error;
                    if(flag == 1)
                    {
                        EditText password = (EditText) alertLayout.findViewById(R.id.reset_pass);
                        EditText confirm_password = (EditText) alertLayout.findViewById(R.id.confirm_pass);
                        EditText old_password = (EditText) alertLayout.findViewById(R.id.old_pass);
                        TextView pass_match = (TextView) alertLayout.findViewById(R.id.pass_match);

                        String spass = password.getText().toString().trim();
                        String scpass = confirm_password.getText().toString().trim();
                        String sold = old_password.getText().toString().trim();

                        if(sold.equals(user.get(Session.PASS_KEY)))
                            s_error = "Password doesn't match!";
                        else {
                            s_error = "Entered old password is wrong!";
                            pass_match.setText(s_error);
                            pass_match.setVisibility(View.VISIBLE);
                            pass_match.setAnimation(AnimationUtils.loadAnimation(context,R.anim.jiggle_animation));
                        }

                        pass_match.setText(s_error);



                        if(spass.equals(scpass) && sold.equals(user.get(Session.PASS_KEY)))
                        {
                            pass_match.setVisibility(View.INVISIBLE);
                            password_new = spass;
                            uri = "http://subjiwala.org/android_pass_update.php?customer_id="+CUSTOMER_ID+"&password="+spass;
                            uri = uri.replaceAll(" ", "%20");
                            GetResult(uri);
                        }
                        else
                        {
                            pass_match.setVisibility(View.VISIBLE);
                            pass_match.setAnimation(AnimationUtils.loadAnimation(context,R.anim.jiggle_animation));
                        }

                    }
                    else if(flag == 4)
                    {
                        EditText email = (EditText) alertLayout.findViewById(R.id.email);

                        String spass = email.getText().toString().trim();

                        email_new = spass;

                        uri = "http://subjiwala.org/android_email_update.php?customer_id="+user.get(Session.USER_ID_KEY)+"&email="+spass;
                        uri = uri.replaceAll(" ", "%20");
                        GetResult(uri);
                    }
                    else if(flag == 5)
                    {
                        EditText phone = (EditText) alertLayout.findViewById(R.id.phone);
                        TextView pass_match = (TextView) alertLayout.findViewById(R.id.pass_match);

                        String spass = phone.getText().toString().trim();

                        if(spass.length()==10)
                        {
                            pass_match.setVisibility(View.INVISIBLE);
                            password_new = spass;
                            uri = "http://subjiwala.org/android_phone_update.php?customer_id="+CUSTOMER_ID+"&phone="+spass;
                            uri = uri.replaceAll(" ", "%20");
                            GetResult(uri);
                        }
                        else
                        {
                            pass_match.setVisibility(View.VISIBLE);
                            pass_match.setAnimation(AnimationUtils.loadAnimation(context,R.anim.jiggle_animation));
                        }
                    }
                    else if(flag == 3)
                    {
                        EditText password = (EditText) alertLayout.findViewById(R.id.reset_pass);
                        EditText confirm_password = (EditText) alertLayout.findViewById(R.id.confirm_pass);
                        TextView pass_match = (TextView) alertLayout.findViewById(R.id.pass_match);

                        String spass = password.getText().toString().trim();
                        String scpass = confirm_password.getText().toString().trim();

                        s_error = "Password doesn't match!";

                        if(spass.equals(scpass))
                        {
                            pass_match.setVisibility(View.INVISIBLE);
                            password_new = spass;
                            uri = "http://subjiwala.org/android_pass_update.php?customer_id="+CUSTOMER_ID+"&password="+spass;
                            uri = uri.replaceAll(" ", "%20");
                            GetResult(uri);
                        }
                        else
                        {
                            pass_match.setText(s_error);
                            pass_match.setVisibility(View.VISIBLE);
                            pass_match.setAnimation(AnimationUtils.loadAnimation(context,R.anim.jiggle_animation));
                        }
                    }
                    else
                    {
                        EditText address_line1 = (EditText) alertLayout.findViewById(R.id.address_line_1);
                        EditText address_line2 = (EditText) alertLayout.findViewById(R.id.address_line_2);

                        String address = address_line1.getText().toString().trim()+" "+address_line2.getText().toString().trim();

                        address_new = address;

                        uri = "http://subjiwala.org/android_add_update.php?customer_id="+user.get(Session.USER_ID_KEY)+"&address="+address;
                        uri = uri.replaceAll(" ", "%20");
                        GetResult(uri);

                    }
                }
            });

            alertDialog.setView(alertLayout);
            alertDialog.show();
        } else {
            if (alertDialog.isShowing()) alertDialog.dismiss();
        }
    }
    public PopupWindow popupDisplay() {
        popupWindow = new PopupWindow(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.popup_msg_layout, null);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//        popupWindow.setContentView(view);
//        findViews(view);
        return popupWindow;
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage(error.toString())
                                        .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                GetResult("http://subjiwala.org/android_list_products.php");
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
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    public void ReadJson(  JSONObject jsonObject) {
        try {
            int response = jsonObject.getInt("Response");
            switch(response)
            {
                case 1:
                    image.setImageResource(R.drawable.order_complete);
                    if (flag==1) {
                        text.setText("Password successfully changed!");
                        session.reset_Pass(password_new);
                    }
                    else if(flag==4)
                    {
                        text.setText("Email successfully changed!");
                        session.reset_Email(email_new);
                    }
                    else if(flag==5)
                    {
                        text.setText("Phone Number successfully changed!");
                        session.reset_Phone(phone_new);
                    }
                    else if(flag==3)
                    {
                        text.setText("Password successfully changed!");
                        session.reset_Pass(password_new);
                        ((MainActivity)context).bacPOP();
                    }
                    else {
                        text.setText("Address successfully changed!");
                        session.reset_Add(address_new);
                    }

                        toast.show();
                        alertDialog.dismiss();
                    break;
                case 0:
                    image.setImageResource(R.drawable.cancel);
                    if(flag==1 || flag==3)
                        text.setText("Unable to change password!");
                    else if(flag == 4)
                        text.setText("Unable to change email!");
                    else if(flag == 5)
                        text.setText("Unable to change phone number!");
                    else
                        text.setText("Unable to change address!");

                        toast.show();
                        alertDialog.dismiss();
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
