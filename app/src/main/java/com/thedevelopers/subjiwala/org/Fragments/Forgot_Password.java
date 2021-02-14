package com.thedevelopers.subjiwala.org.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
import com.thedevelopers.subjiwala.org.Others.Reset_Dialog;
import com.thedevelopers.subjiwala.org.Others.Session;
import com.thedevelopers.subjiwala.org.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgot_Password extends Fragment {

    private View view;
    Button forgot_button;
    EditText forgot_mobile,forgot_ans;
    TextView forgot_ques,forgot_error;
    String sm,sa;
    private JSONObject jsonObject;
    private RequestQueue requestQueue;
    private CustomDialog progressDialog;
    String answer;
    private Handler handler;
    Toast toast;
    TextView text;ImageView image;
    ImageView back_button;
    TextInputLayout ans_layout;
    Reset_Dialog reset_dialog;
    String CUSTOMER_ID;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_forgot__password,container,false);

        forgot_button = (Button) view.findViewById(R.id.forgot_button);
        forgot_mobile = (EditText) view.findViewById(R.id.forgot_mobile);
        forgot_ques = (TextView) view.findViewById(R.id.forgot_ques);
        forgot_ans = (EditText) view.findViewById(R.id.forgot_ans);
        forgot_error = (TextView) view.findViewById(R.id.forgot_error);
        back_button = (ImageView) view.findViewById(R.id.back_button);
        ans_layout = (TextInputLayout) view.findViewById(R.id.ans_parent);

        progressDialog = new CustomDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);

        handler =new Handler();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).bacPOP();
            }
        });

        final View layout = inflater.inflate(R.layout.order_complete_toast,(ViewGroup)view.findViewById(R.id.toast_parent));

        toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        text = (TextView) layout.findViewById(R.id.toast_text);
        image = (ImageView) layout.findViewById(R.id.toast_image);
        text.setText("Password Changed Successfully!");
        image.setImageResource(R.drawable.order_complete);

        forgot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sm = forgot_mobile.getText().toString().trim();
                sa = forgot_ans.getText().toString().trim();

                if(sa.length()==0 && sm.length()==10) {
                    String uri = "http://subjiwala.org/android_forgot_pass.php?mobile=" + sm;

                    uri = uri.replaceAll(" ", "%20");

                    GetResult(uri);
                    forgot_error.setVisibility(View.INVISIBLE);
                }
                else if(sa.length()>0 && sa.equals(answer))
                {
                    reset_dialog = new Reset_Dialog(getActivity(),R.layout.pass_reset_dialog2,3,progressDialog,layout,CUSTOMER_ID);
                    reset_dialog.setCanceledOnTouchOutside(false);

                    reset_dialog.showAlertDialog(getLayoutInflater(),true);

                    Handlers();

                    forgot_error.setVisibility(View.INVISIBLE);

                }
                else if(sm.length()==0 || sm.length()<10)
                {
                    forgot_error.setText("Enter valid mobile no.");
                    forgot_error.setVisibility(View.VISIBLE);
                    forgot_error.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.jiggle_animation));
                }
                else
                {
                    forgot_error.setText("Unable to find your account!");
                    forgot_error.setVisibility(View.VISIBLE);
                    forgot_error.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.jiggle_animation));
                }


            }
        });




        return view;
    }

    private void Handlers() {
        handler.postDelayed(new Runnable() {
            public void run() {

                    ((MainActivity)getActivity()).bacPOP();

            }
        }, 1000);
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
                                                String uri = "http://subjiwala.org/android_forgot_pass.php?mobile=" + sm;

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
                    String question = jsonObject.getString("Security_Question");
                     answer = jsonObject.getString("Security_Answer");
                     CUSTOMER_ID = jsonObject.getString("Customer_ID");
                    forgot_error.setVisibility(View.GONE);

                    forgot_ques.setVisibility(View.VISIBLE);
                    forgot_ques.setText(question);
                    ans_layout.setVisibility(View.VISIBLE);

                    break;
                case 0:
                    forgot_error.setVisibility(View.VISIBLE);
                    forgot_ques.setVisibility(View.GONE);
                    ans_layout.setVisibility(View.GONE);
                    forgot_error.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.jiggle_animation));
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
