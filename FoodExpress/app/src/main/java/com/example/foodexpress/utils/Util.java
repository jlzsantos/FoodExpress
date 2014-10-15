package com.example.foodexpress.utils;

import android.app.AlertDialog;
import android.content.Context;

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

    public final AlertDialog.Builder RetornaSimpleDialog(String msg){
        AlertDialog.Builder dlg = new AlertDialog.Builder(_contexto);
        dlg.setTitle(_dialogTitle);
        dlg.setNeutralButton("Ok", null);
        dlg.setMessage(msg);
        return dlg;
    }
}
