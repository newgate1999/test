package com.example.phamtuananhkiemtra2bai2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoaHocAdapter extends RecyclerView.Adapter<KhoaHocAdapter> implements Filterable {

    ArrayList<KhoaHoc> objects;
    Context context;
    DBHelper dbHelper;
    private ArrayList<KhoaHoc> mDisplayedValues = new ArrayList<>();
    Button ngayBatDau;
    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    public KhoaHocAdapter(ArrayList<KhoaHoc> cours) {
        mDisplayedValues = cours;
        objects = cours;
    }

    @NonNull
    @Override
    public KhoaHocAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item, parent, false);
        KhoaHocAdapter viewHolder = new KhoaHocAdapter;
        context = parent.getContext();
        dbHelper = new DBHelper((context));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KhoaHocViewHolder holder, int position) {
        KhoaHoc khoaHoc = mDisplayedValues.get(position);

        holder.textviewTen.setText(khoaHoc.getName());
        holder.textViewID.setText(khoaHoc.getId());
        holder.textviewNgaybatDau.setText(khoaHoc.getDate());
        holder.texviewLinhVuc.setText(khoaHoc.getMajor());
        if (khoaHoc.getActive().equals("Active")) {
            holder.checkBoxActive.setChecked(true);
        }
        holder.buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDisplayedValues.remove(position);
                dbHelper.(Integer.parseInt(khoaHoc.getId()));
                notifyDataSetChanged();
            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.add_khoa_hoc, null);
                dialogBuilder.setView(dialogView);

                EditText editTextName = dialogView.findViewById(R.id.editTextTen);
                ngayBatDau = dialogView.findViewById(R.id.btnNgayBatDau);
                Spinner spinnerMajor = dialogView.findViewById(R.id.spinner);
                CheckBox checkboxActive = dialogView.findViewById(R.id.checkboxActive);
                Button buttonSave = dialogView.findViewById(R.id.btnSave);
                Button buttonCancel = dialogView.findViewById(R.id.btnCancel);

                Spinner dropdown = dialogView.findViewById(R.id.spinner);
                String[] items = new String[]{"Tieng anh", "cntt", "kinh te", "truyen thong"};
                ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);

                editTextName.setText(khoaHoc.getName());
                ngayBatDau.setText(khoaHoc.getDate());
                if (khoaHoc.getMajor().equals("Tieng anh")){
                    spinnerMajor.setSelection(0);
                }

                if (khoaHoc.getMajor().equals("cntt")){
                    spinnerMajor.setSelection(1);
                }

                if (khoaHoc.getMajor().equals("kinh te")){
                    spinnerMajor.setSelection(2);
                }

                if (khoaHoc.getMajor().equals("truyen thong")){
                    spinnerMajor.setSelection(3);
                }

                if (khoaHoc.getActive() == "Active"){
                    checkboxActive.setChecked(true);
                }


                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();


                ngayBatDau.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                        ngayBatDau.setText(date_time);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
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

                        KhoaHoc moi = new KhoaHoc(khoaHoc.getId(), editTextName.getText().toString(),
                                ngayBatDau.getText().toString(),
                                spinnerMajor.getSelectedItem().toString(), active);

                        khoaHoc.setName(editTextName.getText().toString());
                        khoaHoc.setMajor(spinnerMajor.getSelectedItem().toString());
                        khoaHoc.setDate(ngayBatDau.getText().toString());
                        khoaHoc.setActive(active);

                        dbHelper.updateKhoaHoc(moi);

                        notifyDataSetChanged();

                        alertDialog.dismiss();
                    }
                });
            }
        });


    }


    @Override
    public int getItemCount() {
        return mDisplayedValues.size();
    }

    void submitList(ArrayList<KhoaHoc> khoa) {
        mDisplayedValues.clear();
        mDisplayedValues.addAll(khoa);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = objects.size();
                    filterResults.values = objects;

                } else {
                    List<KhoaHoc> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (KhoaHoc itemsModel : objects) {
                        if (itemsModel.getName().contains(searchStr)) {
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDisplayedValues = (ArrayList<KhoaHoc>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
