package com.example.foodexpress.principal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.foodexpress.bancodados.ProdutoHelper;
import com.example.foodexpress.cardapio.CardapioGrupo;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.entidades.Produto;
import com.example.foodexpress.entidades.ProdutoGrupo;
import com.example.foodexpress.pedidos.ListaPedidos;
import com.example.foodexpress.utils.Util;

import java.util.ArrayList;


public class Main extends ActivityBase implements View.OnClickListener {

    private static final int request_code = 5;
    private Button btnCardapio;
    private Button btnPedidos;
    private Button btnEnderecos;
    private Button btnFechar;
    private Comanda _comanda;
    private final boolean _sincronizarBancoExterno = true;
    private ProdutoHelper _produtoHelper;
    private Util _util;

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

        _comanda = new Comanda();
        _produtoHelper = new ProdutoHelper(getApplicationContext());
        _util = new Util(getApplicationContext());
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
                sincronizaComBancoExterno();
                Intent iCardapioGrupo = new Intent(getApplicationContext(), CardapioGrupo.class);
                iCardapioGrupo.putExtra("Comanda", _comanda);
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

    private void sincronizaComBancoExterno(){
        if (_sincronizarBancoExterno){
            try {
                long idGrupo;
                //_util.RetornaSimpleDialog("Iniciando sincronização...").show();

                // Sincroniza Grupos
                _produtoHelper.RemoveGruposAll();
                ArrayList<ProdutoGrupo> grupos = new ArrayList<ProdutoGrupo>();
                grupos.add(new ProdutoGrupo(1, "Pizzas"));
                grupos.add(new ProdutoGrupo(2, "Massas"));
                grupos.add(new ProdutoGrupo(3, "Sobremesas"));
                grupos.add(new ProdutoGrupo(4, "Bebidas"));

                _produtoHelper.AdicionaProdutoGrupos(grupos);

                // Sincroniza Produtos
                _produtoHelper.RemoveProdutosAll();
                ArrayList<Produto> produtos = new ArrayList<Produto>();

                idGrupo = 1; // Pizzas
                produtos.add(new Produto(1, idGrupo, "Maguerita", 32.00f, "(Maguerita) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(2, idGrupo, "Quatro Queijos", 29.50f, "(Quatro Queijos) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(3, idGrupo, "Escarola", 33.00f, "(Escarola) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(4, idGrupo, "Calabreza", 31.80f, "(Calabreza) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(5, idGrupo, "Mussarela", 29.00f, "(Mussarela) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(6, idGrupo, "Mineira", 34.00f, "(Mineira) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(7, idGrupo, "Portuguesa", 33.50f, "(Portuguesa) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(8, idGrupo, "Paulista", 30.00f, "(Paulista) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(9, idGrupo, "Moda da Casa", 38.00f, "(Moda da Casa) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(10, idGrupo, "Atum", 35.00f, "(Atum) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(11, idGrupo, "Alho e Óleo", 34.00f, "(Alho e Óleo) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(12, idGrupo, "Baiana", 37.00f, "(Baiana) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(13, idGrupo, "Bolonhesa", 39.00f, "(Bolonhesa) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(14, idGrupo, "Brócolis", 33.00f, "(Brócolis) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(15, idGrupo, "Catupiry", 33.00f, "(Catupiry) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(16, idGrupo, "Frango", 35.00f, "(Frango) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(17, idGrupo, "Gorgonzola", 36.00f, "(Gorgonzola) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(18, idGrupo, "Havaiana", 34.00f, "(Havaiana) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(19, idGrupo, "Lombo", 38.00f, "(Lombo) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));
                produtos.add(new Produto(20, idGrupo, "Mexicana", 37.00f, "(Mexicana) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão"));

                idGrupo = 2; // Massas
                produtos.add(new Produto(21, idGrupo, "Pene", 25.00f, "(Pene) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(22, idGrupo, "Spaghetti", 29.00f, "(Spaghetti) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(23, idGrupo, "Fettuccine", 36.00f, "(Fettuccine) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(24, idGrupo, "Farfalle", 41.00f, "(Farfalle) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(25, idGrupo, "Fusilli Integrale", 45.00f, "(Fusilli Integrale) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(26, idGrupo, "Cappelletti de Frango", 37.00f, "(Cappelletti de Frango) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(27, idGrupo, "Cappelletti de Carne", 42.00f, "(Cappelletti de Carne) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(28, idGrupo, "Gnocchi", 40.00f, "(Gnocchi) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(29, idGrupo, "Ravioli de Ricota", 39.00f, "(Ravioli de Ricota) Farinha de trigo, água, sal, molho de tomate..."));
                produtos.add(new Produto(30, idGrupo, "Ravioli de Picanha", 55.00f, "(Ravioli de Picanha) Farinha de trigo, água, sal, molho de tomate..."));

                idGrupo = 3; // Sobremesas
                produtos.add(new Produto(31, idGrupo, "Flocos de Suspiro", 55.00f, "(Flocos de Suspiro) Açúcar refinado, água, ..."));
                produtos.add(new Produto(32, idGrupo, "Mousse Argentina", 56.00f, "(Mousse Argentina) Açúcar refinado, água, ..."));
                produtos.add(new Produto(33, idGrupo, "Mousse de Chocolate", 48.00f, "(Mousse de Chocolate) Açúcar refinado, água, chocolate, ..."));
                produtos.add(new Produto(34, idGrupo, "Mousse de Maracujá", 60.00f, "(Mousse de Maracujá) Açúcar refinado, água, maracujá ..."));
                produtos.add(new Produto(35, idGrupo, "Napoleon de Banana", 86.00f, "(Napoleon de Banana) Açúcar refinado, água, banana, ..."));
                produtos.add(new Produto(36, idGrupo, "Pudim Leite Condensado", 40.00f, "(Pudim Leite Condensado) Açúcar refinado, água, leite condensado, ..."));
                produtos.add(new Produto(37, idGrupo, "Rocambole de Chocolate", 72.00f, "(Rocambole de Chocolate) Açúcar refinado, água, chocolate, ..."));
                produtos.add(new Produto(38, idGrupo, "Strogonoff de Chocolate", 40.00f, "(Strogonoff de Chocolate) Açúcar refinado, água, chocolate, ..."));

                idGrupo = 4; // Bebidas
                produtos.add(new Produto(41, idGrupo, "Água M.c/gás 330ml", 3.00f, "Somente água!"));
                produtos.add(new Produto(42, idGrupo, "Água M.s/gás 330ml", 3.00f, "Somente água!"));
                produtos.add(new Produto(43, idGrupo, "Coca-Cola ligth 2L", 6.50f, "(Coca-Cola ligth 2L) Água gaseificada, corantes, ..."));
                produtos.add(new Produto(44, idGrupo, "Coca-Cola 2L", 6.00f, "(Coca-Cola 2L) Água gaseificada, corantes, ..."));
                produtos.add(new Produto(45, idGrupo, "Fanta 2L", 5.00f, "(Fanta 2L) Água gaseificada, corantes, ..."));
                produtos.add(new Produto(46, idGrupo, "Fanta Uva 2L", 5.00f, "(Fanta Uva 2L) Água gaseificada, corantes, ..."));
                produtos.add(new Produto(47, idGrupo, "Sprite 2L", 5.20f, "(Sprite 2L) Água gaseificada, corantes, ..."));
                produtos.add(new Produto(48, idGrupo, "Sprite ligth 2L", 5.20f, "(Sprite ligth 2L) Água gaseificada, corantes, ..."));

                _produtoHelper.AdicionaProdutos(produtos);

            } catch (Exception ex) {
                //AlertDialog.Builder dlg = _util.RetornaSimpleDialog("Não foi possível sincronizar com o banco de dados externo: " + ex.getMessage());

                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("FoodExpress");
                dlg.setNeutralButton("Ok", null);
                dlg.setMessage("Não foi possível sincronizar com o banco de dados externo: " + ex.getMessage());
                dlg.show();
            }
        }
    }
}
