package com.example.aipuzzle;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet extends BottomSheetDialogFragment {

    double runTime;
    int move;
    TextView timeText, moveText;

    public static  BottomSheet newInstance(double runTime, int move){
        BottomSheet myBottomSheetFracment = new BottomSheet();
        Bundle bundle = new Bundle();
        bundle.putDouble("runtime", runTime);
        bundle.putInt("move", move);
        myBottomSheetFracment.setArguments(bundle);
        return  myBottomSheetFracment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            runTime = bundleReceive.getDouble("runtime");
            move = bundleReceive.getInt("move");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(viewDialog);
        timeText = viewDialog.findViewById(R.id.timeText);
        moveText = viewDialog.findViewById(R.id.moveText);
        timeText.setText(String.valueOf(runTime));
        moveText.setText(String.valueOf(move));
        return bottomSheetDialog;
    }
}
