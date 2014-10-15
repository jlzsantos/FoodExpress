package com.example.foodexpress.cardapio;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.entidades.Produto;
import com.example.foodexpress.principal.ActivityBase;

import java.util.ArrayList;

public class CardapioProduto extends ActivityBase implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int request_code = 5;
    private int _idGrupo;
    private Button btnCancelar;
    private ListView _listaCardapioProduto;
    private ArrayList<Produto> _listaProdutos;
    private Comanda _comanda;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_produto);

        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível listar os produtos.");
            dlg.show();
            return;
        }

        _comanda = RetornaComanda();
        _idGrupo = _comanda.getIdGrupo();

        _listaProdutos = new ArrayList<Produto>();

        switch (_idGrupo){
            case 1:     // Pizzas
                _listaProdutos = RetornaListaPizzas(1);
                super.setTitle("Pizzas");
                break;

            case 2:     // Massas
                _listaProdutos = RetornaListaMassas(2);
                super.setTitle("Massas");
                break;

            case 3:     // Sobremesas
                _listaProdutos = RetornaListaSobremesas(3);
                super.setTitle("Sobremesas");
                break;

            case 4:     // Bebidas
                _listaProdutos = RetornaListaBebidas(4);
                super.setTitle("Bebidas");
                break;
        }

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

        TextView tvIdProduto = (TextView)view.findViewById(R.id.idproduto);
        //String idProduto = tvIdProduto.getText().toString();

        Produto produto = (Produto)adapterView.getItemAtPosition(posicao);
        _comanda.setProduto(produto);
        EnviaComanda(getApplicationContext(), CardapioDetalhe.class, _comanda);

        /*
        Intent i = new Intent(getApplicationContext(), CardapioDetalhe.class);
        //i.putExtra("idProduto", idProduto);
        i.putExtra("Produto", produto);
        startActivityForResult(i, request_code);
        */
    }

    @Override
    public void finish(){
        setResult(RESULT_CANCELED);
        super.finish();
    }

    private ArrayList<Produto> RetornaListaPizzas(long idGrupo)
    {
        ArrayList<Produto> listaPizzas = new ArrayList<Produto>();

        listaPizzas.add(new Produto(1, idGrupo, "Maguerita", 32.00f, "(Maguerita) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(2, idGrupo, "Quatro Queijos", 29.50f, "(Quatro Queijos) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(3, idGrupo, "Escarola", 33.00f, "(Escarola) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(4, idGrupo, "Calabreza", 31.80f, "(Calabreza) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(5, idGrupo, "Mussarela", 29.00f, "(Mussarela) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(6, idGrupo, "Mineira", 34.00f, "(Mineira) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(7, idGrupo, "Portuguesa", 33.50f, "(Portuguesa) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(8, idGrupo, "Paulista", 30.00f, "(Paulista) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(9, idGrupo, "Moda da Casa", 38.00f, "(Moda da Casa) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(10, idGrupo, "Atum", 35.00f, "(Atum) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(11, idGrupo, "Alho e Óleo", 34.00f, "(Alho e Óleo) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(12, idGrupo, "Baiana", 37.00f, "(Baiana) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(13, idGrupo, "Bolonhesa", 39.00f, "(Bolonhesa) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(14, idGrupo, "Brócolis", 33.00f, "(Brócolis) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(15, idGrupo, "Catupiry", 33.00f, "(Catupiry) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(16, idGrupo, "Frango", 35.00f, "(Frango) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(17, idGrupo, "Gorgonzola", 36.00f, "(Gorgonzola) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(18, idGrupo, "Havaiana", 34.00f, "(Havaiana) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(19, idGrupo, "Lombo", 38.00f, "(Lombo) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
        listaPizzas.add(new Produto(20, idGrupo, "Mexicana", 37.00f, "(Mexicana) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));

        return listaPizzas;
    }

    private ArrayList<Produto> RetornaListaMassas(long idGrupo)
    {
        ArrayList<Produto> listaMassas = new ArrayList<Produto>();

        listaMassas.add(new Produto(21, idGrupo, "Pene", 25.00f, "(Pene) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(22, idGrupo, "Spaghetti", 29.00f, "(Spaghetti) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(23, idGrupo, "Fettuccine", 36.00f, "(Fettuccine) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(24, idGrupo, "Farfalle", 41.00f, "(Farfalle) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(25, idGrupo, "Fusilli Integrale", 45.00f, "(Fusilli Integrale) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(26, idGrupo, "Cappelletti de Frango", 37.00f, "(Cappelletti de Frango) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(27, idGrupo, "Cappelletti de Carne", 42.00f, "(Cappelletti de Carne) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(28, idGrupo, "Gnocchi", 40.00f, "(Gnocchi) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(29, idGrupo, "Ravioli de Ricota", 39.00f, "(Ravioli de Ricota) Farinha de trigo, água, sal, molho de tomate..."));
        listaMassas.add(new Produto(30, idGrupo, "Ravioli de Picanha", 55.00f, "(Ravioli de Picanha) Farinha de trigo, água, sal, molho de tomate..."));

        return listaMassas;
    }

    private ArrayList<Produto> RetornaListaSobremesas(long idGrupo)
    {
        ArrayList<Produto> listaSobremesas = new ArrayList<Produto>();

        listaSobremesas.add(new Produto(31, idGrupo, "Flocos de Suspiro", 55.00f, "(Flocos de Suspiro) Açúcar refinado, água, ..."));
        listaSobremesas.add(new Produto(32, idGrupo, "Mousse Argentina", 56.00f, "(Mousse Argentina) Açúcar refinado, água, ..."));
        listaSobremesas.add(new Produto(33, idGrupo, "Mousse de Chocolate", 48.00f, "(Mousse de Chocolate) Açúcar refinado, água, chocolate, ..."));
        listaSobremesas.add(new Produto(34, idGrupo, "Mousse de Maracujá", 60.00f, "(Mousse de Maracujá) Açúcar refinado, água, maracujá ..."));
        listaSobremesas.add(new Produto(35, idGrupo, "Napoleon de Banana", 86.00f, "(Napoleon de Banana) Açúcar refinado, água, banana, ..."));
        listaSobremesas.add(new Produto(36, idGrupo, "Pudim Leite Condensado", 40.00f, "(Pudim Leite Condensado) Açúcar refinado, água, leite condensado, ..."));
        listaSobremesas.add(new Produto(37, idGrupo, "Rocambole de Chocolate", 72.00f, "(Rocambole de Chocolate) Açúcar refinado, água, chocolate, ..."));
        listaSobremesas.add(new Produto(38, idGrupo, "Strogonoff de Chocolate", 40.00f, "(Strogonoff de Chocolate) Açúcar refinado, água, chocolate, ..."));

        return listaSobremesas;
    }

    private ArrayList<Produto> RetornaListaBebidas(long idGrupo)
    {
        ArrayList<Produto> listaBebidas = new ArrayList<Produto>();

        listaBebidas.add(new Produto(41, idGrupo, "Água M.c/gás 330ml", 3.00f, "Somente água!"));
        listaBebidas.add(new Produto(42, idGrupo, "Água M.s/gás 330ml", 3.00f, "Somente água!"));
        listaBebidas.add(new Produto(43, idGrupo, "Coca-Cola ligth 2L", 6.50f, "(Coca-Cola ligth 2L) Água gaseificada, corantes, ..."));
        listaBebidas.add(new Produto(44, idGrupo, "Coca-Cola 2L", 6.00f, "(Coca-Cola 2L) Água gaseificada, corantes, ..."));
        listaBebidas.add(new Produto(45, idGrupo, "Fanta 2L", 5.00f, "(Fanta 2L) Água gaseificada, corantes, ..."));
        listaBebidas.add(new Produto(46, idGrupo, "Fanta Uva 2L", 5.00f, "(Fanta Uva 2L) Água gaseificada, corantes, ..."));
        listaBebidas.add(new Produto(47, idGrupo, "Sprite 2L", 5.20f, "(Sprite 2L) Água gaseificada, corantes, ..."));
        listaBebidas.add(new Produto(48, idGrupo, "Sprite ligth 2L", 5.20f, "(Sprite ligth 2L) Água gaseificada, corantes, ..."));

        return listaBebidas;
    }
}
