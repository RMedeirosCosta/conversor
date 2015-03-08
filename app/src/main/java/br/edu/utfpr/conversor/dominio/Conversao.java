package br.edu.utfpr.conversor.dominio;

/**
 * Created by ricardo on 07/03/15.
 */

public class Conversao {

    private Long id;
    private String binario;
    private String octal;
    private String decimal;
    private String hexadecimal;

    public Conversao(Long id, String binario, String octal, String decimal, String hexadecimal) {
        this.id = id;
        this.binario = binario;
        this.octal = octal;
        this.decimal = decimal;
        this.hexadecimal = hexadecimal;
    }

    public Conversao(String binario, String octal, String decimal, String hexadecimal) {
        id = 0l;

        if ((binario == null) || (binario.isEmpty()))
            throw new IllegalArgumentException("Valor binário está inválido");

        if ((octal == null) || (binario.isEmpty()))
            throw new IllegalArgumentException("Valor octal está inválido");

        if ((decimal == null) || (binario.isEmpty()))
            throw new IllegalArgumentException("Valor decimal está inválido");

        if ((hexadecimal == null) || (binario.isEmpty()))
            throw new IllegalArgumentException("Valor hexadecimal está inválido");

        this.binario = binario;
        this.octal = octal;
        this.decimal = decimal;
        this.hexadecimal = hexadecimal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBinario() {
        return binario;
    }

    public void setBinario(String binario) {
        this.binario = binario;
    }

    public String getOctal() {
        return octal;
    }

    public void setOctal(String octal) {
        this.octal = octal;
    }

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getHexadecimal() {
        return hexadecimal;
    }

    public void setHexadecimal(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }
}
