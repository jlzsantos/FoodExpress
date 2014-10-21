package com.example.foodexpress.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jose.santos on 15/10/2014.
 */
public final class Util {

    private Context _contexto;
    private String _dialogTitle = "FoodExpress";

    public Util(Context contexto){
        super();
        this._contexto = contexto;
    }

    public Util(Context contexto, String title){
        super();
        this._contexto = contexto;
        this._dialogTitle = title;
    }

    public void showMensagem(String msg) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(_contexto);
        dlg.setTitle(_dialogTitle);
        dlg.setNeutralButton("Ok", null);
        dlg.setMessage(msg);
        dlg.show();
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }
}
