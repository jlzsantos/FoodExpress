package com.example.foodexpress.pedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.PedidoItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by JL on 27/09/2014.
 */
public class ListViewAdapterPedidosItens extends BaseAdapter {
    private static ArrayList<PedidoItem> pedidoItens;
    private LayoutInflater inflater;

    public ListViewAdapterPedidosItens(Context contexto, ArrayList<PedidoItem> itens) {
        this.pedidoItens = itens;
        this.inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return pedidoItens.size();
    }

    @Override
    public Object getItem(int posicao) {
        return pedidoItens.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return pedidoItens.get(posicao).getIdPedidoItem();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        PedidoItem item = (PedidoItem)getItem(posicao);
        view = inflater.inflate(R.layout.lista_pedido_item, null);

        TextView idProduto = (TextView)view.findViewById(R.id.idproduto);
        idProduto.setText(String.valueOf(item.getIdProduto()));

        TextView idPedidoItem = (TextView)view.findViewById(R.id.idpedidoitem);
        idPedidoItem.setText(String.valueOf(item.getIdPedidoItem()));

        TextView descricao = (TextView)view.findViewById(R.id.descricaoproduto);
        descricao.setText(item.getProduto().getDescricaoProduto());

        TextView precoVenda = (TextView)view.findViewById(R.id.precovenda_qtde);
        String preco_format = String.format("%.2f", item.getProduto().getPrecoVenda()); //String.valueOf(produto.getPrecoVenda());
        //preco_format = "R$ " + preco_format.replace(".", ",");
        //precoVenda.setText(preco_format);
        preco_format = preco_format.replace(".", ",");
        DecimalFormat df = new DecimalFormat("0.##");
        precoVenda.setText(preco_format + "x" + df.format(item.getQtde()));

        TextView precoTotal = (TextView)view.findViewById(R.id.precototal);
        String precoTotal_format = String.format("%.2f", item.getVlrTotal());
        precoTotal_format = "R$ " + precoTotal_format.replace(".", ",");
        precoTotal.setText(precoTotal_format);

        return view;
    }
}
