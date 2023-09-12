package com.example.projcrech;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projcrech.database.database_helper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    EditText id,name,price,username,password;
    Button add,choose,listtest,btnSign;
    ImageView ver;
    RecyclerView recyclerView;
    ArrayList<String> nameOc,contentOc;
    //public static SQLiteHelper db;
    //MyAdapter adapter;
    private RecyclerView offerRecyclerView , bestSellerRecyclerView , clothingRecyclerView , bestSellerRecyclerView2;
    final int REQUEST_CODE_GALLERY = 999;
    public static database_helper db2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /*id=findViewById(R.id.id_text);
        name=findViewById(R.id.name_text);
        add=findViewById(R.id.addName);
        view=findViewById(R.id.button2);*/
        ver=findViewById(R.id.imageView);
        name=findViewById(R.id.edtName);
        choose=findViewById(R.id.btnChoose);
        listtest=findViewById(R.id.btnList);
        add=findViewById(R.id.btnAdd);
        price=findViewById(R.id.edtPrice);

        username=findViewById(R.id.createUsername);
        password=findViewById(R.id.createPassword);
        btnSign=findViewById(R.id.btnSign);

        //add=findViewById(R.id.addName);
        //db=new SQLiteHelper(this, "FoodDB1.sqlite", null, 1);
        //db.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB)");
        nameOc=new ArrayList<>();
        contentOc=new ArrayList<>();


        db2=new database_helper(this);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        TestActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY
                );
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    db2.insertdata(
                            name.getText().toString().trim(),
                            imageViewToByte(ver)
                    );
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    price.setText("");
                    ver.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        listtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, ChildList.class);
                startActivity(intent);
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    db2.insertUser(
                            username.getText().toString().trim(),
                            password.getText().toString().trim()
                    );
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    price.setText("");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ver.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }




    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }




    /*private void View_test() {
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=db.ViewOcc();
                if(res.getCount()==0){
                    //Toast.makeText(TestActivity.this, "Sem dados", Toast.LENGTH_SHORT).show();;
                    return;
                }
                else
                {
                    while(res.moveToNext()){
                        nameOc.add(res.getString(1));
                        contentOc.add(res.getString(2));
                    }
                }
            //}
        //});
    }

    private void Add_test(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                builder.setMessage("Deseja adicionar?").setCancelable(false).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        //byte[] data = getBitmapAsByteArray(img);


                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.child5);
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                        //byte[] img=byteArray.toByteArray();
                        //ImageView imageStore= MediaStore.Images.Media.getBitmap(getContentResolver(),bitmap);
                        boolean isInserted = db.storeImage(new Child(name.getText().toString(),bitmap));


                        if(isInserted=true){
                            Toast.makeText(TestActivity.this,"Data inserted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TestActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Confirm");
                alertDialog.show();
            }
        });

    }*/

    public void ShowMessage (String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
