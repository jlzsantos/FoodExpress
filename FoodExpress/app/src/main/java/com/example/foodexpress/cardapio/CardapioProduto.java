package com.example.foodexpress.cardapio;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.foodexpress.bancodados.ProdutoHelper;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.entidades.Produto;
import com.example.foodexpress.principal.ActivityBase;

import java.util.ArrayList;

public class CardapioProduto extends ActivityBase implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Button btnCancelar;
    private ListView _listaCardapioProduto;
    private ArrayList<Produto> _listaProdutos;
    private Comanda _comanda;
    private ProdutoHelper _produtoHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_produto);

        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            showMensagem("Não foi possível listar os produtos.");
            return;
        }

        _comanda = RetornaComanda();

        _produtoHelper = new ProdutoHelper(getApplicationContext());
        _listaProdutos = new ArrayList<Produto>();
        _listaProdutos = _produtoHelper.RetornaProdutoPorGrupoId(_comanda.getIdGrupo());

        _listaCardapioProduto = (ListView)findViewById(R.id.lvCardapioProduto);
        _listaCardapioProduto.setAdapter(new ListViewAdapterCardapioProduto(this, _listaProdutos));
        _listaCardapioProduto.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cardapio_produto, menu);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {

        Produto produto = (Produto)adapterView.getItemAtPosition(posicao);
        _comanda.setProduto(produto);
        EnviaComanda(getApplicationContext(), CardapioDetalhe.class, _comanda);
    }

    @Override
    public void finish(){
        setResult(RESULT_CANCELED);
        super.finish();
    }
}
