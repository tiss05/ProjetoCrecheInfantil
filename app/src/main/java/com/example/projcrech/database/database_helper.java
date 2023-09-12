package com.example.projcrech.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.projcrech.Model.Child;
import com.example.projcrech.WeekViewActivity;

import java.util.ArrayList;
import java.util.List;


public class database_helper extends SQLiteOpenHelper {
    Context context;
    private byte[] imageBytes;

    //creation of database
    public static final String Database_Name = "Ties.db";

    //creation of table
    public static final String Table_child = "CHILD";
    public static final String Table_occurrence = "Occurrence_table";
    public static final String Table_parents = "Table_parents";
    public static final String Table_user = "USER";
    public static final String Table_childhood_assistant = "Table_assistant";
    public static final String Table_room = "Table_room";


    //creating column in table
    public static final String Column1 = "ID_CHILD";
    public static final String Column2 = "NAME_CHILD";
    public static final String Column3 = "PHOTO_CHILD";
    public static final String idColumn = "ID_OCCURRENCE";
    public static final String nameColumn = "NAME_OCCURRENCE";
    public static final String contentColumn = "CONTENT_OCCURRENCE";
    public static final String timeColumn = "HOUR_OCCURRENCE";
    public static final String dateColumn = "DATE_OCCURRENCE";
    public static final String idChildColumn = "ID_CHILD_REF";
    public static final String userColumn = "USERNAME";
    public static final String passwordColumn = "PASSWORD";

    public database_helper(@Nullable Context context) {
        super(context, Database_Name, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableChild = "CREATE TABLE " + Table_child + "(" +
                "ID_CHILD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME_CHILD VARCHAR(50)," +
                "PHOTO_CHILD BLOB)";

        String tableOccurrence = "CREATE TABLE " + Table_occurrence + "(" +
                "ID_OCCURRENCE INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME_OCCURRENCE VARCHAR(50), " +
                "CONTENT_OCCURRENCE VARCHAR(50), " +
                "HOUR_OCCURRENCE VARCHAR(50), " +

                //"\" + /)";

                //", " +
                "ID_CHILD_REF INTEGER, " +
                "FOREIGN KEY (ID_CHILD_REF) REFERENCES " +
                Table_child +
                "(" + Column1 + ")" +
                ")";

                /*+
                "DATE_OCCURRENCE VARCHAR(50))";
                /*

                //")";
                /*"ID_CHILD_REF INTEGER, " +
                "FOREIGN KEY ('ID_CHILD_REF') REFERENCES " + Table_child + "('ID_CHILD')"+ ")";
                 FOREIGN KEY(trackartist) REFERENCES artist(artistid)
                "DATE_OCCURRENCE VARCHAR(50))";
        ," +"ID_CHILD_REF VARCHAR(50))";
                "ID_CHILD INTEGER FOREIGNER KEY," +
                "ID_AUX_INF INTEGER FOREIGN KEY)";

                FOREIGN KEY (TOPIC_ID) REFERENCES " +TABLE_NAME_TOPICS + " (_ID)," +
"FOREIGN KEY (USER_ID) REFERENCES " +TABLE_NAME_USERS + " (_ID))");
                */

        String tableUser = "CREATE TABLE " + Table_user + "(" +
                "ID_USER INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT," +
                "PASSWORD TEXT)";

        db.execSQL(tableUser);
        db.execSQL(tableChild);
        db.execSQL(tableOccurrence);
        //db.execSQL("PRAGMA foreign_keys=ON");
        //db.setForeignKeyConstraintsEnabled(true);

    }

    @Override
    public void onOpen(SQLiteDatabase database) {
        database.setForeignKeyConstraintsEnabled(true);
        super.onOpen(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_child);
        db.execSQL("DROP TABLE IF EXISTS " + Table_occurrence);
        db.execSQL("DROP TABLE IF EXISTS " + Table_user);
        db.execSQL("PRAGMA foreign_keys=ON");
        db.setForeignKeyConstraintsEnabled(true);
        onCreate(db);
    }




    //----------------------  INSERT -------------------------


    public int GetId(String currentNote) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor getNoteId = myDB.rawQuery("select ID_OCCURRENCE from Occurrence_table where NAME_OCCURRENCE LIKE '"+currentNote+"'",null);
        return getNoteId.getColumnIndex("ID_OCCURRENCE");
    }



