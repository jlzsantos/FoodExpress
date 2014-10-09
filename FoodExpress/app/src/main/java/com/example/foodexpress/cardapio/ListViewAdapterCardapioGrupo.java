package com.example.foodexpress.cardapio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.ProdutoGrupo;

import java.util.ArrayList;

public class ListViewAdapterCardapioGrupo extends BaseAdapter {
    private static ArrayList<ProdutoGrupo> produtoGrupoLista;
    private LayoutInflater inflater;

    public ListViewAdapterCardapioGrupo(Context contexto, ArrayList<ProdutoGrupo> lista){
        this.produtoGrupoLista = lista;
        this.inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return produtoGrupoLista.size();
    }

    @Override
    public Object getItem(int posicao) {
        return produtoGrupoLista.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return produtoGrupoLista.get(posicao).getIdProdutoGrupo();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        ProdutoGrupo grupo = (ProdutoGrupo)getItem(posicao);
        view = inflater.inflate(R.layout.lista_produtogrupo_item, null);

        TextView idGrupo = (TextView)view.findViewById(R.id.idprodutogrupo);
        idGrupo.setText(String.valueOf(grupo.getIdProdutoGrupo()));

        TextView descricao = (TextView)view.findViewById(R.id.descricaoprodutogrupo);
        descricao.setText(grupo.getDescricaoGrupo());

        return view;
    }
}
