package com.example.foodexpress.principal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.foodexpress.cardapio.CardapioGrupo;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.pedidos.ListaPedidos;


public class Main extends ActivityBase implements View.OnClickListener {

    private static final int request_code = 5;
    private Button btnCardapio;
    private Button btnPedidos;
    private Button btnEnderecos;
    private Button btnFechar;
    private Comanda comanda;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCardapio = (Button)findViewById(R.id.btnCardapio);
        btnPedidos = (Button)findViewById(R.id.btnPedidos);
        btnEnderecos = (Button)findViewById(R.id.btnEnderecos);
        btnFechar = (Button)findViewById(R.id.btnFechar);

        btnCardapio.setOnClickListener(this);
        btnPedidos.setOnClickListener(this);
        btnEnderecos.setOnClickListener(this);
        btnFechar.setOnClickListener(this);

        comanda = new Comanda();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent pConstrucao = new Intent(getApplicationContext(), EmConstrucao.class);
        int idControle = view.getId();

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("FoodExpress");
        dlg.setNeutralButton("Ok", null);
        dlg.setMessage("Pedidos");
        //dlg.show();

        switch (idControle){
            case R.id.btnCardapio:
                Intent iCardapioGrupo = new Intent(getApplicationContext(), CardapioGrupo.class);
                iCardapioGrupo.putExtra("Comanda", comanda);
                startActivity(iCardapioGrupo);
                break;

            case R.id.btnPedidos:
                Intent iListaPedidos = new Intent(getApplicationContext(), ListaPedidos.class);
                startActivity(iListaPedidos);
                break;

            case R.id.btnEnderecos:
                startActivity(pConstrucao);
                break;

            case R.id.btnFechar:
                //android.os.Process.killProcess(android.os.Process.myPid());
                finishAll();
                //System.exit(0);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("FoodExpress");
        dlg.setNeutralButton("Ok", null);

        if (requestCode == request_code && requestCode == RESULT_OK){
            dlg.setMessage("Ok!");
            dlg.show();
        } else {
            //dlg.setMessage("Cancelado!");
            //dlg.show();
        }
    }

    @Override
    public void onDestroy() {
        // closing Entire Application
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}
