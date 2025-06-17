package br.com.ies.model;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private int cursoId;
    private String nomeCurso;

    public Usuario() {
    }

    public Usuario(int id, String nome, String cpf, String email, int cursoId) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.cursoId = cursoId;
    }

    public Usuario(int id, String nome, String cpf, String email, int cursoId, String nomeCurso) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.cursoId = cursoId;
        this.nomeCurso = nomeCurso;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    @Override
    public String toString() {
        return nome;
    }
} 