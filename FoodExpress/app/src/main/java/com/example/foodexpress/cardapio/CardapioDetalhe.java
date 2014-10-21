package com.example.foodexpress.cardapio;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodexpress.bancodados.PedidoHelper;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.entidades.PedidoItem;
import com.example.foodexpress.entidades.Produto;
import com.example.foodexpress.pedidos.ListaItensPedido;
import com.example.foodexpress.principal.ActivityBase;
import com.example.foodexpress.utils.Util;

public class CardapioDetalhe extends ActivityBase implements View.OnClickListener {

    private Button btnCancelar;
    private Button btnAdicionar;
    private TextView tvIngredientes;
    private TextView tvProduto;
    private EditText etQtde;
    private ImageView imgProduto;
    private long _idProduto;
    private Produto _produto;
    private PedidoHelper _pedidosHelper;
    private Comanda _comanda;
    private Util _util;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_detalhe);

        _pedidosHelper = new PedidoHelper(this);
        _util = new Util(this);

        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        btnAdicionar = (Button)findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(this);

        imgProduto = (ImageView)findViewById(R.id.imgProduto);
        tvIngredientes = (TextView)findViewById(R.id.tvIngredientes);
        tvProduto = (TextView)findViewById(R.id.tvProduto);
        etQtde = (EditText)findViewById(R.id.etQtde);

        _comanda = RetornaComanda();
        _produto = _comanda.getProduto();
        _idProduto = _produto.getIdProduto();

        super.setTitle(_produto.getDescricaoProduto());
        tvIngredientes.setText(_produto.getIngredientes());

        String preco_format = String.format("%.2f", _produto.getPrecoVenda());
        preco_format = "R$ " + preco_format.replace(".", ",");
        tvProduto.setText(_produto.getDescricaoProduto() + " - " + preco_format);

        if (_idProduto >= 1 && _idProduto <= 20) {
            imgProduto.setImageResource(R.drawable.pizza_144x144);
        } else if (_idProduto >= 21 && _idProduto <= 30) {
            imgProduto.setImageResource(R.drawable.ic_foodexpress);
        } else if (_idProduto >= 31 && _idProduto <= 40) {
            imgProduto.setImageResource(R.drawable.ic_foodexpress);
        } else if (_idProduto >= 41 && _idProduto <= 50) {
            imgProduto.setImageResource(R.drawable.ic_foodexpress);
        } else {
            imgProduto.setImageResource(R.drawable.ic_foodexpress);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cardapio_detalhe, menu);
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

            case R.id.btnAdicionar:
                String qtde = etQtde.getText().toString();

                if (qtde == null || qtde.equals("")) {
                    _util.showMensagem("Informe a quantidade do item.");
                    return;
                }

                adicionaItemPedido(Double.valueOf(qtde));
                _comanda.setQtdeItem(Double.valueOf(qtde));
                EnviaComanda(getApplicationContext(), ListaItensPedido.class, _comanda);

                break;
        }
    }

    private void adicionaItemPedido(double qtde)
    {
        PedidoItem novoItem = new PedidoItem(_comanda.getIdPedido(), 0, _produto.getIdProduto(), qtde, _produto.getPrecoVenda(), _produto);
        _pedidosHelper.AdicionaPedidoItem(novoItem);
    }
}
