package com.rumachcoding.sekolahkuu.pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.rumachcoding.sekolahkuu.R;
import com.rumachcoding.sekolahkuu.database.StudentDataSource;
import com.rumachcoding.sekolahkuu.model.Student;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvDetailnama = findViewById(R.id.tvDetail_nama);
        TextView tvDetailHp = findViewById(R.id.tvDetail_handphone);
        TextView tvDetailGender = findViewById(R.id.tvDetail_gender);
        TextView tvDetailJenjang = findViewById(R.id.tvDetail_jenjang);
        TextView tvDetailHobi = findViewById(R.id.tvDetail_hobi);
        TextView tvDetailAlamat = findViewById(R.id.tvDetail_alamat);



        long id = getIntent().getLongExtra("id",0);

        StudentDataSource studentDataSource = new StudentDataSource(DetailActivity.this);
        Student student = studentDataSource.getStudent(id);

        tvDetailnama.setText(student.getNamaDepan()+""+student.getNamaBelakang());
        tvDetailHp.setText(student.getGender());
        tvDetailGender.setText(student.getSpinner());
        tvDetailJenjang.setText(student.getNomorHp());
        tvDetailHobi.setText(student.getSpinner());
        tvDetailAlamat.setText(student.getAlamat());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
