package br.com.ies.model;

public class Palestrante {
    private int id;
    private String nome;
    private String email;
    private String miniCurriculo;
    private byte[] foto;

    public Palestrante() {
    }


    public Palestrante(int id, String nome, String email, String miniCurriculo, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.miniCurriculo = miniCurriculo;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMiniCurriculo() {
        return miniCurriculo;
    }

    public void setMiniCurriculo(String miniCurriculo) {
        this.miniCurriculo = miniCurriculo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }


    @Override
    public String toString() {
        return nome;
    }
} 