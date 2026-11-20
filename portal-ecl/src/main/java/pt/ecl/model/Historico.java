package pt.ecl.model;

import java.time.LocalDateTime;

public class Historico {

    private String estadoAnterior;
    private String estadoNovo;
    private String utilizador;
    private LocalDateTime alteradoEm;

    public String getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(String estadoAnterior) { this.estadoAnterior = estadoAnterior; }

    public String getEstadoNovo() { return estadoNovo; }
    public void setEstadoNovo(String estadoNovo) { this.estadoNovo = estadoNovo; }

    public String getUtilizador() { return utilizador; }
    public void setUtilizador(String utilizador) { this.utilizador = utilizador; }

    public LocalDateTime getAlteradoEm() { return alteradoEm; }
    public void setAlteradoEm(LocalDateTime alteradoEm) { this.alteradoEm = alteradoEm; }

    public String getAlteradoEmFormatado() {
        return Datas.formatar(alteradoEm);
    }
}
