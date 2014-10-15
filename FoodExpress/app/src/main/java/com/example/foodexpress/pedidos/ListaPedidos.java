package com.example.foodexpress.pedidos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.foodexpress.bancodados.PedidoHelper;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Pedido;
import com.example.foodexpress.principal.ActivityBase;

import java.util.ArrayList;

public class ListaPedidos extends ActivityBase implements AdapterView.OnItemClickListener, View.OnClickListener {

    private PedidoHelper pedidosHelper;
    private Button btnMenu;
    private ListView listViewPedidos;
    private ArrayList<Pedido> listaPedidos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        pedidosHelper = new PedidoHelper(this);

        btnMenu = (Button)findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);

        listaPedidos = pedidosHelper.RetornaPedidos();
        listViewPedidos = (ListView)findViewById(R.id.lvPedidos);
        listViewPedidos.setAdapter(new ListViewAdapterListaPedidos(this, listaPedidos));
        listViewPedidos.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_pedidos, menu);
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
                super.finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
