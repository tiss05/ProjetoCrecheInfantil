package com.example.projcrech;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projcrech.Adapter.ChildListAdapter;
import com.example.projcrech.Model.Child;
import com.example.projcrech.database.database_helper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MailBoxActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Child> list;
    ChildListAdapter adapter = null;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    public static database_helper db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);

        db2=new database_helper(this);
        //CalendarUtils.selectedDate = LocalDate.now();
        //setWeekView();

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new ChildListAdapter(this, R.layout.grid_item, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = db2.getData("SELECT * FROM CHILD");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new Child(name, image, id));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                //Intent intent = new Intent (Mai.this,GridItemActivity.class);


                //busca os dados dad criança selecionada
                String nameChild = list.get(i).getNameChild();
                Bundle addinfo = new Bundle();
                addinfo.putInt("IMAGE_RES", list.get(i).getIdChild());
                //intent.putExtras(addinfo);
                //intent.putExtra("name", nameChild);
                //startActivity(intent);

            }
        });








        // Initialize and Assign Variable
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNav.setSelectedItemId(R.id.mailbox);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(), WeekViewActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mailbox:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
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