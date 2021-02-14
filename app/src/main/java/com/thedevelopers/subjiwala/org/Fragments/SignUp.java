package com.thedevelopers.subjiwala.org.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.thedevelopers.subjiwala.org.Others.CustomDialog;
import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Others.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends Fragment {


    private View view;
    EditText signup_city,signup_state,full_name,username,mobile,email,password,address,pincode,security,securityanswer;
    Button signup_button;
    private JSONObject jsonObject;
    private CustomDialog progressDialog;
    private RequestQueue requestQueue;
    String sname,susername,smobile,semail,spassword,sadd,spincode,sques,sans;
    Toast toast;
    ImageView toast_image;
    TextView toast_text;
    ImageView back_button;
    boolean all_clear = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_up,container,false);

        MainActivity.flag=0;

        progressDialog = new CustomDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);

        signup_city = (EditText) view.findViewById(R.id.signup_city);
        signup_state = (EditText) view.findViewById(R.id.signup_state);
        signup_button = (Button) view.findViewById(R.id.signup_submit);
        full_name = (EditText) view.findViewById(R.id.signup_name);
        username = (EditText) view.findViewById(R.id.signup_username);
        mobile = (EditText) view.findViewById(R.id.signup_phone);
        email = (EditText) view.findViewById(R.id.signup_email);
        password = (EditText) view.findViewById(R.id.signup_pass);
        address = (EditText) view.findViewById(R.id.signup_add);
        pincode = (EditText) view.findViewById(R.id.signup_pincode);
        back_button = (ImageView) view.findViewById(R.id.back_button);
        security = (EditText) view.findViewById(R.id.signup_ques);
        securityanswer = (EditText) view.findViewById(R.id.signup_ans);

        View layout = inflater.inflate(R.layout.red_city_toast,(ViewGroup)view.findViewById(R.id.toast_parent));
        toast_image = (ImageView) layout.findViewById(R.id.toast_image);
        toast_text = (TextView) layout.findViewById(R.id.toast_text);
        toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        signup_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast_image.setImageResource(R.drawable.flag);
                toast_text.setText("Only in JABALPUR. YET!");
                toast.show();
            }
        });

        signup_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast_image.setImageResource(R.drawable.flag);
                toast_text.setText("Only in JABALPUR. YET!");
                toast.show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bacPOP();
            }
        });


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = full_name.getText().toString();
                susername = username.getText().toString();
                smobile = mobile.getText().toString();
                semail = email.getText().toString();
                spassword = password.getText().toString();
                sadd = address.getText().toString();
                spincode = pincode.getText().toString();
                sques = security.getText().toString();
                sans = security.getText().toString();

                if(sname.length()==0) {
                    full_name.setError("Enter your name!");
                    all_clear = false;
                }
                else if(susername.length()==0) {
                    username.setError("Enter your username!");
                    all_clear = false;
                }
                else if(smobile.length()==0)
                {
                    mobile.setError("Enter your phone number!");
                    all_clear = false;
                }
                else if(semail.length()==0) {
                    email.setError("Enter your email!");
                    all_clear = false;
                }
                else if(spassword.length()==0) {
                    password.setError("Enter your password!");
                    all_clear = false;
                }
                else if(sadd.length()==0) {
                    address.setError("Enter your address!");
                    all_clear = false;
                }
                else if(sques.length()==0) {
                    security.setError("Enter your security question!");
                    all_clear = false;
                }
                else if(sans.length()==0) {
                    securityanswer.setError("Enter your security answer!");
                    all_clear = false;
                }
                else if(spincode.length()==0)
                {
                    pincode.setError("Enter your pincode!");
                    all_clear = false;
                }
                else
                {
                    all_clear = true;
                }

                String uri= "http://subjiwala.org/android_registration.php?name="+sname+
                        "&login="+susername+
                        "&password="+spassword+
                        "&mobile="+smobile+
                        "&email="+semail+
                        "&address="+sadd+
                        "&city="+"Jabalpur"+
                        "&state="+"Madhya Pradesh"+
                        "&pin="+spincode+
                        "&security="+sques+
                        "&securityanswer="+sans;
                uri = uri.replaceAll(" ", "%20");

                if(all_clear)
                GetResult(uri);
            }
        });


        return view;
    }

    public void GetResult(String mURL) {
        Log.e("URL",mURL);
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
                                                String uri= "http://subjiwala.org/android_registration.php?name="+sname+
                                                        "&login="+susername+
                                                        "&password="+spassword+
                                                        "&mobile="+smobile+
                                                        "&email="+semail+
                                                        "&address="+sadd+
                                                        "&city="+"Jabalpur"+
                                                        "&state="+"Madhya Pradesh"+
                                                        "&pin="+spincode+
                                                        "&security="+sques+
                                                        "&securityanswer="+sans;
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

    public void ReadJson(  JSONObject jsonObject) {
        try {
            int response = jsonObject.getInt("Response");
            switch(response)
            {
                case 1:
                    int id = jsonObject.getInt("Customer_id");
                    new Session(getActivity()).createLoginSession(String.valueOf(id),
                            smobile,
                            sname,
                            semail,
                            sadd,
                            spassword);
                    startActivity(new Intent(getActivity(),Dashboard_Activity.class));
                    ((MainActivity)getActivity()).finish();
                    break;
                case 2:
                    toast_image.setImageResource(R.drawable.account_exists);
                    toast_text.setText("Account already exits!");

                    toast.show();

                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
