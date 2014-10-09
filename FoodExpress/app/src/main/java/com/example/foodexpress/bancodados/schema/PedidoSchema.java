package com.example.foodexpress.bancodados.schema;

/**
 * Created by jose.santos on 07/10/2014.
 */
public final class PedidoSchema {

    // Estrutura
    public static final String TABLE_NAME = "Pedido";
    public static final String KEY_ID = "id";
    public static final String KEY_DATE_TIME_ISSUE = "data_hora_emissao";
    public static final String KEY_STATUS_PEDIDO = "status_pedido";
    private static final String KEY_DESCRICAO_STATUS = String.format("CASE WHEN %s = 0 THEN 'Aberto' WHEN %s = 1 THEN 'Fechado' WHEN %s = 2 THEN 'Producao' WHEN %s = 3 THEN 'Transito' WHEN %s = 4 THEN 'Entregue' END AS descricao_status ", KEY_STATUS_PEDIDO, KEY_STATUS_PEDIDO, KEY_STATUS_PEDIDO, KEY_STATUS_PEDIDO, KEY_STATUS_PEDIDO);

    // Querys
    public static String getQueryConsultaPedidos() {
        String query = "SELECT "
                + KEY_ID + ", "
                + KEY_DATE_TIME_ISSUE + ", "
                + KEY_STATUS_PEDIDO + ", "
                + KEY_DESCRICAO_STATUS
                + " FROM "
                + TABLE_NAME;

        return query;
    }

    public static String getQueryConsultaPedidosPorId(long id) {
        String str = "SELECT "
                + KEY_ID + ", "
                + KEY_DATE_TIME_ISSUE + ", "
                + KEY_STATUS_PEDIDO + ", "
                + KEY_DESCRICAO_STATUS
                + " FROM "
                + TABLE_NAME
                + " WHERE "
                + KEY_ID + " = %d";

        final String query = String.format(str, id);
        return query;
    }

    public static String getQueryConsultaPedidosPorStatus(int status) {
        String str = "SELECT "
                + KEY_ID + ", "
                + KEY_DATE_TIME_ISSUE + ", "
                + KEY_STATUS_PEDIDO + ", "
                + KEY_DESCRICAO_STATUS
                + " FROM "
                + TABLE_NAME
                + " WHERE "
                + KEY_STATUS_PEDIDO + " = %d";

        final String query = String.format(str, status);
        return query;
    }
}