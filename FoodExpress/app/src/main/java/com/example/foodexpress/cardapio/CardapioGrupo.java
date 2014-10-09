package com.example.foodexpress.cardapio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.ProdutoGrupo;
import com.example.foodexpress.principal.ActivityBase;

import java.util.ArrayList;

public class CardapioGrupo extends ActivityBase implements OnItemClickListener, View.OnClickListener {

    private static final int request_code = 5;
    private Button btnCancelar;
    private ListView listaCardapioGrupo;
    private ArrayList<ProdutoGrupo> listaProdutoGrupo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_grupo);

        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        // Popula lista de grupos de produto para mostrar no cardápio
        listaProdutoGrupo = new ArrayList<ProdutoGrupo>();
        listaProdutoGrupo.add(new ProdutoGrupo(1, "Pizzas"));
        listaProdutoGrupo.add(new ProdutoGrupo(2, "Massas"));
        listaProdutoGrupo.add(new ProdutoGrupo(3, "Sobremesas"));
        listaProdutoGrupo.add(new ProdutoGrupo(4, "Bebidas"));

        listaCardapioGrupo = (ListView)findViewById(R.id.lvCardapioGrupo);
        listaCardapioGrupo.setAdapter(new ListViewAdapterCardapioGrupo(this, listaProdutoGrupo));
        listaCardapioGrupo.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cardapio_grupo, menu);
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
        int idControle = view.getId();

        switch (idControle){
            case R.id.btnCancelar:
                finish();
                break;
        }
    }

    @Override
    public void finish(){
        setResult(RESULT_CANCELED);
        super.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
        //String item = ((TextView)view).getText().toString();

        TextView tvIdGrupo = (TextView)view.findViewById(R.id.idprodutogrupo);
        String idGrupo = tvIdGrupo.getText().toString();

        Intent i = new Intent(getApplicationContext(), CardapioProduto.class);
        i.putExtra("idGrupo", idGrupo);
        startActivityForResult(i, request_code);

        /*
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("FoodExpress");
        dlg.setNeutralButton("Ok", null);
        dlg.setMessage(idGrupo);
        dlg.show();
        */
    }
}
