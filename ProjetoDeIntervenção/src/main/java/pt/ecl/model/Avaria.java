package pt.ecl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Avaria {

    private int id;
    private String sala;
    private String tipo;
    private String descricao;
    private String reportadoPor;
    private String estado;
    private LocalDateTime dataRegisto;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getReportadoPor() { return reportadoPor; }
    public void setReportadoPor(String reportadoPor) { this.reportadoPor = reportadoPor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getDataRegisto() { return dataRegisto; }
    public void setDataRegisto(LocalDateTime dataRegisto) { this.dataRegisto = dataRegisto; }

    public String getDataRegistoFormatada() {
        return dataRegisto == null ? "" : dataRegisto.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
