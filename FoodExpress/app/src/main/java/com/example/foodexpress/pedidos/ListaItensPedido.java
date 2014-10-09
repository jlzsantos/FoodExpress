package com.example.foodexpress.pedidos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.foodexpress.cardapio.CardapioGrupo;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Pedido;
import com.example.foodexpress.entidades.PedidoItem;
import com.example.foodexpress.principal.ActivityBase;

import java.util.ArrayList;
import java.util.Date;

public class ListaItensPedido extends ActivityBase implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int request_code = 5;
    private int idProduto;
    private PedidoItem item;
    private Button btnComprarMais;
    private Button btnFinalizar;
    private ListView listaPedidoItens;

    private Pedido pedido;
    private ArrayList<PedidoItem> pedidoItens;

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
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível listar os itens do pedido.");
            dlg.show();
            return;
        }

        item = (PedidoItem)extras.getSerializable("Item");

        AdicionaItemPedido(item);
        listaPedidoItens = (ListView)findViewById(R.id.lvPedidosItens);
        listaPedidoItens.setAdapter(new ListViewAdapterPedidosItens(this, pedidoItens));
        listaPedidoItens.setOnItemClickListener(this);

        /*
        String msg = item.getProduto().getdescricaoProduto() + " - " + Integer.toString(item.getProduto().getIdProduto()) + " - " + Float.toString(item.getQtde());
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("FoodExpress");
        dlg.setNeutralButton("Ok", null);
        dlg.setMessage(msg);
        dlg.show();
        */
    }

    private void AdicionaItemPedido(PedidoItem item)
    {
        if (item == null) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Item de pedido inválido.");
            dlg.show();
            return;
        }

        if (pedido == null){
            pedido = new Pedido();
            pedido.setIdPedido(754);
            pedido.setDataHoraEmissao(new Date());
            pedido.setStatusPedido(1);
        }

        if (pedidoItens == null) {
            pedidoItens = new ArrayList<PedidoItem>();
            pedidoItens.add(new PedidoItem(pedido.getIdPedido(), 1, item.getQtde(), item.getVlrUnit(), item.getVlrTotal(), item.getProduto()));
        } else {
            pedidoItens.add(new PedidoItem(pedido.getIdPedido(), 1, item.getQtde(), item.getVlrUnit(), item.getVlrTotal(), item.getProduto()));
        }

        pedido.setItensPedido(pedidoItens);
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
                Intent i = new Intent(getApplicationContext(), CardapioGrupo.class);
                i.putExtra("Pedido", 1);
                startActivity(i);
                break;

            case R.id.btnFinalizar:
                Intent fi = new Intent(getApplicationContext(), FinalizarPedido.class);
                fi.putExtra("Pedido", (java.io.Serializable)pedido);
                startActivity(fi);
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
