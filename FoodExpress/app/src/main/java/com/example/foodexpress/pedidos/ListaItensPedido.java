package com.example.foodexpress.pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.foodexpress.bancodados.PedidoHelper;
import com.example.foodexpress.cardapio.CardapioGrupo;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.entidades.Pedido;
import com.example.foodexpress.entidades.PedidoItem;
import com.example.foodexpress.principal.ActivityBase;

import java.util.ArrayList;

public class ListaItensPedido extends ActivityBase implements AdapterView.OnItemClickListener, View.OnClickListener {

    private PedidoItem item;
    private Button btnComprarMais;
    private Button btnFinalizar;
    private ListView _listaPedidoItens;
    private Comanda _comanda;

    private Pedido pedido;
    private ArrayList<PedidoItem> _pedidoItens;
    private PedidoHelper _pedidosHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens_pedido);

        btnComprarMais = (Button)findViewById(R.id.btnComprarMais);
        btnComprarMais.setOnClickListener(this);

        btnFinalizar = (Button)findViewById(R.id.btnFinalizar);
        btnFinalizar.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            showMensagem("Não foi possível listar os itens do pedido.");
            return;
        }

        _pedidosHelper = new PedidoHelper(this);
        _comanda = RetornaComanda();

        _pedidoItens = new ArrayList<PedidoItem>();
        _pedidoItens = _pedidosHelper.RetornaPedidoItensPorIdPedido(_comanda.getIdPedido());

        _listaPedidoItens = (ListView)findViewById(R.id.lvPedidosItens);
        _listaPedidoItens.setAdapter(new ListViewAdapterPedidosItens(this, _pedidoItens));
        _listaPedidoItens.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_itens_pedido, menu);
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
            case R.id.btnComprarMais:
                _comanda.setIniciarPedido(false);
                EnviaComanda(getApplicationContext(), CardapioGrupo.class, _comanda);
                break;

            case R.id.btnFinalizar:
                //Intent fi = new Intent(getApplicationContext(), FinalizarPedido.class);
                //fi.putExtra("Pedido", (java.io.Serializable)pedido);
                //startActivity(fi);
                EnviaComanda(getApplicationContext(), FinalizarPedido.class, _comanda);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //
    }

    @Override
    public void finish(){
        setResult(RESULT_CANCELED);
        super.finish();
    }
}
