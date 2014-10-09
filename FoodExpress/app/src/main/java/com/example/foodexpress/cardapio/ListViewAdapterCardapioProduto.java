package com.example.foodexpress.cardapio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Produto;

import java.util.ArrayList;

public class ListViewAdapterCardapioProduto extends BaseAdapter {
    private static ArrayList<Produto> produtoLista;
    private LayoutInflater inflater;

    public ListViewAdapterCardapioProduto(Context contexto, ArrayList<Produto> produtos){
        this.produtoLista = produtos;
        this.inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return produtoLista.size();
    }

    @Override
    public Object getItem(int posicao) {
        return produtoLista.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return produtoLista.get(posicao).getIdProduto();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        Produto produto = (Produto)getItem(posicao);
        view = inflater.inflate(R.layout.lista_produto_item, null);

        TextView idProduto = (TextView)view.findViewById(R.id.idproduto);
        idProduto.setText(String.valueOf(produto.getIdProduto()));

        TextView descricao = (TextView)view.findViewById(R.id.descricaoproduto);
        descricao.setText(produto.getdescricaoProduto());

        TextView precoVenda = (TextView)view.findViewById(R.id.precovenda);
        String preco_format = String.format("%.2f", produto.getPrecoVenda()); //String.valueOf(produto.getPrecoVenda());
        preco_format = "R$ " + preco_format.replace(".", ",");
        precoVenda.setText(preco_format);

        return view;
    }
}
