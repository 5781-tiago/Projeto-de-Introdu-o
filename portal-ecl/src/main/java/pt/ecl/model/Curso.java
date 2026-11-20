package pt.ecl.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Curso {

    private int id;
    private String nome;
    private String modalidade;
    private String area;
    private String nivel;
    private Integer horasTotal;
    private String componentes;
    private String perfil;
    private String saidasProfissionais;
    private String fonte;
    private LocalDate dataVerificacao;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public Integer getHorasTotal() { return horasTotal; }
    public void setHorasTotal(Integer horasTotal) { this.horasTotal = horasTotal; }

    public String getComponentes() { return componentes; }
    public void setComponentes(String componentes) { this.componentes = componentes; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public String getSaidasProfissionais() { return saidasProfissionais; }
    public void setSaidasProfissionais(String saidasProfissionais) { this.saidasProfissionais = saidasProfissionais; }

    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }

    public LocalDate getDataVerificacao() { return dataVerificacao; }
    public void setDataVerificacao(LocalDate dataVerificacao) { this.dataVerificacao = dataVerificacao; }

    public String getDataVerificacaoFormatada() {
        return dataVerificacao == null ? "" : dataVerificacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public List<String> getListaComponentes() {
        return dividir(componentes);
    }

    public List<String> getListaSaidas() {
        return dividir(saidasProfissionais);
    }

    public boolean isFonteLink() {
        return fonte != null && fonte.startsWith("https://");
    }

    private List<String> dividir(String texto) {
        List<String> lista = new ArrayList<>();
        if (texto != null) {
            for (String parte : texto.split(";")) {
                if (!parte.isBlank()) {
                    lista.add(parte.trim());
                }
            }
        }
        return lista;
    }
}
