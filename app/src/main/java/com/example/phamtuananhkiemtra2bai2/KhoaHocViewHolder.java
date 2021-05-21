package com.example.phamtuananhkiemtra2bai2;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class KhoaHocViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewID;
    public TextView textviewTen;
    public TextView texviewLinhVuc;
    public TextView textviewNgaybatDau;
    public CheckBox checkBoxActive;
    public Button buttonEdit;
    public Button buttonXoa;

    public KhoaHocViewHolder(View itemView) {
        super(itemView);

        textViewID =  itemView.findViewById(R.id.textViewId);
        textviewTen =  itemView.findViewById(R.id.textViewTen);
        textviewNgaybatDau =  itemView.findViewById(R.id.textViewNgayBatDau);
        texviewLinhVuc =  itemView.findViewById(R.id.textViewLinhVuc);
        checkBoxActive =  itemView.findViewById(R.id.checkboxActive);
        buttonXoa =  itemView.findViewById(R.id.btnDelete);
        buttonEdit =  itemView.findViewById(R.id.btnEdit);

    }
}
