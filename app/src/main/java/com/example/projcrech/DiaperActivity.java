package com.example.projcrech;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projcrech.database.database_helper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.time.LocalDate;
import java.time.LocalTime;

public class DiaperActivity extends AppCompatActivity {

    CheckBox boxPooToilet, boxPooDiaper, boxPee;
    Button confirmDiaper,backBtnDiaper,view;
    //RadioButton radioButton;
    database_helper db;



    BottomSheetBehavior bBehavior;
    private Drawable radioButtonDrawable;
    private LocalTime time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaper_dialog);

        boxPooToilet = findViewById(R.id.btn_poo);
        boxPooDiaper = findViewById(R.id.btn_poo_diaper);
        boxPee = findViewById(R.id.btn_pee);
        confirmDiaper = findViewById(R.id.btn_confirm_health);
        backBtnDiaper = findViewById(R.id.btn_back_health);
        //view = findViewById(R.id.buttonviewTest);

        time = LocalTime.now();

        //Intent intentFromA = getIntent();
        String nameChild = getIntent().getStringExtra("name");
        String statusSnack = getIntent().getStringExtra("buttontext");
        String statusLunch = getIntent().getStringExtra("buttonDrawable");
        String statusHealth = getIntent().getStringExtra("txtObs");
        String statusDiaper = getIntent().getStringExtra("boxChecked");
        Bundle bundleFromA = getIntent().getExtras();

        Bundle bundleToC = new Bundle(bundleFromA);

        db=new database_helper(this);
        //Add_test();
        //View_test();

        backBtnDiaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaperActivity.this, GridItemActivity.class);
                intent.putExtra("name", nameChild);
                //intent.putExtra("image", uri);
                intent.putExtra("buttontext", statusSnack);
                intent.putExtra("txtObs", statusHealth);
                //intent.putExtra("boxChecked", statusDiaper);
                startActivity(intent);
            }
        });


        confirmDiaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent (DiaperActivity.this, GridItemActivity.class);
                if(boxPee.isChecked() && !boxPooDiaper.isChecked() && !boxPooToilet.isChecked()){
                    showDialogHealth(nameChild,"Fez xixi");
                }

                if(boxPooToilet.isChecked() && !boxPooDiaper.isChecked() && !boxPee.isChecked()){
                    showDialogHealth(nameChild,"Fez cocó na sanita");
                }

                if(boxPooDiaper.isChecked() && !boxPooToilet.isChecked() && !boxPee.isChecked()){
                    showDialogHealth(nameChild,"Fez cocó na fralda");
                }

                if(boxPee.isChecked() && boxPooToilet.isChecked() && !boxPooDiaper.isChecked()){
                    showDialogHealth(nameChild,"Fez xixi e cocó na sanita");
                }

                if(boxPee.isChecked() && boxPooDiaper.isChecked() && !boxPooToilet.isChecked()){
                    /*i.putExtra("boxChecked", "Fez xixi e cocó na fralda");
                    i.putExtra("name", nameChild);
                    i.putExtra("image", bundleToC);
                    i.putExtra("buttontext", statusSnack);
                    i.putExtra("txtObs", statusHealth);
                    i.putExtra("buttonDrawable", statusLunch);
                    boolean isInserted = db.insertOccurrence("Muda de fralda","Fez xixi e cocó na fralda");*/
                    showDialogHealth(nameChild,"Fez xixi e cocó na fralda");
                    //i.putExtra("boxChecked", statusDiaper);
                    //startActivity(i);
                    //toastCheck.show();
                }

                if(boxPooToilet.isChecked() && boxPooDiaper.isChecked()){
                    NotificationWarningBoxPoo();
                }

                if(!boxPee.isChecked() && !boxPooToilet.isChecked() && !boxPooDiaper.isChecked()){
                    NotificationWarningDiaper();
                }
            }
        });
    }


    private void showDialogHealth(String nameChild, String txtDiaper){
        Dialog dialog = new Dialog(this,R.style.DialogStyle);
        dialog.setContentView(R.layout.custom_dialog_confirmation);
        String dateCurrent= CalendarUtils.formattedTimeOcc(time);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        TextView txtDesc = dialog.findViewById(R.id.txtDesc);
        TextView txtTitle = dialog.findViewById(R.id.txttite);
        LocalDate dt = (CalendarUtils.selectedDate);
        String test="test";
        int i=1;
        txtTitle.setText("Confirmação da muda de fralda\n");
        txtDesc.setText("Deseja confirmar a muda de fralda de "+nameChild+"?\n\n"+"Situação: "+txtDiaper+"\n");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DiaperActivity.this, GridItemActivity.class);
                boolean isInserted = db.insertOccurrence("Muda de fralda",txtDiaper,dateCurrent);
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
        txtMsg.setText("Adicionado a muda de fralda de " + nameChild);
        toastCheck.show();
    }

    private void NotificationWarningDiaper(){
        Toast toastWarn = new Toast(getApplicationContext());
        View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_warning, (ViewGroup) findViewById(R.id.customToastWarningLunch));
        toastWarn.setView(viewWarn);
        toastWarn.setDuration(Toast.LENGTH_SHORT);
        TextView warningTxt = viewWarn.findViewById((R.id.txtMessageWarningLunch));
        warningTxt.setText("Por favor, selecione uma opção no mínimo");
        toastWarn.show();
    }

    private void NotificationWarningBoxPoo(){
        Toast toastWarn = new Toast(getApplicationContext());
        View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_warning, (ViewGroup) findViewById(R.id.customToastWarningLunch));
        toastWarn.setView(viewWarn);
        toastWarn.setDuration(Toast.LENGTH_SHORT);
        TextView warningTxt = viewWarn.findViewById((R.id.txtMessageWarningLunch));
        warningTxt.setText("Situação impossivel");
        toastWarn.show();
    }

    @Override
    public void onStart(){
        super.onStart();
    }
}