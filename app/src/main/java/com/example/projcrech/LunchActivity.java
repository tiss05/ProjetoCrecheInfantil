package com.example.projcrech;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projcrech.database.database_helper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.time.LocalDate;
import java.time.LocalTime;

public class LunchActivity extends AppCompatActivity {

    TextView titleLunch;
    RadioButton btnGood, btnMedium, btnBad;
    Button confirmLunch,backBtnLunch;
    database_helper db;
    SQLiteDatabase sqLiteDatabase;
    //RadioButton radioButton;

    BottomSheetBehavior bBehavior;
    private Drawable radioButtonDrawable;
    private LocalTime time;
    public static LocalDate selectedDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_dialog);

        btnGood = findViewById(R.id.btn_satisfied);
        btnMedium = findViewById(R.id.btn_neutral);
        btnBad = findViewById(R.id.btn_bad);;
        //radioGroup = findViewById(R.id.radioGroupLunch);
        confirmLunch = findViewById(R.id.btn_confirm_health);
        backBtnLunch = findViewById(R.id.btn_back_health);

        titleLunch=findViewById(R.id.LunchTitleTxt);


        time = LocalTime.now();
        selectedDate = LocalDate.now();
        //Intent intentFromA = getIntent();
        String nameChild = getIntent().getStringExtra("name");
        String statusSnack = getIntent().getStringExtra("buttontext");
        String statusHealth = getIntent().getStringExtra("txtObs");
        String statusDiaper = getIntent().getStringExtra("boxChecked");


        Bundle bundleFromA = getIntent().getExtras();

        db=new database_helper(this);





        Bundle bundleToC = new Bundle(bundleFromA);

        backBtnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LunchActivity.this, GridItemActivity.class);
                intent.putExtra("name", nameChild);
                //intent.putExtra("IMAGE_RES", imageRes);
                intent.putExtra("buttontext", statusSnack);
                intent.putExtra("txtObs", statusHealth);
                intent.putExtra("boxChecked", statusDiaper);
                startActivity(intent);
            }
        });


        confirmLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (LunchActivity.this, GridItemActivity.class);
                /*ContentValues contentValues=new ContentValues();*/
                
                if(btnGood.isChecked()){

                    /*i.putExtra("buttonDrawable", "Comeu tudo");

                    i.putExtra("image", bundleToC);
                    i.putExtra("buttontext", statusSnack);
                    i.putExtra("txtObs", statusHealth);
                    i.putExtra("boxChecked", statusDiaper);*/
                    //contentValues.put("name_occurence",titleLunch.getText().toString());
                    //contentValues.put("content_occurence",btnGood.getText().toString());
                    //sqLiteDatabase=dbmain.getWritableDatabase();
                    //boolean isInserted = db.insertOccurrence("Almoço","Comeu tudo");
                    showDialogLunch(nameChild,"Comeu tudo");
                    //startActivity(i);
                    //toastCheck.show();
                }

                if(btnMedium.isChecked()){

                    /*i.putExtra("buttonDrawable", "Comeu pouco");
                    i.putExtra("name", nameChild);
                    i.putExtra("image", bundleToC);
                    i.putExtra("buttontext", statusSnack);
                    i.putExtra("txtObs", statusHealth);
                    i.putExtra("boxChecked", statusDiaper);*/
                    //contentValues.put("NAME_OCCURRENCE",btnMedium.getText().toString());
                    showDialogLunch(nameChild,"Comeu pouco");
                    //boolean isInserted = db.insertOccurrence("Almoço","Comeu pouco");
                    //startActivity(i);
                    //toastCheck.show();
                }

                if(btnBad.isChecked()){

                   /* i.putExtra("buttonDrawable", "Não comeu");
                    i.putExtra("name", nameChild);
                    i.putExtra("image", bundleToC);
                    i.putExtra("buttontext", statusSnack);
                    i.putExtra("txtObs", statusHealth);
                    i.putExtra("boxChecked", statusDiaper);*/
                    //contentValues.put("NAME_OCCURRENCE",btnBad.getText().toString());
                    //boolean isInserted = db.insertOccurrence("Almoço","Não comeu");
                    showDialogLunch(nameChild,"Não comeu");
                    //startActivity(i);
                    //toastCheck.show();
                }

                if(!btnGood.isChecked() && !btnMedium.isChecked() && !btnBad.isChecked()){
                    NotificationWarningLunch();
                }
            }
        });
    }


    public void onRadioButtonClicked(View view){

        Toast toastSelect = new Toast(LunchActivity.this);
        View viewSelect = getLayoutInflater().inflate(R.layout.custom_toast_select, (ViewGroup) findViewById(R.id.customToastSelectLunch));
        toastSelect.setView(viewSelect);
        toastSelect.setDuration(Toast.LENGTH_SHORT);
        TextView selectTxt = viewSelect.findViewById((R.id.txtMessageSelectLunch));

        boolean checked=((RadioButton)view).isChecked();
        switch(view.getId()) {
            case R.id.btn_satisfied:
                if(checked) {
                    selectTxt.setText("Opção Selecionada: Comeu Tudo");
                }
                break;

            case R.id.btn_neutral:
                if(checked) {
                    selectTxt.setText("Opção Selecionada: Comeu Pouco");
                }
                break;

            case R.id.btn_bad:
                if(checked) {
                    selectTxt.setText("Opção Selecionada: Não Comeu");
                }
                break;
        }
        toastSelect.show();
    }


    private void showDialogLunch(String nameChild, String txtLunch){
        //View mView=getLayoutInflater().inflate(R.layout.layout_custom_dialog,null);
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
        txtTitle.setText("Confirmação do almoço\n");
        txtDesc.setText("Deseja confirmar a avaliação do almoço de "+nameChild+"?\n\n"+"Avaliação: "+txtLunch+"\n");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LunchActivity.this, GridItemActivity.class);
                boolean isInserted = db.insertOccurrence("Almoço",txtLunch,dateCurrent);
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
        toastCheck.setDuration(Toast.LENGTH_SHORT);
        TextView txtMsg = v.findViewById((R.id.txtMessageCheckLunch));
        txtMsg.setText("Adicionado o almoço de " + nameChild);
        toastCheck.show();
    }

    private void NotificationWarningLunch(){
        Toast toastWarn = new Toast(getApplicationContext());
        View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_warning, (ViewGroup) findViewById(R.id.customToastWarningLunch));
        toastWarn.setView(viewWarn);
        toastWarn.setDuration(Toast.LENGTH_SHORT);
        TextView warningTxt = viewWarn.findViewById((R.id.txtMessageWarningLunch));
        warningTxt.setText("Por favor, selecione uma opção");
        toastWarn.show();
    }

    @Override
    public void onStart(){
        super.onStart();
    }
}
