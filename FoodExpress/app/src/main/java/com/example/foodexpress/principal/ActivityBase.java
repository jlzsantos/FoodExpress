package com.example.foodexpress.principal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.entidades.Produto;

import java.util.ArrayList;

/**
 * Created by jose.santos on 07/10/2014.
 */
public class ActivityBase extends Activity {

    private static final String TAG = ActivityBase.class.getName();
    private static ArrayList<Activity> activities=new ArrayList<Activity>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        activities.remove(this);
    }

    public static void finishAll()
    {
        for(Activity activity:activities)
            activity.finish();
    }

    public static void finishAll(String excludeActivityName)
    {
        for(Activity activity:activities) {
            if (!activity.getLocalClassName().toUpperCase().equals(excludeActivityName.toUpperCase())) {
                activity.finish();
            }
        }
    }

    protected Comanda RetornaComanda(){

        Comanda comanda;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            showMensagemRetornaMenu("Comanda inválida.");
            return null;
        }

        comanda = (Comanda)extras.getSerializable("Comanda");

        if (comanda == null) {
            showMensagemRetornaMenu("Comanda inválida.");
            return null;
        }

        return comanda;
    }

    protected void EnviaComanda(Context contexto, Class<?> atividade, Comanda comanda){
        Intent i = new Intent(contexto, atividade);
        i.putExtra("Comanda", comanda);
        startActivity(i);
    }

    public void showMensagem(String msg) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("FoodExpress");
        dlg.setNeutralButton("Ok", null);
        dlg.setMessage(msg);
    }

    protected void showMensagemRetornaMenu(String msg, final Intent atividade) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TextView viewT = new TextView(this);

        builder.setTitle("FoodExpress").setMessage(msg).setView(viewT);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                startActivity(atividade);
                finishAll("com.example.foodexpress.principal.Main");
            }
        });
        builder.create().show();
    }

    protected void showMensagemRetornaMenu(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TextView viewT = new TextView(this);

        builder.setTitle("FoodExpress").setMessage(msg).setView(viewT);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                finishAll("com.example.foodexpress.principal.Main");
            }
        });
        builder.create().show();
    }
}
