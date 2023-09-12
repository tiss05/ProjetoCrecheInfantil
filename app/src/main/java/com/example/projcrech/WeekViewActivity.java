package com.example.projcrech;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projcrech.Adapter.ChildListAdapter;
import com.example.projcrech.Model.Child;
import com.example.projcrech.database.database_helper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.projcrech.CalendarUtils.daysInWeekArray;
import static com.example.projcrech.CalendarUtils.monthYearFromDate;
import static com.example.projcrech.database.database_helper.Column2;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{

    GridView gridView;
    ArrayList<Child> list;
    ChildListAdapter adapter = null;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    public static database_helper db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        db2=new database_helper(this);
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();

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
                Intent intent = new Intent (WeekViewActivity.this,GridItemActivity.class);


                //busca os dados dad criança selecionada
                String nameChild = list.get(i).getNameChild();
                Bundle addinfo = new Bundle();
                addinfo.putInt("IMAGE_RES", list.get(i).getIdChild());
                intent.putExtras(addinfo);
                intent.putExtra("name", nameChild);
                startActivity(intent);

            }
        });

        // Initialize and Assign Variable
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNav.setSelectedItemId(R.id.register);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.register:
                        return true;
                    case R.id.mailbox:
                        startActivity(new Intent(getApplicationContext(), MailBoxActivity.class));
                        overridePendingTransition(0, 0);
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



    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        gridView = findViewById(R.id.gridView);
        //eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        //setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
        //selectedDate.setBackgroundResource(R.color.blue);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //setEventAdpater();
    }

    /*private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }*/

    /*public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }*/

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
