package com.example.foodexpress.bancodados.schema;

/**
 * Created by jose.santos on 07/10/2014.
 */
public final class PedidoItemSchema {

    // Estrutura
    public static final String TABLE_NAME = "pedido_item";
    public static final String KEY_ID = "id";
    public static final String KEY_PEDIDO_ID = "pedido_id";
    public static final String KEY_PRODUTO_ID = "produto_id";
    public static final String KEY_QTDE = "qtde";
    public static final String KEY_VLR_UNIT = "vlr_unit";

    // Querys
    public static String getQueryConsultaPedidoItemPorIdPedido(long id) {
        String str = "SELECT "
                + KEY_ID + ", "
                + KEY_PEDIDO_ID + ", "
                + KEY_PRODUTO_ID + ", "
                + KEY_QTDE + ", "
                + KEY_VLR_UNIT
                + " FROM "
                + TABLE_NAME
                + " WHERE "
                + KEY_PEDIDO_ID + " = %d";

        final String query = String.format(str, id);
        return query;
    }

    public static String getQueryDeletaPedidoItensPorIdPedido(long id){
        String str = "DELETE "
                + "FROM "
                + TABLE_NAME
                + " WHERE "
                + KEY_ID + " = %d";

        final String query = String.format(str, id);
        return query;
    }
}
