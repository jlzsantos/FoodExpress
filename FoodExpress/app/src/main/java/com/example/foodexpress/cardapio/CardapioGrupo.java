package com.example.foodexpress.cardapio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodexpress.bancodados.PedidoHelper;
import com.example.foodexpress.bancodados.ProdutoHelper;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.entidades.Pedido;
import com.example.foodexpress.entidades.ProdutoGrupo;
import com.example.foodexpress.principal.ActivityBase;

import java.util.ArrayList;
import java.util.Date;

public class CardapioGrupo extends ActivityBase implements OnItemClickListener, View.OnClickListener {

    private Button btnMenu;
    private ListView _listaCardapioGrupo;
    private ArrayList<ProdutoGrupo> _listaProdutoGrupo;
    private PedidoHelper _pedidosHelper;
    private ProdutoHelper _produtoHelper;
    private Comanda _comanda;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_grupo);

        btnMenu = (Button)findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);

        _pedidosHelper = new PedidoHelper(this);
        _produtoHelper = new ProdutoHelper(this);

        _comanda = RetornaComanda();
        _listaProdutoGrupo = _produtoHelper.RetornaProdutoGrupos();

        _listaCardapioGrupo = (ListView)findViewById(R.id.lvCardapioGrupo);
        _listaCardapioGrupo.setAdapter(new ListViewAdapterCardapioGrupo(this, _listaProdutoGrupo));
        _listaCardapioGrupo.setOnItemClickListener(this);

        if (_comanda.isIniciarPedido()) {
            iniciaPedido();
        }
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
            case R.id.btnMenu:
                finishAll("com.example.foodexpress.principal.Main");
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

        TextView tvIdGrupo = (TextView)view.findViewById(R.id.idprodutogrupo);
        String idGrupo = tvIdGrupo.getText().toString();

        _comanda.setIdGrupo(Integer.parseInt(idGrupo));
        EnviaComanda(getApplicationContext(), CardapioProduto.class, _comanda);
    }

    private void iniciaPedido(){

        final ArrayList<Pedido> pedidos = _pedidosHelper.RetornaPedidosPorStatus(0);

        if (pedidos != null && pedidos.size() > 0) {

            final long idPedido = pedidos.get(pedidos.size()-1).getIdPedido();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Existe um pedido aberto. Você deseja manter os itens ou começar um novo pedido?")
                    .setTitle("FoodExpress")
                    .setCancelable(false)
                    .setPositiveButton("Manter", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        // Continua pedido que está aberto
                        _comanda.setIdPedido(idPedido);
                        }
                    })
                    .setNegativeButton("Novo", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        // Excluir itens do pedido
                        _produtoHelper.RemovePedidoItensPorIdPedido(idPedido);
                        _comanda.setIdPedido(idPedido);
                        }
                    })
                    .setNeutralButton("Menu", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        finish();
                        }
                    });
            builder.show();
        } else {
            // Adiciona um novo Pedido
            Pedido pedido = new Pedido(0, new Date(), 0);   // Status = 0 - Aberto
            PedidoHelper pHelper = new PedidoHelper(this);
            _comanda.setIdPedido(pHelper.AdicionaPedido(pedido));
        }
    }
}
