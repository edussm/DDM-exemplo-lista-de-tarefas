package br.fpu.exemplolistactivity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;
	private Date dataCriacao;
	private Date dataInicio;
	private Date dataFim;
	private String criador;
	private String executor;
	private Status status;

	public Tarefa(){}
	public Tarefa(Long i, String s, String resp, Status pendente) {
		this.id = i;
		this.descricao = s;
		this.executor = resp;
		this.status = pendente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getCriador() {
		return criador;
	}

	public void setCriador(String criador) {
		this.criador = criador;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tarefa [id=");
		builder.append(id);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append(", dataCriacao=");
		builder.append(dataCriacao);
		builder.append(", dataInicio=");
		builder.append(dataInicio);
		builder.append(", dataFim=");
		builder.append(dataFim);
		builder.append(", criador=");
		builder.append(criador);
		builder.append(", executor=");
		builder.append(executor);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
