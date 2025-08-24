package estm.stage.ifsane.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "consommations")
public class Consommation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer trimestre;

    @Column(nullable = false)
    private Integer annee;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private Integer valeurCompteur;

    @ManyToOne
    @JoinColumn(name = "compteur_id", nullable = false)
    private Compteur compteur;

    @OneToMany(mappedBy = "consommation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Facture> factures;
    
    @Transient
    private List<Object[]> historique;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(Integer trimestre) {
        this.trimestre = trimestre;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getValeurCompteur() {
        return valeurCompteur;
    }

    public void setValeurCompteur(Integer valeurCompteur) {
        this.valeurCompteur = valeurCompteur;
    }

    public Compteur getCompteur() {
        return compteur;
    }

    public void setCompteur(Compteur compteur) {
        this.compteur = compteur;
    }

    public List<Object[]> getHistorique() {
        return historique;
    }

    public void setHistorique(List<Object[]> historique) {
        this.historique = historique;
    }
}