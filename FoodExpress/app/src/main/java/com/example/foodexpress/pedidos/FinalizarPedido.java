package com.example.foodexpress.pedidos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.foodexpress.bancodados.PedidoHelper;
import com.example.foodexpress.deliveryfood.R;
import com.example.foodexpress.entidades.Comanda;
import com.example.foodexpress.entidades.Pedido;
import com.example.foodexpress.principal.ActivityBase;
import com.example.foodexpress.principal.Main;
import com.example.foodexpress.utils.Util;

public class FinalizarPedido extends ActivityBase implements View.OnClickListener {

    private RadioButton rbRetiraBalcao;
    private RadioButton rbEntregaDomicilio;
    private Button btnFecharPedido;
    private Button btnCancelar;
    private TextView tvTotalPedido;
    private TextView tvCepInformadoLabel;
    private TextView tvCepInformado;

    private final double _taxaEntrega = 5.00;
    private Pedido _pedido;
    private String _cepInformado;
    private Comanda _comanda;
    private PedidoHelper _pedidoHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido);

        rbRetiraBalcao = (RadioButton)findViewById(R.id.rbRetiraBalcao);
        rbRetiraBalcao.setChecked(false);

        rbEntregaDomicilio = (RadioButton)findViewById(R.id.rbEntregaDomicilio);
        rbEntregaDomicilio.setChecked(false);
        String txEntrega_format = String.format("%.2f", _taxaEntrega);
        txEntrega_format = " (+R$ " + txEntrega_format.replace(".", ",") + ")";
        rbEntregaDomicilio.setText("Entrega domicílio" + txEntrega_format);

        btnFecharPedido = (Button)findViewById(R.id.btnFecharPedido);
        btnFecharPedido.setOnClickListener(this);

        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        tvCepInformadoLabel = (TextView)findViewById(R.id.tvCepInformadoLabel);

        tvCepInformado = (TextView)findViewById(R.id.tvCepInformado);
        tvCepInformado.setText("");

        showCEP(false);

        _pedidoHelper = new PedidoHelper(getApplicationContext());
        _comanda = RetornaComanda();
        _pedido = _pedidoHelper.RetornaPedidoPorId(_comanda.getIdPedido());

        tvTotalPedido = (TextView)findViewById(R.id.tvTotalPedido);
        setTotalPedido(_pedido.getVlrTotalPedido());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.finalizar_pedido, menu);
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

    private void setTotalPedido(double valor){
        String total_format = String.format("%.2f", valor);
        total_format = "R$ " + total_format.replace(".", ",");
        tvTotalPedido.setText(total_format);
    }
    
    private void finalizaPedido() {

        Intent i = new Intent(getApplicationContext(), Main.class);

        if (rbEntregaDomicilio.isChecked() == false && rbRetiraBalcao.isChecked() == false) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Por favor, selecione como deseja receber seu pedido.");
            dlg.show();
            return;
        }

        if (rbRetiraBalcao.isChecked()) {
            alteraStatusPedido();
            showMensagemRetornaMenu("Pedido finalizado com sucesso. Acompanhe seu pedido através da opção Pedidos no menu.", i);
        } else if (rbEntregaDomicilio.isChecked()) {
            if (_cepInformado.equals("")) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("FoodExpress");
                dlg.setNeutralButton("Ok", null);
                dlg.setMessage("Informe o CEP para entrega");
                dlg.show();
                getCEP();
            } else {
                alteraStatusPedido();
                showMensagemRetornaMenu("Pedido finalizado com sucesso. Acompanhe seu pedido através da opção Pedidos no menu.", i);
            }
        }
    }

    private void alteraStatusPedido() {
        _pedidoHelper.AlteraPedidoStatus(_pedido.getIdPedido(), 1);  // Fechado
    }

    private void getCEP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText text = new EditText(this);
        text.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setTitle("FoodExpress").setMessage("Por favor, informe o seu CEP.").setView(text);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                final String cep = text.getText().toString();
                _cepInformado = cep;
                showCEP(true);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
                _cepInformado = "";
                showCEP(false);
                rbRetiraBalcao.setChecked(true);
                rbEntregaDomicilio.setChecked(false);
                setTotalPedido(_pedido.getVlrTotalPedido());
            }
        });
        builder.create().show();
    }

    private void showCEP(boolean visible) {
        if (visible) {
            tvCepInformadoLabel.setVisibility(View.VISIBLE);
            tvCepInformado.setVisibility(View.VISIBLE);
            tvCepInformado.setText(_cepInformado);
        } else {
            tvCepInformadoLabel.setVisibility(View.INVISIBLE);
            tvCepInformado.setVisibility(View.INVISIBLE);
            tvCepInformado.setText("");
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbRetiraBalcao:
                if (checked) {
                    rbEntregaDomicilio.setChecked(false);
                    setTotalPedido(_pedido.getVlrTotalPedido());
                    showCEP(false);
                }
                break;
            case R.id.rbEntregaDomicilio:
                if (checked) {
                    rbRetiraBalcao.setChecked(false);
                    setTotalPedido(_pedido.getVlrTotalPedido() + _taxaEntrega);
                    getCEP();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {

        int idControle = view.getId();

        switch (idControle){
            case R.id.btnCancelar:
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);
                finishAll("com.example.foodexpress.principal.Main");
                break;

            case R.id.btnFecharPedido:
                finalizaPedido();
                break;
        }
    }
}
