package com.example.foodexpress.cardapio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.PedidoItem;
import com.example.foodexpress.entidades.Produto;
import com.example.foodexpress.pedidos.ListaItensPedido;
import com.example.foodexpress.principal.ActivityBase;


public class CardapioDetalhe extends ActivityBase implements View.OnClickListener {

    private static final int request_code = 5;
    private Button btnCancelar;
    private Button btnAdicionar;
    private TextView tvIngredientes;
    private TextView tvProduto;
    private EditText etQtde;
    private ImageView imgProduto;
    private int idProduto;
    private Produto produto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_detalhe);

        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        btnAdicionar = (Button)findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(this);

        imgProduto = (ImageView)findViewById(R.id.imgProduto);
        tvIngredientes = (TextView)findViewById(R.id.tvIngredientes);
        tvProduto = (TextView)findViewById(R.id.tvProduto);
        etQtde = (EditText)findViewById(R.id.etQtde);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível exibir o produto.");
            dlg.show();
            return;
        }

        //String strIdProduto = extras.getString("idProduto");
        produto = (Produto)extras.getSerializable("Produto");
        idProduto = produto.getIdProduto(); //Integer.parseInt(strIdProduto);

        super.setTitle(produto.getdescricaoProduto());
        tvIngredientes.setText(produto.getIngredientes());

        String preco_format = String.format("%.2f", produto.getPrecoVenda());
        preco_format = "R$ " + preco_format.replace(".", ",");
        tvProduto.setText(produto.getdescricaoProduto() + " - " + preco_format);

        if (idProduto >= 1 && idProduto <= 20) {
            imgProduto.setImageResource(R.drawable.pizza_144x144);
        } else if (idProduto >= 21 && idProduto <= 30) {
            imgProduto.setImageResource(R.drawable.ic_foodexpress);
        } else if (idProduto >= 31 && idProduto <= 40) {
            imgProduto.setImageResource(R.drawable.ic_foodexpress);
        } else if (idProduto >= 41 && idProduto <= 50) {
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
        //Intent pConstrucao = new Intent(getApplicationContext(), EmConstrucao.class);
        //Intent itensPedido = new Intent(getApplicationContext(), ListaItensPedido.class);

        int idControle = view.getId();

        switch (idControle){
            case R.id.btnCancelar:
                finish();
                break;

            case R.id.btnAdicionar:

                /*
                Intent i = new Intent(getApplicationContext(), ListaItensPedido.class);
                i.putExtra("Produto", produto);
                */

                String qtde = etQtde.getText().toString();

                if (qtde == null || qtde.equals("")) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("FoodExpress");
                    dlg.setNeutralButton("Ok", null);
                    dlg.setMessage("Informe a quantidade do item.");
                    dlg.show();
                    return;
                }

                PedidoItem item = new PedidoItem();
                item.setQtde(Float.valueOf(qtde));
                item.setVlrUnit(produto.getPrecoVenda());
                item.setProduto(produto);

                Intent i = new Intent(getApplicationContext(), ListaItensPedido.class);
                i.putExtra("Item", (java.io.Serializable) item);

                startActivityForResult(i, request_code);

                //startActivity(itensPedido);
                break;
        }
    }

    @Override
    public void finish(){
        setResult(RESULT_CANCELED);
        super.finish();
    }

}
