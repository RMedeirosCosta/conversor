package br.edu.utfpr.conversor.infraestrutura;

import android.provider.BaseColumns;

/**
 * Created by ricardo on 07/03/15.
 */
public class ConversaoContract {

    public ConversaoContract() {}

    public static abstract class Conversao implements BaseColumns {
        public static final String TABLE_NAME = "conversao";
        public static final String COLUMN_NAME_CONVERSAO_ID = "id";
        public static final String COLUMN_NAME_BINARIO = "binario";
        public static final String COLUMN_NAME_OCTAL = "octal";
        public static final String COLUMN_NAME_DECIMAL = "decimal";
        public static final String COLUMN_NAME_HEXADECIMAL = "hexadecimal";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Conversao.TABLE_NAME + " (" +
                    Conversao.COLUMN_NAME_CONVERSAO_ID + " INTEGER PRIMARY KEY," +
                    Conversao.COLUMN_NAME_BINARIO + TEXT_TYPE + COMMA_SEP +
                    Conversao.COLUMN_NAME_OCTAL + TEXT_TYPE + COMMA_SEP +
                    Conversao.COLUMN_NAME_DECIMAL + TEXT_TYPE + COMMA_SEP +
                    Conversao.COLUMN_NAME_HEXADECIMAL + TEXT_TYPE +
            " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Conversao.TABLE_NAME;
}
