package com.example.phamtuananhkiemtra2bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewCourse;
    ArrayList<KhoaHoc> mKhoaHoc = new ArrayList<>();
    ImageView imageButtonSearch;
    EditText editTextSearch;
    Button btnAdd;
    Button btnNgayBatDau;
    KhoaHocAdapter khoaHocAdapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());

        getView();
        getData();
        setRecyclerView();

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextSearch.getText().toString().trim().equals(""))
                    khoaHocAdapter.getFilter().filter(editTextSearch.getText().toString().trim());
                else {
                    Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_LONG).show();
                    getData();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.add_khoa_hoc, null);
                dialogBuilder.setView(dialogView);

                EditText editTextName = dialogView.findViewById(R.id.editTextTen);
                btnNgayBatDau = dialogView.findViewById(R.id.btnNgayBatDau);
                Spinner spinnerMajor = dialogView.findViewById(R.id.spinner);
                CheckBox checkboxActive = dialogView.findViewById(R.id.checkboxActive);
                Button buttonSave = dialogView.findViewById(R.id.btnSave);
                Button buttonCancel = dialogView.findViewById(R.id.btnCancel);

                Spinner dropdown = dialogView.findViewById(R.id.spinner);
                String[] items = new String[]{"Tieng anh", "cntt", "kinh te", "truyen thong"};
                ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);


                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                btnNgayBatDau.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePicker();
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String active = "Not active";
                        if (checkboxActive.isChecked()) {
                            active = "Active";
                        }

                        khoaHoc = new KhoaHoc("1", editTextName.getText().toString(),
                                btnNgayBatDau.getText().toString(),
                                spinnerMajor.getSelectedItem().toString(), active);

                        mKhoaHoc.add(khoaHoc);

                        dbHelper.insertKhoaHoc(khoaHoc);

                        khoaHocAdapter.notifyDataSetChanged();

                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    private void datePicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        btnNgayBatDau.setText(date_time);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setRecyclerView() {
        khoaHocAdapter = new KhoaHocAdapter(mKhoaHoc);
        recyclerViewCourse.setAdapter(khoaHocAdapter);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getData() {
        mKhoaHoc.addAll(dbHelper.getAllKhoaHoc());
        setRecyclerView();
    }

    private void getView() {
        recyclerViewCourse = findViewById(R.id.recyclerViewOrder);
        btnAdd = findViewById(R.id.buttonAdd);
        editTextSearch = findViewById(R.id.editTextSearch);
        imageButtonSearch = findViewById(R.id.imageButtonSearch);
    }
}