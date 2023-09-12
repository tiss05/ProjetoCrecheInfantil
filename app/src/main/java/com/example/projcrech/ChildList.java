package com.example.projcrech;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.projcrech.Adapter.ChildListAdapter;
import com.example.projcrech.Model.Child;
import com.example.projcrech.database.database_helper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class ChildList extends AppCompatActivity {
    GridView gridView;
    ArrayList<Child> list;
    ChildListAdapter adapter = null;
    public static database_helper db2;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_list_activity);
        db2=new database_helper(this);

        gridView = (GridView) findViewById(R.id.gridViewChilds);
        list = new ArrayList<>();
        adapter = new ChildListAdapter(this, R.layout.grid_item, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = db2.getData("SELECT * FROM Occurrence_table");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new Child(name, image, id));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ChildList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = db2.getData("SELECT ID_OCCURRENCE,NAME_OCCURRENCE FROM Occurrence_table");
                            ArrayMap<Integer,String> occList = new ArrayMap<Integer,String>();
                            //CustomClass cust = new CustomClass(position);
                            //ArrayList<Integer> arrID = new ArrayList<Integer>();
                            //String test=c.getString(1);
                            ArrayList<String> arrID = new ArrayList<String>();
                            while (c.moveToNext()) {
                                //cust.setNameOcc(c.getString(1));
                                //cust.setCount(c.getInt(0));
                                //occList.put(c.getInt(0),c.getString(1));
                                //occList.add(new CustomClass(c.getInt(0)));
                                arrID.add(c.getString(1));
                                //arrID.add(c.getString(1));

                            }
                            //String name = parent.getItemAtPosition(position).toString();
                            //if(occList.get(position)){
                            // show dialog update at here
                            //String res = occList.get(position).getNameOcc();
                            //int count = occList.get(position).getCount();
                            //Log.i("test1",res);
                            //Log.i("test3", (name));
                            Log.i("test2", arrID.get(position));
                            //showDialogUpdate(ChildList.this, count, res);
                           showDialogUpdate(ChildList.this, arrID.get(position));

                        } else {
                            // delete
                            Cursor c = db2.getData("SELECT ID_OCCURRENCE FROM Occurrence_table");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            int idd=arrID.get(position);
                            Toast.makeText(getApplicationContext(), String.valueOf(idd), Toast.LENGTH_SHORT).show();
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    ImageView imageViewFood;

    private void showDialogUpdate(Activity activity, final String position) {

        int data = db2.GetId(position);
        //occList.put(c.getInt(0),c.getString(1));
        if(position.equals("Muda de fralda")) {
            Toast.makeText(this, "muda", Toast.LENGTH_SHORT).show();
        }

        if(position.equals("Obs_saude")) {
            Toast.makeText(this, "saude", Toast.LENGTH_SHORT).show();
        }

        if(position.equals("Almoço") || position.equals("Lanche")) {
            final Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.list_item);
            dialog.setTitle("Update");

            imageViewFood = (ImageView) dialog.findViewById(R.id.imageViewFood);
            final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
            final EditText edtPrice = (EditText) dialog.findViewById(R.id.edtPrice);
            //Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

            // set width for dialog
            int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
            // set height for dialog
            int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
            dialog.getWindow().setLayout(width, height);
            dialog.show();

            imageViewFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // request photo library
                    ActivityCompat.requestPermissions(
                            ChildList.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            888
                    );
                }
            });

            /*btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        db2.updateOcc(
                                edtName.getText().toString().trim(),
                                edtPrice.getText().toString().trim(),
                                //TestActivity.imageViewToByte(imageViewFood),
                                data
                        );
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Update successfully!!!", Toast.LENGTH_SHORT).show();
                    } catch (Exception error) {
                        Log.e("Update error", error.getMessage());
                    }
                    updateFoodList();
                }
            });*/
        }
    }

    private void showDialogDelete(final int idFood) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ChildList.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    db2.deleteOcc(idFood);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateFoodList() {
        // get all data from sqlite
        Cursor cursor = db2.getData("SELECT * FROM Occurrence_table");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(3);

            list.add(new Child(name, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
