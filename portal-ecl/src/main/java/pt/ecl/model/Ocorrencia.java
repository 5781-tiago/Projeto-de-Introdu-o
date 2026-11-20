package pt.ecl.model;

import java.time.LocalDateTime;

public class Ocorrencia {

    private int id;
    private String codigo;
    private String nomeReportador;
    private String perfilReportador;
    private int salaId;
    private String sala;
    private String localizacao;
    private String categoria;
    private String descricao;
    private String prioridade;
    private String estado;
    private String resolucao;
    private String tecnico;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNomeReportador() { return nomeReportador; }
    public void setNomeReportador(String nomeReportador) { this.nomeReportador = nomeReportador; }

    public String getPerfilReportador() { return perfilReportador; }
    public void setPerfilReportador(String perfilReportador) { this.perfilReportador = perfilReportador; }

    public int getSalaId() { return salaId; }
    public void setSalaId(int salaId) { this.salaId = salaId; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getResolucao() { return resolucao; }
    public void setResolucao(String resolucao) { this.resolucao = resolucao; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }

    public String getCriadoEmFormatado() {
        return Datas.formatar(criadoEm);
    }

    public String getAtualizadoEmFormatado() {
        return Datas.formatar(atualizadoEm);
    }
}
