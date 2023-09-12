package com.example.projcrech;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projcrech.database.database_helper;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.LocalTime;

public class HealthActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView titleHealth, eventTimeTV;

    private LocalTime time;
    database_helper db;

    private TextInputEditText obsHealth;
    private Button confirmObsHealth, backBtnHealth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_dialog);
        db=new database_helper(this);

        time = LocalTime.now();

        obsHealth = findViewById(R.id.InputHealth);
        confirmObsHealth = findViewById(R.id.btn_confirm_health);
        backBtnHealth = findViewById(R.id.btn_back_health);


        String nameChild = getIntent().getStringExtra("name");

        backBtnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthActivity.this, GridItemActivity.class);
                intent.putExtra("name", nameChild);
                startActivity(intent);
            }
        });


        confirmObsHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = obsHealth.getText().toString();
                if(!text.isEmpty()) {
                    Intent i = new Intent(HealthActivity.this, GridItemActivity.class);
                    /*i.putExtra("txtObs", text);
                    i.putExtra("name", nameChild);
                    //i.putExtra("image", uri);
                    i.putExtra("buttonDrawable", statusLunch);
                    i.putExtra("buttontext", statusSnack);
                    i.putExtra("boxChecked", statusDiaper);*/
                    //boolean isInserted = db.insertOccurrence("Observação de sáude",text);
                    showDialogHealth(nameChild,text);
                    //startActivity(i);
                    //toastCheck.show();
                }

                if(text.isEmpty()) {
                    NotificationWarningHealth();
                }
            }
        });
    }


    private void showDialogHealth(String nameChild, String txtHeatlh){
        //View mView=getLayoutInflater().inflate(R.layout.layout_custom_dialog,null);
        Dialog dialog = new Dialog(this,R.style.DialogStyle);
        dialog.setContentView(R.layout.custom_dialog_confirmation);
        String dateCurrent= CalendarUtils.formattedTimeOcc(time);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        TextView txtDesc = dialog.findViewById(R.id.txtDesc);
        TextView txtTitle = dialog.findViewById(R.id.txttite);
        txtTitle.setText("Confirmação da observação de saúde\n");
        txtDesc.setText("Deseja confirmar a observação de saúde de "+nameChild+"?\n\n"+"Obs.: "+txtHeatlh+"\n");
        LocalDate dt = (CalendarUtils.selectedDate);
        String test="test";
        int i=1;
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (HealthActivity.this, GridItemActivity.class);
                boolean healthOccInserted = db.insertOccurrence("Obs_saude",txtHeatlh,dateCurrent);
                intent.putExtra("name", nameChild);
                startActivity(intent);
                NotificationCheck();
                finish();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window_update_delete);
        dialog.show();
    }

    private void NotificationCheck(){
        String nameChild = getIntent().getStringExtra("name");
        Toast toastCheck = new Toast(getApplicationContext());
        View v = getLayoutInflater().inflate(R.layout.custom_toast_check, (ViewGroup) findViewById(R.id.customToastCheckLunch));
        toastCheck.setView(v);
        toastCheck.setDuration(Toast.LENGTH_LONG);
        TextView txtMsg = v.findViewById((R.id.txtMessageCheckLunch));
        txtMsg.setText("Adicionado a observação de saúde de " + nameChild);
        toastCheck.show();
    }

    private void NotificationWarningHealth(){
        Toast toastWrite = new Toast(getApplicationContext());
        View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_write, (ViewGroup) findViewById(R.id.customToastWriteLunch));
        toastWrite.setView(viewWarn);
        toastWrite.setDuration(Toast.LENGTH_SHORT);
        TextView warningTxt = viewWarn.findViewById((R.id.txtMessageWriteLunch));
        warningTxt.setText("Por favor, escreva uma observação de saúde");
        toastWrite.show();
    }
}
