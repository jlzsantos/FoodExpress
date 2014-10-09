package com.example.foodexpress.pedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Pedido;

import java.util.ArrayList;

/**
 * Created by jose.santos on 08/10/2014.
 */
public class ListViewAdapterListaPedidos extends BaseAdapter {
    private static ArrayList<Pedido> pedidos;
    private LayoutInflater inflater;

    public ListViewAdapterListaPedidos(Context contexto, ArrayList<Pedido> listaPedidos) {
        this.pedidos = listaPedidos;
        this.inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int posicao) {
        return pedidos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return pedidos.get(posicao).getIdPedido();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        Pedido itemLista = (Pedido)getItem(posicao);
        view = inflater.inflate(R.layout.lista_listapedidos_item, null);

        TextView idPedido = (TextView)view.findViewById(R.id.idpedido);
        idPedido.setText(String.valueOf(itemLista.getIdPedido()));

        TextView dataHora = (TextView)view.findViewById(R.id.datahoraemissao);
        dataHora.setText(itemLista.getDataHoraEmissaoString());

        TextView status = (TextView)view.findViewById(R.id.descricaostatus);
        status.setText(itemLista.getDescricaoStatus());

        return view;
    }
}
