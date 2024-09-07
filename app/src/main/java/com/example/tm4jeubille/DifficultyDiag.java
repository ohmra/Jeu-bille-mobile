package com.example.tm4jeubille;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DifficultyDiag extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            CharSequence[] items = {"lent", "moyen", "rapide"};
            dialogBuilder.setItems(items, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    switch(i){
                        case 1:
                            //difficulte = 3f;
                            break;
                        case 2:
                            //difficulte = 4f;
                            break;
                        default:
                            //difficulte = 1f;
                            break;
                    }
                }
            });
            return dialogBuilder.create();
        }
}
