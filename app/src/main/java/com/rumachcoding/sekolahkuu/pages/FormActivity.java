package com.rumachcoding.sekolahkuu.pages;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rumachcoding.sekolahkuu.R;
import com.rumachcoding.sekolahkuu.database.StudentDataSource;
import com.rumachcoding.sekolahkuu.model.Student;

public class FormActivity extends AppCompatActivity {

    EditText etnamadepan,etnamabelakang,etnamaalamat,etnohp;
    RadioGroup radioGroup;
    RadioButton radiopria,radiowanita;
    Spinner spinner;
    CheckBox baca,nulis,gambar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide1_tasklogin);

        etnamadepan = (EditText)findViewById(R.id.ed_namadepan);
        etnamabelakang = (EditText)findViewById(R.id.ed_namabelakang);
        etnamaalamat = (EditText)findViewById(R.id.ed_alamat);
        etnohp = (EditText)findViewById(R.id.et_hp);
        radioGroup = (RadioGroup)findViewById(R.id.radiogrup);
        radiopria = (RadioButton)findViewById(R.id.radiopria);
        radiowanita = (RadioButton)findViewById(R.id.radiowanita);
        spinner = (Spinner)findViewById(R.id.spinner);
        baca = (CheckBox)findViewById(R.id.check_baca);
        nulis = (CheckBox)findViewById(R.id.check_menulis);
        gambar = (CheckBox)findViewById(R.id.check_menggambar);
        Button simpan = (Button)findViewById(R.id.btnsimpan);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });

        //mengambil data dari list ke form untuk edit //

        long id = getIntent().getLongExtra("id",0);
        if (id >0){
            //ketika edit
            StudentDataSource studentDataSource = new StudentDataSource(FormActivity.this);
            Student student = studentDataSource.getStudent(id);

            if(student != null){
                //EditText
                etnamadepan.setText(student.getNamaDepan());
                etnamabelakang.setText(student.getNamaBelakang());
                etnohp.setText(student.getNomorHp());
                etnamaalamat.setText(student.getAlamat());

                //radioButton
                if (student.getGender().equals("Pria")){
                    radiopria.setChecked(true);
                    radiowanita.setChecked(false);
                } else if (student.getGender().equals("Wanita")){
                    radiopria.setChecked(false);
                    radiowanita.setChecked(true);
                }
                //Spinner form data
                ArrayAdapter spAdapter = (ArrayAdapter)spinner.getAdapter();
                int spPosition = spAdapter.getPosition(student.getSpinner());
                spinner.setSelection(spPosition);

                //checkbox ==>> Mengisi box untuk di check
                boolean isNulisChecked = student.getHobi().contains(nulis.getText().toString());
                nulis.setChecked(isNulisChecked);
                boolean isBacaChecked = student.getHobi().contains(baca.getText().toString());
                baca.setChecked(isBacaChecked);
                boolean isGambarChecked = student.getHobi().contains(gambar.getText().toString());
                gambar.setChecked(isGambarChecked);
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void saveData() {
        String namadepan = etnamadepan.getText().toString();
        String namabelakang = etnamabelakang.getText().toString();
        String nomorhp = etnohp.getText().toString();
        String alamat = etnamaalamat.getText().toString();

        //bisa menggunakan if else
                /*String gender = "";
                if (radioGroup.getCheckedRadioButtonId() == R.id.radiopria){
                    gender = "Pria";
                } else{
                    gender = "Wanita";
                }*/


        //menggunakan metode ringan
        String gender = radioGroup.getCheckedRadioButtonId()==R.id.radiopria?"Pria":"Wanita";


        //menambahkan string pada form data
        String spinnerr = spinner.getSelectedItem().toString();

        String strHobi = "";
        if (baca.isChecked()){
            strHobi =baca.getText().toString();
        }
        if (nulis.isChecked()){
            strHobi += "," + nulis.getText().toString();
        }
        if (gambar.isChecked()){
            strHobi += "," + gambar.getText().toString();
        }

        //form validation
        if(namadepan.isEmpty()){
            etnamadepan.setError("Wajib Isi");

            return;
        }
        if (namabelakang.isEmpty()){
            etnamabelakang.setError("Wajib Isi");

            return;
        }
        if (nomorhp.isEmpty()){
            etnohp.setError("Wajib Isi");
            return;
        }
        if(nomorhp.length()>13){
            etnohp.setError("Minimal 13");
            return;
        }

        if (alamat.isEmpty()){
            etnamaalamat.setError("Wajib Isi");
            return;
        }


        /*
        String informasi = "Hello :"+ namadepan +"" + namabelakang +"\n" +
                "No Hp : "+nomorhp+"\n" +
                "Gender :"+ gender+"\n" +
                "Jenjang :" + spinnerr+"\n" +
                "Hobi :" + strHobi+"\n" +
                "Alamat :"+alamat;

        Toast.makeText(FormActivity.this,informasi, Toast.LENGTH_SHORT).show();
        */

        //dapat object student

        Student student = new Student();
        student.setNamaDepan(namadepan);
        student.setNamaBelakang(namabelakang);
        student.setNomorHp(nomorhp);
        student.setGender(gender);
        student.setSpinner(spinnerr);
        student.setHobi(strHobi);
        student.setAlamat(alamat);

        // invoke method addStudent form StudentDataSource
        StudentDataSource studentDataSource = new StudentDataSource(FormActivity.this);

        long id = getIntent().getLongExtra("id",0);
        if (id > 0){
            //mode update data
            student.setId(id);
            studentDataSource.updateStudent(student);

            Toast.makeText(FormActivity.this, "Telah Ter-Update", Toast.LENGTH_SHORT).show();
            finish();

        } else {
            //mode add
            boolean isSucces = studentDataSource.addStudent(student);

            if (isSucces){
                Toast.makeText(FormActivity.this, "Telah Tersimpan", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(FormActivity.this, "Gagal Tersimpan", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                saveData();
                return true;
            case android.R.id.home:
                finish();
                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }
    }
}
