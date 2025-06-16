package br.com.ies.model;

/**
 * Classe que representa um palestrante no sistema
 * Contém os atributos básicos para identificar e contatar um palestrante
 */
public class Palestrante {
    private int id;              // Identificador único do palestrante
    private String nome;         // Nome completo do palestrante
    private String email;        // Email de contato do palestrante
    private String miniCurriculo; // Mini curriculo do palestrante
    private byte[] foto;         // Foto do palestrante

    /**
     * Construtor padrão sem parâmetros
     */
    public Palestrante() {
    }

    /**
     * Construtor completo com todos os atributos
     * @param id Identificador do palestrante
     * @param nome Nome do palestrante
     * @param email Email do palestrante
     * @param miniCurriculo Mini curriculo do palestrante
     * @param foto Foto do palestrante
     */
    public Palestrante(int id, String nome, String email, String miniCurriculo, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.miniCurriculo = miniCurriculo;
        this.foto = foto;
    }

    // Getters e Setters para todos os atributos
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

    /**
     * Sobrescreve o método toString para exibir o nome do palestrante
     * @return String com o nome do palestrante
     */
    @Override
    public String toString() {
        return nome;
    }
} 