    //ADD OCCURRENCE
    public boolean insertOccurrence(String nameCol, String contentCol, String scheduleCol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //long id = db.insert(Table_occurrence, null , contentValues);
        String strDate = "DATA";
        //contentValues.put(idColumn,id);
        contentValues.put(nameColumn, nameCol);
        contentValues.put(contentColumn, contentCol);
        contentValues.put(timeColumn, scheduleCol);
       // contentValues.put(idChildColumn, idChild);
        //contentValues.put(dateColumn, dateCol);
        //contentValues.put(testColumn, test);

        long result = db.insert(Table_occurrence, null, contentValues);

        //return id;

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Insert childs
    public boolean insertdata(String NAME_CHILD, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        //contentValues.put(Column1,ID_CHILD);
        contentValues.put(Column2, NAME_CHILD);
        contentValues.put(Column3, image);

        long result = db.insert(Table_child, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean insertUser(String USERNAME, String PASSWORD){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(userColumn,USERNAME);
        contentValues.put(passwordColumn,PASSWORD);
        long result=db.insert(Table_user,null,contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkusername(String username, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USER where username = ? and password = ?", new String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    /*-------------------------------------------------------------------------------------------------------------------*/


    public void queryData(String sql)
    {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public String getNameOcc(String name){
        SQLiteDatabase db = getReadableDatabase();
        String qu = "select * from Occurrence_table where NAME_OCCURRENCE='"+name+"'";
        Cursor cur = db.rawQuery(qu, null);

        if (cur.moveToNext()){
            String nameOc = cur.getString(1);
            cur.close();

            return nameOc;
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null;
    }






    public void insertOcc(String nameCol, String contentCol, String scheduleCol, String nameChild){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Table_occurrence VALUES (NULL, ?, ?, ?, ?)";


        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, nameCol);
        statement.bindString(2, contentCol);
        statement.bindString(3, scheduleCol);
        statement.bindString(4, nameChild);

        statement.executeInsert();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //long id = db.insert(Table_occurrence, null , contentValues);
        //String strDate = "DATA";
        //contentValues.put(idColumn,id);
        //contentValues.put(nameColumn, nameCol);
        //contentValues.put(contentColumn, contentCol);
        //contentValues.put(timeColumn, scheduleCol);
        //contentValues.put(Column2, nameChild);
        //contentValues.put(dateColumn, dateCol);
        //contentValues.put(testColumn, test);



    }

    public void updateData(String name, String price, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE FOOD SET name = ?, price = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }

    public void updateOcc(String content, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE Occurrence_table SET CONTENT_OCCURRENCE = ? WHERE ID_OCCURRENCE = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        //statement.bindString(1, name);
        statement.bindString(1, content);
        statement.bindDouble(2, (double)id);

        statement.execute();
        database.close();
    }




    public void deleteOcc(int ID_OCCURRENCE) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM Occurrence_table WHERE ID_OCCURRENCE=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)ID_OCCURRENCE);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }





    /*-----------------------------------------------------------------------------------------------------------------------------*/


    //----------------------  UPDATE -------------------------
    public boolean updatedata(String nameCol, String contentCol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(idColumn,id);
        contentValues.put(nameColumn, nameCol);
        contentValues.put(contentColumn, contentCol);
        Cursor cursor = db.rawQuery("Select * from" + Table_occurrence + " WHERE " + nameCol + "= ?", new String[]{contentCol});
        if (cursor.getCount() > 0) {

            long result = db.update(Table_occurrence, contentValues, "nameCol=?", new String[]{contentCol});


            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    public void updateOcc(String nameCol, String contentCol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(contentCol)

    }

    /*public String getOccName(String occName) {
        /*SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String empName = "";
        try {
            cursor = db.rawQuery("SELECT NAME_OCCURRENCE FROM Occurrence_table WHERE EmpNo=?", new String[] {occName + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                //empName = cursor.getString(cursor.getColumnIndex("EmployeeName"));
            }
            return empName;
        }finally {
            cursor.close();
        }*/

        /*SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select NAME_OCCURRENCE from Occurrence_table where NAME_OCCURRENCE=?",new String[]{occName});
        if (cursor.moveToFirst()) {
            int nameOccurrence = cursor.getInt(cursor.getColumnIndex("NAME_OCCURRENCE"));
        } else {
            nameOccurrence = 0;
        }
        return nameOccurrence;*/
    //}

    //----------------------  DELETE -------------------------

    public boolean deleteContent(String contentCol) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_occurrence, contentColumn + "=?", new String[]{contentCol}) > 0;
    }


    //----------------------  VIEW -------------------------
    public int GetIdOcc(String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ID_ FROM user WHERE username=?", new String[]{username});
        int id = -1;
        if (cursor.moveToFirst()) id = cursor.getInt(0);
        cursor.close();
        sqLiteDatabase.close();
        return id;
    }





    public Cursor ViewAllChild(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * from "+Table_child+"", null);

       /* if(res.getCount()!=0){
            while (res.moveToNext()){
                String nameChild=res.getString(1);
                byte[] photoChild=res.getBlob(2);

                Bitmap imageBitmap=BitmapFactory.decodeByteArray(photoChild,0,photoChild.length);

            }
        }*/
        //byte[] photo=res.getBlob(2);
        /*byte[] blob = null;
        if (res.moveToFirst()) blob = res.getBlob(2);
        return res;
        while(res.moveToFirst())
        {


        }*/

        return res;
    }

    /*public Occurrence occurrence(String occ){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * from "+Table_occurrence+" WHERE "+nameColumn+ "=" + occ, null);
        //return occ;

    }*/




    public Cursor ViewOcc(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * from "+Table_occurrence+"", null);
        return res;
    }

    //View all heatlh's occurences
    public Cursor ViewHealth(){
        SQLiteDatabase db = this.getReadableDatabase();
        String occ ="Obs_saude";
        String query = "select * from Occurrence_table where NAME_OCCURRENCE='"+occ+"'";
        Cursor  cursor = db.rawQuery(query,null);
        return cursor;

    }

    //View all diaper's occurences
    public Cursor ViewDiaper(){
        SQLiteDatabase db = this.getReadableDatabase();
        String occ ="Muda de fralda";
        String query = "select * from Occurrence_table where NAME_OCCURRENCE='"+occ+"'";
        Cursor  cursor = db.rawQuery(query,null);
        return cursor;

    }

    //View all lunch's occurences
    public Cursor ViewLunch(){
        SQLiteDatabase db = this.getReadableDatabase();
        String occ ="Almoço";
        String query = "select * from Occurrence_table where NAME_OCCURRENCE='"+occ+"'";
        Cursor  cursor = db.rawQuery(query,null);
        return cursor;

    }

    //View all snack's occurences
    public Cursor ViewSnack(){
        SQLiteDatabase db = this.getReadableDatabase();
        String occ ="Lanche";
        String query = "select * from Occurrence_table where NAME_OCCURRENCE='"+occ+"'";
        Cursor  cursor = db.rawQuery(query,null);
        return cursor;

    }
}
