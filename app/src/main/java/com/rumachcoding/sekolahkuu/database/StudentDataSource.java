package com.rumachcoding.sekolahkuu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rumachcoding.sekolahkuu.model.Student;

import java.util.ArrayList;
import java.util.List;

public final class StudentDataSource {

    //import database helper in here and create objek

    private SQLiteDatabase database; // objek untuk CRUD
    private DatabaseHelper dbHelper; // Create database

    public StudentDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    // method open database
    public void open() throws SQLException { //exception == error
        database = dbHelper.getWritableDatabase();
    }

    //method close database
    public void close(){
        dbHelper.close();
    }
    public boolean addStudent(Student student){
        try {

            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put("nama_depan", student.getNamaDepan());
            contentValues.put("nama_belakang", student.getNamaBelakang());
            contentValues.put("no_hp", student.getNomorHp());
            contentValues.put("gender", student.getGender());
            contentValues.put("spinner", student.getSpinner());
            contentValues.put("hobi", student.getHobi());
            contentValues.put("alamat", student.getAlamat());

            database.insert("student", null, contentValues);
            close();

            return true;

        } catch (SQLException e){
            return false;
        } catch (Exception e){
            return false;
        }
    }
    private Student fetchRow(Cursor cursor){
        Student student = new Student();

        student.setId(cursor.getLong(0));
        student.setNamaDepan(cursor.getString(1));
        student.setNamaBelakang(cursor.getString(2));
        student.setNomorHp(cursor.getString(3));
        student.setGender(cursor.getString(4));
        student.setSpinner(cursor.getString(5));
        student.setHobi(cursor.getString(6));
        student.setAlamat(cursor.getString(7));

        return student;
    }

    public List<Student> getAll(){
        open();
        Cursor cursor = database.rawQuery("SELECT * FROM student",null);
        cursor.moveToFirst();

        //buat object
        List<Student>studentList = new ArrayList<>();

        while (!cursor.isAfterLast()){
            //get data student,detail Codingan dibawah :
            Student student = fetchRow(cursor);

            // masukan data Student kedalam list Student,codingan :
            studentList.add(student);

            //lanjut interasi
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return studentList;
    }
    public Student getStudent(long id){
        open();
        String sql = "SELECT * FROM student WHERE id =?";
        Cursor cursor = database.rawQuery(sql,new String[]{
                Long.toString(id)});
        cursor.moveToFirst();

        Student student = fetchRow(cursor);
        cursor.close();
        close();
        return student;
    }
    public List<Student>search(String keywoard){
        open();
        String sql = "SELECT * FROM student WHERE nama_depan LIKE? OR nama_belakang LIKE ?";
        Cursor cursor = database.rawQuery(sql,new String[]{"%"+keywoard+"%","%"+keywoard+"%"});
        cursor.moveToFirst();

        List<Student> studentList = new ArrayList<>();
        while(!cursor.isAfterLast()){
            //data student
            Student student = fetchRow(cursor);


            studentList.add(student);
            cursor.moveToNext();

        }
        cursor.close();
        close();

        return studentList;
    }

    public void removeStudent(Student student){
        open();
        database.delete("student","id=?",new String[]{Long.toString(student.getId())});
        close();
    }
    public void updateStudent(Student student){

        //sebuah database harus diawali dengan open(); dan penutup close();
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getNomorHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("spinner", student.getSpinner());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());

        //update data database
        database.update("student",contentValues,"id=?",new String[]{Long.toString(student.getId())});
        close();
    }
}
