package com.thedevelopers.subjiwala.org.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thedevelopers.subjiwala.org.Activities.Dashboard_Activity;
import com.thedevelopers.subjiwala.org.Others.CustomDialog;
import com.thedevelopers.subjiwala.org.Others.Reset_Dialog;
import com.thedevelopers.subjiwala.org.Others.Session;
import com.thedevelopers.subjiwala.org.R;

import java.util.HashMap;

public class My_Account extends Fragment {


    private View view;
    TextView account_name,account_email,account_phone,reset_pass,change_add,current_address,current_email,change_email,current_phone,change_phone;
    HashMap<String, String> user;
    Session session;
    private CustomDialog progressDialog;
    Reset_Dialog reset_dialog;
    Toast toast;
    LinearLayout log_out;
    ImageView back_button;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my__account,container,false);

        account_name = (TextView) view.findViewById(R.id.account_name);
        account_email = (TextView) view.findViewById(R.id.account_email);
        account_phone = (TextView) view.findViewById(R.id.account_phone);
        reset_pass = (TextView) view.findViewById(R.id.reset_pass);
        change_add = (TextView) view.findViewById(R.id.change_address);
        current_address = (TextView) view.findViewById(R.id.current_address);
        log_out = (LinearLayout) view.findViewById(R.id.logout);
        back_button = (ImageView) view.findViewById(R.id.back_button);
        current_email = (TextView) view.findViewById(R.id.current_email);
        change_email = (TextView) view.findViewById(R.id.change_email);
        current_phone = (TextView) view.findViewById(R.id.current_phone);
        change_phone = (TextView) view.findViewById(R.id.change_phone);

        progressDialog = new CustomDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard_Activity)getActivity()).bacPOP();
            }
        });


        session = new Session(getActivity());
        user = session.getUserDetails();

        account_name.setText(user.get(Session.NAME_KEY));
        account_email.setText(user.get(Session.EMAIL_KEY));
        account_phone.setText(user.get(Session.MOBILE_KEY));
        current_address.setText(user.get(Session.ADD_KEY));
        current_email.setText(user.get(Session.EMAIL_KEY));
        current_phone.setText(user.get(Session.MOBILE_KEY));

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });


        View layout2 = inflater.inflate(R.layout.order_complete_toast,(ViewGroup)view.findViewById(R.id.toast_parent));



        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View layout = inflater.inflate(R.layout.order_complete_toast,(ViewGroup)view.findViewById(R.id.toast_parent));


                reset_dialog = new Reset_Dialog(getActivity(),R.layout.pass_reset_dialog,1,progressDialog,layout,user.get(Session.USER_ID_KEY));
                reset_dialog.setCanceledOnTouchOutside(false);

                reset_dialog.showAlertDialog(getLayoutInflater(),true);

            }
        });


        change_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View layout = inflater.inflate(R.layout.order_complete_toast,(ViewGroup)view.findViewById(R.id.toast_parent));

                reset_dialog = new Reset_Dialog(getActivity(),R.layout.add_reset_dialog,2,progressDialog,layout,user.get(Session.USER_ID_KEY));
                reset_dialog.setCanceledOnTouchOutside(false);

                reset_dialog.showAlertDialog(getLayoutInflater(),true);
                current_address.setText(user.get(Session.ADD_KEY));
            }
        });

        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View layout = inflater.inflate(R.layout.order_complete_toast,(ViewGroup)view.findViewById(R.id.toast_parent));

                reset_dialog = new Reset_Dialog(getActivity(),R.layout.email_reset_dialog,4,progressDialog,layout,user.get(Session.USER_ID_KEY));
                reset_dialog.setCanceledOnTouchOutside(false);

                reset_dialog.showAlertDialog(getLayoutInflater(),true);
                current_email.setText(user.get(Session.EMAIL_KEY));
            }
        });

        change_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View layout = inflater.inflate(R.layout.order_complete_toast,(ViewGroup)view.findViewById(R.id.toast_parent));

                reset_dialog = new Reset_Dialog(getActivity(),R.layout.phone_reset_dialog,5,progressDialog,layout,user.get(Session.USER_ID_KEY));
                reset_dialog.setCanceledOnTouchOutside(false);

                reset_dialog.showAlertDialog(getLayoutInflater(),true);
                current_phone.setText(user.get(Session.MOBILE_KEY));

            }
        });



        return view;
    }
}
