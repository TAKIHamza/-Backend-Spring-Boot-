package estm.stage.ifsane.model;

import java.util.List;

public class ConsommationHistorique {
    private int trimestre;
    private int annee;
    private List<Object[]> historique;

    // Getters and Setters
    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public List<Object[]> getHistorique() {
        return historique;
    }

    public void setHistorique(List<Object[]> historique) {
        this.historique = historique;
    }
}
