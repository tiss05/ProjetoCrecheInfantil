package com.example.projcrech;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.projcrech.database.database_helper;


public class LoginActivity extends AppCompatActivity {

    private EditText name, password;
    private String photoPath;
    private Button btnLogin;
    public static database_helper db2;
    boolean passwordVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db2=new database_helper(this);


        name = findViewById(R.id.name_input);
        password = findViewById(R.id.password_input);
        btnLogin = findViewById(R.id.buttonSign);


        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = password.getSelectionEnd();
                        if(passwordVisible){
                            //set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visibility_off,0);

                            //for hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        } else {
                            //set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visibility,0);

                            //for show password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        btnLogin.setOnClickListener((View v) -> {
            String user = name.getText().toString();
            String pass = password.getText().toString();


            if (name.equals("") || password.equals("")) {

                Toast.makeText(this, "Introduza as credenciais", Toast.LENGTH_SHORT).show();
            } else {
                Boolean checkusername = db2.checkusername(user, pass);
                if (checkusername == true) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    NotificationCheck();
                } else {
                    NotificationWarningDetailsUser();
                }
            }
        });




    }

    private void NotificationCheck(){
        Toast toastCheck = new Toast(getApplicationContext());
        View v = getLayoutInflater().inflate(R.layout.custom_toast_check, (ViewGroup) findViewById(R.id.customToastCheckLunch));
        toastCheck.setView(v);
        toastCheck.setDuration(Toast.LENGTH_LONG);
        TextView txtMsg = v.findViewById((R.id.txtMessageCheckLunch));
        txtMsg.setText("Bem-vindo à sua sessão!");
        toastCheck.show();
    }

    private void NotificationWarningDetailsUser(){
        Toast toastWrite = new Toast(getApplicationContext());
        View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_write, (ViewGroup) findViewById(R.id.customToastWriteLunch));
        toastWrite.setView(viewWarn);
        toastWrite.setDuration(Toast.LENGTH_SHORT);
        TextView warningTxt = viewWarn.findViewById((R.id.txtMessageWriteLunch));
        warningTxt.setText("Credenciais inválidas");
        toastWrite.show();
    }

    /**
     * Starter for this view, it gets the context and starts the new intent.
     * @param context Application context.
     */

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon_logo)
                .setTitle("Patronato de Santo António")
                .setMessage("Deseja sair da aplicação?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
