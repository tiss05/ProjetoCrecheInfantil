package com.example.projcrech;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projcrech.database.database_helper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.time.LocalTime;

public class SnackActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton btnGood, btnMedium, btnBad;
    Button confirmLunch, backBtnLunch;
    database_helper db;
    //RadioButton radioButton;

    BottomSheetBehavior bBehavior;
    private Drawable radioButtonDrawable;
    private LocalTime time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_dialog);

        btnGood = findViewById(R.id.btn_satisfied);
        btnMedium = findViewById(R.id.btn_neutral);
        btnBad = findViewById(R.id.btn_bad);
        confirmLunch = findViewById(R.id.btn_confirm_health);
        backBtnLunch = findViewById(R.id.btn_back_health);

        Uri uri = getIntent().getParcelableExtra("image");

        //Intent intentFromA = getIntent();
        String nameChild = getIntent().getStringExtra("name");
        String statusSnack = getIntent().getStringExtra("buttontext");
        String statusLunch = getIntent().getStringExtra("buttonDrawable");
        String statusHealth = getIntent().getStringExtra("txtObs");
        String statusDiaper = getIntent().getStringExtra("boxChecked");
        String test = getIntent().getStringExtra("test");
        Toast.makeText(this, "Date: " + test, Toast.LENGTH_SHORT).show();
        Bundle bundleFromA = getIntent().getExtras();

        db = new database_helper(this);

        time = LocalTime.now();

        Bundle bundleToC = new Bundle(bundleFromA);

        backBtnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SnackActivity.this, GridItemActivity.class);
                intent.putExtra("name", nameChild);
                intent.putExtra("image", uri);
                intent.putExtra("buttonDrawable", statusLunch);
                intent.putExtra("txtObs", statusHealth);
                intent.putExtra("boxChecked", statusDiaper);
                startActivity(intent);
            }
        });


        confirmLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent (SnackActivity.this, GridItemActivity.class);

                if (btnGood.isChecked()) {
                    /*i.putExtra("buttontext", "Comeu tudo");
                    i.putExtra("name", nameChild);
                    i.putExtra("image", bundleToC);
                    i.putExtra("buttonDrawable", statusLunch);
                    i.putExtra("txtObs", statusHealth);
                    i.putExtra("boxChecked", statusDiaper);
                    //boolean isInserted = db.insertOccurrence("Lanche","Comeu tudo");*/
                    showDialogSnack(nameChild, "Comeu tudo");
                    //startActivity(i);
                    //toastCheck.show();
                }

                if (btnMedium.isChecked()) {
                    /*i.putExtra("buttontext", "Comeu pouco");
                    i.putExtra("name", nameChild);
                    i.putExtra("image", bundleToC);
                    i.putExtra("buttonDrawable", statusLunch);
                    i.putExtra("txtObs", statusHealth);
                    i.putExtra("boxChecked", statusDiaper);
                    //boolean isInserted = db.insertOccurrence("Lanche","Comeu pouco");*/
                    showDialogSnack(nameChild, "Comeu pouco");
                    //startActivity(i);
                    //toastCheck.show();
                }

                if (btnBad.isChecked()) {
                    /*i.putExtra("buttontext", "Não comeu");
                    i.putExtra("name", nameChild);
                    i.putExtra("image", bundleToC);
                    i.putExtra("buttonDrawable", statusLunch);
                    i.putExtra("txtObs", statusHealth);
                    i.putExtra("boxChecked", statusDiaper);
                    //boolean isInserted = db.insertOccurrence("Lanche","Não comeu");*/
                    showDialogSnack(nameChild, "Não comeu");
                    //startActivity(i);
                    //toastCheck.show();
                }

                if (!btnGood.isChecked() && !btnMedium.isChecked() && !btnBad.isChecked()) {
                    NotificationWarningSnack();
                }
            }
        });

    }


    public void onRadioButtonClickedd(View view) {

        Toast toastSelect = new Toast(getApplicationContext());
        View viewSelect = getLayoutInflater().inflate(R.layout.custom_toast_select, (ViewGroup) findViewById(R.id.customToastSelectLunch));
        toastSelect.setView(viewSelect);
        toastSelect.setDuration(Toast.LENGTH_SHORT);
        TextView selectTxt = viewSelect.findViewById((R.id.txtMessageSelectLunch));


        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.btn_satisfied:
                if (checked) {
                    selectTxt.setText("Opção Selecionada: Comeu Tudo");
                }
                break;

            case R.id.btn_neutral:
                if (checked) {
                    selectTxt.setText("Opção Selecionada: Comeu Pouco");
                }
                break;

            case R.id.btn_bad:
                if (checked) {
                    selectTxt.setText("Opção Selecionada: Não Comeu");
                }
                break;
        }
        toastSelect.show();
    }

    private void showDialogSnack(String nameChild, String txtSnack) {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.custom_dialog_confirmation);
        String dateCurrent = CalendarUtils.formattedTimeOcc(time);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        TextView txtDesc = dialog.findViewById(R.id.txtDesc);
        TextView txtTitle = dialog.findViewById(R.id.txttite);
        String dt = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        String test="test";
        int i=1;
        txtTitle.setText("Confirmação do lanche\n");
        txtDesc.setText("Deseja confirmar a avaliação do lanche de " + nameChild + "?\n\n" + "Avaliação: "
                + txtSnack+"\n");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SnackActivity.this, GridItemActivity.class);
                boolean isInserted = db.insertOccurrence("Lanche", txtSnack, dateCurrent);
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

    private void NotificationCheck() {
        String nameChild = getIntent().getStringExtra("name");
        Toast toastCheck = new Toast(getApplicationContext());
        View v = getLayoutInflater().inflate(R.layout.custom_toast_check, (ViewGroup) findViewById(R.id.customToastCheckLunch));
        toastCheck.setView(v);
        toastCheck.setDuration(Toast.LENGTH_LONG);
        TextView txtMsg = v.findViewById((R.id.txtMessageCheckLunch));
        txtMsg.setText("Adicionado o lanche de " + nameChild);
        toastCheck.show();
    }

    private void NotificationWarningSnack() {
        Toast toastWarn = new Toast(SnackActivity.this);
        View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_warning, (ViewGroup) findViewById(R.id.customToastWarningLunch));
        toastWarn.setView(viewWarn);
        toastWarn.setDuration(Toast.LENGTH_SHORT);
        TextView warningTxt = viewWarn.findViewById((R.id.txtMessageWarningLunch));
        warningTxt.setText("Por favor, selecione uma opção");
        toastWarn.show();
    }
}

