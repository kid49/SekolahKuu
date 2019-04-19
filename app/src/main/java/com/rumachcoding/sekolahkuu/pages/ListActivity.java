package com.rumachcoding.sekolahkuu.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rumachcoding.sekolahkuu.R;
import com.rumachcoding.sekolahkuu.adapters.StudentItemAdapter;
import com.rumachcoding.sekolahkuu.database.StudentDataSource;
import com.rumachcoding.sekolahkuu.model.Student;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    StudentItemAdapter studentAdapter;
    List<Student>studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView lvStudent = findViewById(R.id.LvStudent);

        studentList = new ArrayList<>();

        studentAdapter = new StudentItemAdapter(ListActivity.this,studentList);

        lvStudent.setAdapter(studentAdapter);

        lvStudent.setOnItemClickListener(ListActivity.this);

        SearchView searchData = findViewById(R.id.sv_caridata);

        searchData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keywoard) {
                studentList.clear();

                StudentDataSource studentDataSource = new StudentDataSource(ListActivity.this);
                List<Student> resultList = studentDataSource.search(keywoard);

                studentList.addAll(resultList);
                studentAdapter.notifyDataSetChanged();
                return false;
            }
        });

        registerForContextMenu(lvStudent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        studentList.clear();

        StudentDataSource studentDataSource = new StudentDataSource(ListActivity.this);
        List<Student> resultList = studentDataSource.getAll();

        studentList.addAll(resultList);

        for (int i = 0; i<studentList.size();i++){
            Student student = studentList.get(i);

        }

        studentAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.list_menu,menu);

        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_add:
                Intent add = new Intent(ListActivity.this,FormActivity.class);

                startActivity(add);

                return true;

                default:

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Student student = studentList.get(position);

        Intent detailhalaman = new Intent(ListActivity.this,DetailActivity.class);
        detailhalaman.putExtra("id",student.getId());

        startActivity(detailhalaman);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if (v.getId() == R.id.LvStudent){
            getMenuInflater().inflate(R.menu.context_menu,menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menucontext_edit:
                //aksi edit contextmenu
                AdapterView.AdapterContextMenuInfo acmiedit = (AdapterView.AdapterContextMenuInfo)menuInfo;
                Student studentEdit = studentList.get(acmiedit.position);

                //berpindah untuk mengedit data
                Intent edit = new Intent(ListActivity.this,FormActivity.class);
                edit.putExtra("id",studentEdit.getId());
                startActivity(edit);

                return true;
            case R.id.menucontext_delete:
                //aksi delete contextmenu
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo)menuInfo;
                Student student = studentList.get(acmi.position);

                //hapus data dari database
                StudentDataSource studentDataSource = new StudentDataSource(ListActivity.this);
                studentDataSource.removeStudent(student);

                //refresh listview
                studentList.remove(acmi.position);

                //notifikasi adapter
                studentAdapter.notifyDataSetChanged();
                return true;
                default:
                    return super.onContextItemSelected(item);

        }

    }
}
