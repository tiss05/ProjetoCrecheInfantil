package com.example.projcrech;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projcrech.Adapter.OccAdapter;
import com.example.projcrech.Model.Occurrence;
import com.example.projcrech.database.database_helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.DateTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GridItemActivity extends AppCompatActivity implements OccAdapter.MyClickListener {

    private TextView eventDateTV;
    private LocalTime time;
    private Button backBtn, editBtn;
    private ImageView photoChild;
    private ImageButton deleteBtn, modifyBtn;
    private View backgroundLayout;
    private TextView emptyViewLunch,emptyViewSnack,emptyViewDiaper,emptyViewHealth;

    Animation rotateOpen, rotateClose, fromBottom, toBottom;

    FloatingActionButton addOcurrence, lunch, diaper, snack, health;
    TextView addLunchText, addChangeDiaperText, addSnackText, addHealthText, nameChild;
    boolean aBoolean=false;

    RecyclerView recyclerViewLunch,recyclerViewSnack,recyclerViewDiaper,recyclerViewHealth;

    public String testData;
    public LocalDate date;
    public static database_helper db2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        db2=new database_helper(this);

        deleteBtn = findViewById(R.id.buttonDelete);
        modifyBtn = findViewById(R.id.buttonModify);

        initWidgets();
        initLunch();
        initSnack();
        initHealth();
        initDiaper();
        addAction();
        backAction();
        editAction();
        infoChild();
        infoLunch();
        infoHealth();
        infoSnack();
        infoDiaper();

        time = LocalTime.now();
        date = CalendarUtils.selectedDate;
        testData= CalendarUtils.formattedDate(date);
        eventDateTV.setText(testData);

    }

    private void initWidgets()
    {
        addOcurrence = findViewById(R.id.add_btn);
        lunch = findViewById(R.id.add_lunch);
        diaper = findViewById(R.id.add_change_diaper);
        snack = findViewById(R.id.add_snack);
        health = findViewById(R.id.add_health);

        addLunchText = findViewById(R.id.add_lunch_text);
        addChangeDiaperText = findViewById(R.id.add_change_diaper_text);
        addSnackText = findViewById(R.id.add_snack_text);
        addHealthText = findViewById(R.id.add_health_text);

        eventDateTV = findViewById(R.id.eventDateTV);
        backBtn = findViewById(R.id.backButton);
        editBtn = findViewById(R.id.editButton);


        photoChild = findViewById(R.id.imageChildTest);
        nameChild = findViewById(R.id.nameChild);


        backgroundLayout = findViewById(R.id.transparentBg);


        emptyViewLunch = (TextView) findViewById(R.id.empty_view_lunch);
        emptyViewSnack = (TextView) findViewById(R.id.empty_view_snack);
        emptyViewDiaper = (TextView) findViewById(R.id.empty_view_diaper);
        emptyViewHealth = (TextView) findViewById(R.id.empty_view_health);

        recyclerViewLunch=findViewById(R.id.lunchStatus);
        recyclerViewSnack=findViewById(R.id.snackStatus);
        recyclerViewDiaper=findViewById(R.id.diaperStatus);
        recyclerViewHealth=findViewById(R.id.healthStatus);


        db2=new database_helper(this);

        db2.queryData("CREATE TABLE IF NOT EXISTS Occurrence_table(ID_OCCURRENCE INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME_OCCURRENCE VARCHAR, CONTENT_OCCURRENCE VARCHAR, HOUR_OCCURRENCE VARCHAR, DATE_OCCURRENCE VARCHAR)");


        String test= CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        eventDateTV.setText(test);

    }



    private void infoChild() {
        String name = getIntent().getStringExtra("name");
        nameChild.setText(name);


        /*------------------------------------iniico bom acredito----------------------------------------*/

        /*Bundle bundle = this.getIntent().getExtras();
        int pic = bundle.getInt("IMAGE_RES");
        String query="SELECT * FROM CHILD WHERE ID_CHILD='"+pic+"'";
        Cursor cursor = db2.getData("SELECT PHOTO_CHILD FROM CHILD");
        cursor.moveToFirst();
        if (cursor.getCount()> 0) {
            if (cursor.moveToFirst()){

                byte[] x = cursor.getBlob(2);
                //byte[] xx = x;
                Bitmap bmp = BitmapFactory.decodeByteArray(x,0,x.length);
                photoChild.setImageBitmap(bmp);
            }

        }
        Toast.makeText(getApplicationContext(), "You Clicked at " + query , Toast.LENGTH_LONG).show();*/


        /*------------------------------------fim bom acredito----------------------------------------*/

        //photoChild.setImageResource(pic);
        /*Bundle extras = getIntent().getExtras();
        int imageRes = extras.getInt("IMAGE_RES");

        photoChild.setImageResource(imageRes);

        Toast.makeText(getApplicationContext(), "You Clicked at " + imageRes , Toast.LENGTH_SHORT).show();*/

        //ImageView image = (ImageView) findViewById(R.id.selectedimageview);
        //int imageId = getIntent().getIntExtra("IMAGE_RES",i);
        //photoChild.setImageResource(imageId);


        //ImageView mImage = (ImageView) findViewById(R.id.mImgView1);
        //photoChild.setImageResource(SyncStateContract.Constants.mThumbIds[index]);


       /* Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.your_image);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Bundle bundle = new Bundle();
        bundle.putByteArray("image", byteArray);*/



    }



    private void infoDiaper() {

        /*String diaperStatus = getIntent().getStringExtra("boxChecked");
        if(diaperStatus != null) {
            statusDiaper.setText(diaperStatus);
        }
        RelativeLayout testLayout = new RelativeLayout(this);
        ViewGroup parent;
        //relativeLayout1 = findViewById(R.id.customcontentdata);
        TextView textView= new TextView(this);

        Cursor res=db.ViewDiaper();
        if(res.getCount()==0 || res == null){
            String test="Sem nenhuma ocorrência";
            textView.setText(test);
           // relativeLayout1.addView(textView);



        }

        //for(int i=0,i,i++)
        while(res.moveToNext()){
            //txtObs.append("ID da ocorrência:"+res.getString(0)+"\n");
            View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_warning, (ViewGroup) findViewById(R.id.customToastWarningLunch));
            ViewGroup container;
            //LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);
            //View view = LayoutInflater.inflate(R.layout.custom_box_content, container, false);
            // Adjust the placement in the parent
            /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT , RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); // make sure to use the function which takes a boolean value for rules like CENTER_IN_PARENT
            statusLunch.setLayoutParams(params); // Add these parameters to the textview

            // Let the layout know about your newly created textview so that it can re-draw its canvas
            viewWarn.set(statusLunch);*/


            //RelativeLayout relativeLayout1 = new RelativeLayout(this);

            //TextView contentTxt = findViewById((R.id.txtContent));
            //TextView horasTxt = findViewById((R.id.txtSchedule));
            //relativeLayout.addView(statusDiaper);


            //text.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            //text.setText("normal");



            //View v = getLayoutInflater().inflate(R.layout.custom_box_content, (ViewGroup) findViewById(R.id.customBoxContent));
            //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);

            //View test=findViewById(R.id.customcontentdata);


            //contentTxt.setText(res.getString(1));
            //horasTxt.setText(res.getString(2));
            //relativeLayout1.setLayoutManager(contentTxt);
            //relativeLayout.addView(v,0);
            //v.setVisibility(View.VISIBLE);



            //statusDiaper.append("\n"+"Nome da ocorrência:"+res.getString(1)+"\n");
            //statusDiaper.append("Conteúdo:"+res.getString(2)+"\n\n");

            //Cursor res=db.ViewLunch();
        String hourStr = getIntent().getStringExtra("boxChecked");
        Cursor res=db2.ViewDiaper();
        if(res.getCount()==0){
            /*EmptyAdapter emptyAdapter = new EmptyAdapter();
            recyclerViewDiaper.setAdapter(emptyAdapter);*/
            emptyViewDiaper.setVisibility(View.VISIBLE);
            //Toast.makeText(GridItemActivity.this, "Sem dados", Toast.LENGTH_SHORT).show();;
            //recyclerView.set("Sem dados");
            //return;
        }
        else {
            emptyViewDiaper.setVisibility(View.GONE);
            //clothingRecyclerView = findViewById(R.id.lunchStatus);
            recyclerViewDiaper.setHasFixedSize(true);
            recyclerViewDiaper.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            /*LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewDiaper.setLayoutManager(llm);*/


            List<Occurrence> occurrenceList = new ArrayList<>();
            //ArrayList<Occurrence> dailyEvents = Occurrence.eventsForDate(CalendarUtils.selectedDate);
            while (res.moveToNext()) {
                LocalDate dailyEvents=CalendarUtils.selectedDate;
                occurrenceList.add(new Occurrence(res.getString(1), res.getString(2),
                        res.getString(3), dailyEvents, res.getInt(0)));

                OccAdapter occAdapter = new OccAdapter(this, occurrenceList,this);
                recyclerViewDiaper.setAdapter(occAdapter);
                occAdapter.notifyDataSetChanged();


            }


        }



    }


    private void infoHealth() {
        Cursor res=db2.ViewHealth();
        if(res.getCount()==0){
            emptyViewHealth.setVisibility(View.VISIBLE);
        }
        else {
            emptyViewHealth.setVisibility(View.GONE);

            recyclerViewHealth.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewHealth.setLayoutManager(llm);

            List<Occurrence> healthList = new ArrayList<>();

            while (res.moveToNext()) {
                LocalDate dailyEvents=CalendarUtils.selectedDate;
                healthList.add(new Occurrence(res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        dailyEvents,
                        res.getInt(0)));

                OccAdapter occAdapter = new OccAdapter(this, healthList, this);
                recyclerViewHealth.setAdapter(occAdapter);
                occAdapter.notifyDataSetChanged();

            }
        }


    }

    private void infoLunch() {
        Cursor res=db2.ViewLunch();

        if(res.getCount()==0){
            emptyViewLunch.setVisibility(View.VISIBLE);

        }
        else {
            emptyViewLunch.setVisibility(View.GONE);
            recyclerViewLunch.setHasFixedSize(true);


            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewLunch.setLayoutManager(llm);

            List<Occurrence> occurrenceList = new ArrayList<>();

            while (res.moveToNext()) {
                LocalDate dailyEvents=CalendarUtils.selectedDate;
                occurrenceList.add(new Occurrence(res.getString(1), res.getString(2),
                        res.getString(3),dailyEvents, res.getInt(0)));

                OccAdapter occAdapter = new OccAdapter(this, occurrenceList,this);
                recyclerViewLunch.setAdapter(occAdapter);
                occAdapter.notifyDataSetChanged();

            }
        }
    }

    private void infoSnack() {
        Cursor res=db2.ViewSnack();
        if(res.getCount()==0){
            emptyViewSnack.setVisibility(View.VISIBLE);

        }
        else
        {
            emptyViewSnack.setVisibility(View.GONE);
            recyclerViewSnack.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewSnack.setLayoutManager(llm);

            List<Occurrence> snackList = new ArrayList<>();

            while (res.moveToNext()) {
                LocalDate dailyEvents=CalendarUtils.selectedDate;
                snackList.add(new Occurrence(res.getString(1), res.getString(2),
                        res.getString(3),dailyEvents, res.getInt(0)));

                OccAdapter occAdapter = new OccAdapter(this, snackList,this);
                recyclerViewSnack.setAdapter(occAdapter);
                occAdapter.notifyDataSetChanged();


            }


        }

    }

    private void addAction() {
        addOcurrence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });
    }

    private void backAction() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GridItemActivity.this, WeekViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void editAction() {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFabEditBtn();
            }
        });
    }


    private void initSnack() {
        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db2.ViewSnack();
                if (res.getCount()==1) {
                    NotificationWarningLunchSnack();
                } else {
                    Intent intent = new Intent(GridItemActivity.this, SnackActivity.class);
                    String name = getIntent().getStringExtra("name");
                    String test= CalendarUtils.formattedDate(CalendarUtils.selectedDate);
                    intent.putExtra("name", name);
                    intent.putExtra("test", test);
                    //intent.putExtra("image", uri);
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null) {
                        int res_image = bundle.getInt("image");
                        photoChild.setImageResource(res_image);
                    }
                    startActivity(intent);
                }
            }
        });


    }

    private void initLunch() {

            lunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor res=db2.ViewLunch();
                    if(res.getCount()==1){
                        //emptyViewDiaper.setVisibility(View.VISIBLE);
                        NotificationWarningLunchSnack();
                        //recyclerView.set("Sem dados");
                        //return;
                    }
                    else {
                    Intent intent = new Intent(GridItemActivity.this, LunchActivity.class);
                    String name = getIntent().getStringExtra("name");
                    intent.putExtra("name", name);
                    //intent.putExtra("image", uri);
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null) {
                        int res_image = bundle.getInt("image");
                        photoChild.setImageResource(res_image);
                        }
                    startActivity(intent);
                    finish();
                    }
                }
            });

    }

    private void initHealth() {
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GridItemActivity.this, HealthActivity.class);
                String name = getIntent().getStringExtra("name");
                intent.putExtra("name", name);
                //intent.putExtra("image", uri);

                Bundle bundle = getIntent().getExtras();
                if (bundle!=null){
                    int res_image = bundle.getInt("image");
                    photoChild.setImageResource(res_image);
                }

                startActivity(intent);
                finish();
            }
        });
    }

    private void initDiaper() {
        diaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(GridItemActivity.this, DiaperActivity.class);
                String name = getIntent().getStringExtra("name");
                intent.putExtra("name", name);
                //intent.putExtra("image", uri);

                Bundle bundle = getIntent().getExtras();
                if (bundle!=null){
                    int res_image = bundle.getInt("image");
                    photoChild.setImageResource(res_image);
                }
                startActivity(intent);
                finish();
            }
        });
    }


    private void NotificationWarningEdit(){
        Toast toastEdit = new Toast(getApplicationContext());
        View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_write, (ViewGroup) findViewById(R.id.customToastWriteLunch));
        toastEdit.setView(viewWarn);
        toastEdit.setDuration(Toast.LENGTH_LONG);
        TextView warningTxt = viewWarn.findViewById((R.id.txtMessageWriteLunch));
        warningTxt.setText("Por favor, modifica ou elimina uma ocorrência da criança");
        toastEdit.show();
    }

    private void NotificationWarningLunchSnack(){
        Toast toastEdit = new Toast(getApplicationContext());
        View viewWarn = getLayoutInflater().inflate(R.layout.custom_toast_write, (ViewGroup) findViewById(R.id.customToastWriteLunch));
        toastEdit.setView(viewWarn);
        toastEdit.setDuration(Toast.LENGTH_LONG);
        TextView warningTxt = viewWarn.findViewById((R.id.txtMessageWriteLunch));
        warningTxt.setText("Já existe o registo da ocorrência");
        toastEdit.show();
    }


    private void animateFabEditBtn(){
        //animations
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        if (aBoolean){
            editBtn.startAnimation(rotateClose);
            deleteBtn.setVisibility(View.INVISIBLE);
            modifyBtn.setVisibility(View.INVISIBLE);
            //adapter.activateButtons(false);
            addOcurrence.show();

            aBoolean=false;
        } else {
            editBtn.startAnimation(rotateOpen);
            //deleteBtn.setVisibility(View.VISIBLE);
            //modifyBtn.setVisibility(View.VISIBLE);
            //adapter.activateButtons(true);
            NotificationWarningEdit();
            addOcurrence.hide();
            aBoolean=true;

        }
    }

    private void animateFab(){
        //animations
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        if (aBoolean){
            addOcurrence.startAnimation(rotateClose);
            lunch.startAnimation(toBottom);
            diaper.startAnimation(toBottom);
            snack.startAnimation(toBottom);
            health.startAnimation(toBottom);
            addLunchText.startAnimation(toBottom);
            addChangeDiaperText.startAnimation(toBottom);
            addSnackText.startAnimation(toBottom);
            addHealthText.startAnimation(toBottom);
            lunch.setClickable(false);
            diaper.setClickable(false);
            snack.setClickable(false);
            health.setClickable(false);
            addLunchText.setVisibility(View.GONE);
            addChangeDiaperText.setVisibility(View.GONE);
            addSnackText.setVisibility(View.GONE);
            addHealthText.setVisibility(View.GONE);
            aBoolean=false;
            backgroundLayout.setVisibility(View.INVISIBLE);
            backBtn.setEnabled(true);
            editBtn.setEnabled(true);

        } else {
            addOcurrence.startAnimation(rotateOpen);
            lunch.startAnimation(fromBottom);
            diaper.startAnimation(fromBottom);
            snack.startAnimation(fromBottom);
            health.startAnimation(fromBottom);
            addLunchText.startAnimation(fromBottom);
            addChangeDiaperText.startAnimation(fromBottom);
            addSnackText.startAnimation(fromBottom);
            addHealthText.startAnimation(fromBottom);
            lunch.setClickable(true);
            diaper.setClickable(true);
            snack.setClickable(true);
            health.setClickable(true);
            addLunchText.setVisibility(View.VISIBLE);
            addChangeDiaperText.setVisibility(View.VISIBLE);
            addSnackText.setVisibility(View.VISIBLE);
            addHealthText.setVisibility(View.VISIBLE);
            aBoolean=true;
            backgroundLayout.setVisibility(View.VISIBLE);
            backBtn.setEnabled(false);
            editBtn.setEnabled(false);
        }
    }


    @Override
    public void onEdit(int p) {
        int p1=p+1;
        Toast.makeText(this, "Item clicked: "+p1, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(int p) {
        int p2=p+1;
        Toast.makeText(this, "Item clicked: "+p2, Toast.LENGTH_SHORT).show();
    }

}
