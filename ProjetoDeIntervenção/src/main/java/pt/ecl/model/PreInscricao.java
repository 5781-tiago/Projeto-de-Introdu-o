package pt.ecl.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PreInscricao {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private int cursoId;
    private String nomeCurso;
    private String observacoes;
    private LocalDateTime dataRegisto;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }

    public String getNomeCurso() { return nomeCurso; }
    public void setNomeCurso(String nomeCurso) { this.nomeCurso = nomeCurso; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDateTime getDataRegisto() { return dataRegisto; }
    public void setDataRegisto(LocalDateTime dataRegisto) { this.dataRegisto = dataRegisto; }

    public String getDataNascimentoFormatada() {
        return dataNascimento == null ? "" : dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getDataRegistoFormatada() {
        return dataRegisto == null ? "" : dataRegisto.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
