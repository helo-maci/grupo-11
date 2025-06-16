package br.com.ies.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe que representa um evento no sistema
 * Contém todos os atributos necessários para gerenciar um evento
 */
public class Evento {
    private int id;              // Identificador único do evento
    private String titulo;       // Título do evento
    private String descricao;    // Descrição detalhada do evento
    private LocalDate data;      // Data em que o evento ocorrerá
    private LocalTime hora;      // Horário em que o evento ocorrerá
    private int idCurso;         // ID do curso relacionado ao evento
    private int idPalestrante;   // ID do palestrante responsável pelo evento
    private String slug;         // Identificador único para URLs amigáveis

    /**
     * Construtor padrão sem parâmetros
     */
    public Evento() {
    }

    /**
     * Construtor completo com todos os atributos
     * @param id Identificador do evento
     * @param titulo Título do evento
     * @param descricao Descrição do evento
     * @param data Data do evento
     * @param hora Horário do evento
     * @param idCurso ID do curso
     * @param idPalestrante ID do palestrante
     * @param slug Identificador para URL
     */
    public Evento(int id, String titulo, String descricao, LocalDate data, LocalTime hora, 
                 int idCurso, int idPalestrante, String slug) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.idCurso = idCurso;
        this.idPalestrante = idPalestrante;
        this.slug = slug;
    }

    // Getters e Setters para todos os atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdPalestrante() {
        return idPalestrante;
    }

    public void setIdPalestrante(int idPalestrante) {
        this.idPalestrante = idPalestrante;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return titulo;
    }
} 