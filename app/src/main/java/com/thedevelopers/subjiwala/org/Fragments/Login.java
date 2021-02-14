package com.thedevelopers.subjiwala.org.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thedevelopers.subjiwala.org.Others.Constant;
import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.Activities.MainActivity;
import com.thedevelopers.subjiwala.org.Others.CustomDialog;
import com.thedevelopers.subjiwala.org.R;
import com.thedevelopers.subjiwala.org.Others.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends Fragment {

    private View view;
    private RequestQueue requestQueue;
//    private ProgressBar progressBar;
    private EditText login_username,login_pass;
    private String username,pass;
    Button login_button;
    private JSONObject jsonObject;
    private CustomDialog progressDialog;
    TextView login_error,forgot_password,go_registration;
    ImageView back_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login,container,false);

        MainActivity.flag=0;

        login_pass = (EditText) view.findViewById(R.id.login_pass);
        login_username = (EditText) view.findViewById(R.id.login_username);
        login_button = (Button) view.findViewById(R.id.login_button);
        login_error = (TextView) view.findViewById(R.id.login_error);
        back_button = (ImageView) view.findViewById(R.id.back_button);
        forgot_password = (TextView) view.findViewById(R.id.forgot_password);
        go_registration = (TextView) view.findViewById(R.id.go_registration);

        progressDialog = new CustomDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);

         login_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 username = login_username.getText().toString();
                 pass = login_pass.getText().toString();
                 GetResult(Constant.getLogin_URL(username,pass));
             }
         });

         back_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ((MainActivity)getActivity()).bacPOP();
             }
         });

         forgot_password.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ((MainActivity)getActivity()).replace_Fragment(new Forgot_Password());
             }
         });

         go_registration.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ((MainActivity)getActivity()).replace_Fragment(new SignUp());
             }
         });

        return view;
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
                                                GetResult(Constant.getLogin_URL(username,pass));
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
                    login_error.setVisibility(View.GONE);
                    new Session(getActivity()).createLoginSession(jsonObject.getString("Customer_id"),
                                                                    jsonObject.getString("Customer_mobile"),
                                                                    jsonObject.getString("Customer_Name"),
                                                                    jsonObject.getString("Email"),
                                                                    jsonObject.getString("Address"),
                                                                    pass);
                    startActivity(new Intent(getActivity(),Dashboard_Activity.class));
                    ((MainActivity)getActivity()).finish();
                    break;
                case 0:
                    login_error.setVisibility(View.VISIBLE);
                    login_error.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.jiggle_animation));
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
