package br.fpu.exemplolistactivity.domain;

public class Tarefa {
    private int id;
    private String descricao;
    private String responsavel;
    private Status status;

    public Tarefa() {
    }

    public Tarefa(int id, String descricao, String responsavel,
                  Status status) {
        this.id = id;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", responsavel='" + responsavel + '\'' +
                ", status=" + status +
                '}';
    }
}
