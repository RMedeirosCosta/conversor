package br.edu.utfpr.conversor;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import br.edu.utfpr.conversor.dominio.Conversao;
import br.edu.utfpr.conversor.infraestrutura.ConversorHelper;
import br.edu.utfpr.conversor.infraestrutura.ConversaoDAO;

import java.util.regex.Pattern;

public class MainActivity extends ActionBarActivity {

    private EditText edtBinario;
    private TextView txtBinario;

    private EditText edtOctal;
    private TextView txtOctal;

    private EditText edtDecimal;
    private TextView txtDecimal;

    private EditText edtHexadecimal;
    private TextView txtHexadecimal;

    private Button btnArmazenar;

    private TextWatcher listenerBinario;
    private TextWatcher listenerOctal;
    private TextWatcher listenerDecimal;
    private TextWatcher listenerHexadecimal;

    private ConversorHelper helper;

    private static final String BINARIO = "[0-1]";
    private static final String OCTAL = "[0-7]";
    private static final String DECIMAL = "\\d";
    private static final String HEXADECIMAL = "[a-fA-F0-9]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new ConversorHelper(getApplicationContext());
        setContentView(R.layout.activity_main);
        inicializarComponentes();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void inicializarComponentes() {
        edtBinario     = (EditText)findViewById(R.id.edtBinario);
        txtBinario = (TextView)findViewById(R.id.txtBinario);
        edtOctal = (EditText)findViewById(R.id.edtOctal);
        txtOctal = (TextView)findViewById(R.id.txtOctal);
        edtDecimal     = (EditText)findViewById(R.id.edtDecimal);
        txtDecimal = (TextView)findViewById(R.id.txtDecimal);
        edtHexadecimal = (EditText)findViewById(R.id.edtHexadecimal);
        txtHexadecimal = (TextView)findViewById(R.id.txtHexadecimal);
        btnArmazenar = (Button)findViewById(R.id.btnArmazenar);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        listenerBinario = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                verificarTexto(BINARIO, s);
            }
        };

        listenerOctal = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                verificarTexto(OCTAL, s);
            }
        };

        listenerDecimal = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                verificarTexto(DECIMAL, s);
            }
        };

        listenerHexadecimal = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String ultimoCaractere;

                    try {
                        ultimoCaractere = s.toString().substring(s.toString().length() - 1);
                    } catch (StringIndexOutOfBoundsException e) {
                        desabilitarTodosEventosAlteracao();
                        alterarTodos(0);
                        habilitarTodosEventosAlteracao();
                        return;
                    }

                    if (!Pattern.matches(HEXADECIMAL, ultimoCaractere)) {
                        desabilitarEventoAlteracao(edtHexadecimal);
                        edtHexadecimal.setText(edtHexadecimal.getText().subSequence(0, edtHexadecimal.getText().length() - 1));
                        edtHexadecimal.setSelection(edtHexadecimal.length());
                        habilitarEventoAlteracao(edtHexadecimal);
                    } else {
                        desabilitarTodosEventosAlteracao();
                        Integer decimal = Integer.parseInt(edtHexadecimal.getText().toString(), 16);
                        alterarTodos(decimal);
                        habilitarTodosEventosAlteracao();
                    }
                } catch(Exception e) {
                    desabilitarEventoAlteracao(edtHexadecimal);
                    edtHexadecimal.setText("");
                    habilitarEventoAlteracao(edtHexadecimal);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
                //verificarTexto(HEXADECIMAL, s);
            }
        };

        btnArmazenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    ConversaoDAO dao = new ConversaoDAO(helper);
                    Conversao conversao = dao.obter(edtDecimal.getText().toString());

                    if (conversao == null) {
                        conversao = new Conversao(edtBinario.getText().toString(),
                                edtOctal.getText().toString(),
                                edtDecimal.getText().toString(),
                                edtHexadecimal.getText().toString());
                    }

                    dao.salvar(conversao);
                    builder.setTitle(R.string.sucesso);
                    builder.setMessage(R.string.operacao_sucesso);
                    builder.show();

                } catch (IllegalArgumentException ex) {
                    builder.setIcon(R.drawable.ic_launcher);
                    builder.setTitle(R.string.atencao);
                    builder.setMessage("Não há dados para armazenar");
                    builder.show();
                }
            }
        });

        txtBinario.setTextColor(Color.BLUE);
        edtBinario.setTextColor(Color.BLUE);
        edtOctal.setTextColor(Color.BLACK);
        txtOctal.setTextColor(Color.BLACK);
        edtDecimal.setTextColor(Color.MAGENTA);
        txtDecimal.setTextColor(Color.MAGENTA);
        edtHexadecimal.setTextColor(Color.RED);
        txtHexadecimal.setTextColor(Color.RED);
        btnArmazenar.setBackgroundColor(Color.DKGRAY);
        btnArmazenar.setTextColor(Color.GRAY);

        habilitarTodosEventosAlteracao();
    }

    private void desabilitarTodosEventosAlteracao() {
        desabilitarEventoAlteracao(edtBinario);
        desabilitarEventoAlteracao(edtOctal);
        desabilitarEventoAlteracao(edtDecimal);
        desabilitarEventoAlteracao(edtHexadecimal);
    }

    private void habilitarTodosEventosAlteracao() {
        habilitarEventoAlteracao(edtBinario);
        habilitarEventoAlteracao(edtOctal);
        habilitarEventoAlteracao(edtDecimal);
        habilitarEventoAlteracao(edtHexadecimal);
    }

    private void habilitarEventoAlteracao(EditText e) {
        switch (e.getId()) {
            case R.id.edtBinario: e.addTextChangedListener(listenerBinario); break;
            case R.id.edtOctal: e.addTextChangedListener(listenerOctal); break;
            case R.id.edtDecimal: e.addTextChangedListener(listenerDecimal); break;
            case R.id.edtHexadecimal: e.addTextChangedListener(listenerHexadecimal); break;
        }
    }

    private void desabilitarEventoAlteracao(EditText e) {
        switch (e.getId()) {
            case R.id.edtBinario: e.removeTextChangedListener(listenerBinario); break;
            case R.id.edtOctal: e.removeTextChangedListener(listenerOctal); break;
            case R.id.edtDecimal: e.removeTextChangedListener(listenerDecimal); break;
            case R.id.edtHexadecimal: e.removeTextChangedListener(listenerHexadecimal); break;
        }
    }

    private void alterarTodos(Integer decimal) {
        edtBinario.setText(Integer.toBinaryString(decimal));
        edtBinario.setSelection(edtBinario.length());
        edtOctal.setText(Integer.toOctalString(decimal));
        edtOctal.setSelection(edtOctal.length());
        edtDecimal.setText(decimal.toString());
        edtDecimal.setSelection(edtDecimal.length());
        edtHexadecimal.setText(Integer.toHexString(decimal));
        edtHexadecimal.setSelection(edtHexadecimal.length());
    }

    private void verificarTexto(String padrao, Editable s) {
        String text;

        try {
            text = s.toString().substring(s.toString().length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            desabilitarTodosEventosAlteracao();
            alterarTodos(0);
            habilitarTodosEventosAlteracao();
            return;
        }

        int length = s.toString().length();

        try {
            if (!Pattern.matches(padrao, text)) {
                desabilitarTodosEventosAlteracao();
                s.delete(length - 1, length);
                habilitarTodosEventosAlteracao();
            } else {
                desabilitarTodosEventosAlteracao();
                Integer decimal = getDecimalByPadrao(padrao, s.toString());
                alterarTodos(decimal);
                habilitarTodosEventosAlteracao();
            }
        } catch (Exception e){
            desabilitarTodosEventosAlteracao();
            alterarTodos(0);
            habilitarTodosEventosAlteracao();
        }
    }

    public Integer getDecimalByPadrao(String padrao, String texto) {

        if      (padrao.equals(BINARIO))
            return Integer.parseInt(texto, 2);
        else if (padrao.equals(OCTAL))
            return Integer.parseInt(texto, 8);
        else if (padrao.equals(DECIMAL))
            return Integer.parseInt(texto);
        else
            return 0;
    }
}
