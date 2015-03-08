package br.edu.utfpr.conversor.infraestrutura;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import br.edu.utfpr.conversor.dominio.Conversao;

/**
 * Created by ricardo on 07/03/15.
 */
public class ConversaoDAO {

    private ConversorHelper helper;

    public ConversaoDAO(ConversorHelper helper) {
        this.helper = helper;
    }

    public void salvar(Conversao conversao) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (conversao.getId() > 0)
            values.put(ConversaoContract.Conversao.COLUMN_NAME_CONVERSAO_ID, conversao.getId());

        values.put(ConversaoContract.Conversao.COLUMN_NAME_BINARIO, conversao.getBinario());
        values.put(ConversaoContract.Conversao.COLUMN_NAME_OCTAL, conversao.getOctal());
        values.put(ConversaoContract.Conversao.COLUMN_NAME_DECIMAL, conversao.getDecimal());
        values.put(ConversaoContract.Conversao.COLUMN_NAME_HEXADECIMAL, conversao.getHexadecimal());

        conversao.setId(db.insert(ConversaoContract.Conversao.TABLE_NAME, null, values));
    }

    private String[] getProjection() {
        String[] projection = {
                ConversaoContract.Conversao.COLUMN_NAME_CONVERSAO_ID,
                ConversaoContract.Conversao.COLUMN_NAME_BINARIO,
                ConversaoContract.Conversao.COLUMN_NAME_OCTAL,
                ConversaoContract.Conversao.COLUMN_NAME_DECIMAL,
                ConversaoContract.Conversao.COLUMN_NAME_HEXADECIMAL
        };

        return projection;
    }

    public Conversao obter(String decimal) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sortOrder =
                ConversaoContract.Conversao.COLUMN_NAME_CONVERSAO_ID + " ASC";

        String selection = ConversaoContract.Conversao.COLUMN_NAME_DECIMAL+" = ?";
        String[] selectionArgs = { decimal };

        Cursor c = db.query(
                ConversaoContract.Conversao.TABLE_NAME,
                getProjection(),
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Conversao conversao = new Conversao(c.getLong(c.getColumnIndexOrThrow(ConversaoContract.Conversao.COLUMN_NAME_CONVERSAO_ID)),
                c.getString(c.getColumnIndexOrThrow(ConversaoContract.Conversao.COLUMN_NAME_BINARIO)),
                c.getString(c.getColumnIndexOrThrow(ConversaoContract.Conversao.COLUMN_NAME_OCTAL)),
                c.getString(c.getColumnIndexOrThrow(ConversaoContract.Conversao.COLUMN_NAME_DECIMAL)),
                c.getString(c.getColumnIndexOrThrow(ConversaoContract.Conversao.COLUMN_NAME_HEXADECIMAL)));

        return conversao;
    }

}
