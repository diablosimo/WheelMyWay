package com.example.wheel.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.wheel.R;

public class ReclamationDialog extends DialogFragment {
    private static final String TAG = "REC_DIALOG";
    public ReclamationDialogListener listener;
    private EditText et_objet;
    private EditText et_message;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_reclamation, null);

        builder.setView(view)
                .setTitle("Ajouter une réclamation/avis")
                .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String objet = et_objet.getText().toString();
                        String message = et_message.getText().toString();
                        listener.applyTexts(objet, message);

                    }
                });

        et_message = view.findViewById(R.id.et_message);
        et_objet = view.findViewById(R.id.et_objet);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ReclamationDialogListener) getTargetFragment();
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement ReclamationDialogException");
        }
    }

    public interface ReclamationDialogListener {
        void applyTexts(String objet, String message);
    }
}
