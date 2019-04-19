package com.rumachcoding.sekolahkuu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.rumachcoding.sekolahkuu.R;
import com.rumachcoding.sekolahkuu.model.Student;

import java.util.List;

public class StudentItemAdapter extends ArrayAdapter<Student> {
    public StudentItemAdapter(Context context, List<Student> studentList){
        super(context, R.layout.custom_list,studentList);
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_list,null);
        }

        Student student = getItem(position);

        //mendapatkan objek dari textview semua yang ada di list.
        TextView tvNama = view.findViewById(R.id.txtnama);
        TextView tvGender = view.findViewById(R.id.txtgender);
        TextView tvJenjang = view.findViewById(R.id.txt_sd);
        TextView tvHp = view.findViewById(R.id.txt_hp);

        //isi textview masing2 dengan data student
        tvNama.setText(student.getNamaDepan()+""+student.getNamaBelakang());
        tvGender.setText(student.getGender());
        tvJenjang.setText(student.getSpinner());
        tvHp.setText(student.getNomorHp());

        return view;
    }
}
