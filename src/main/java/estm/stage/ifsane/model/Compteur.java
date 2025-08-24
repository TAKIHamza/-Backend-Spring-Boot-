package estm.stage.ifsane.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "compteurs")
public class Compteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroSerie;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateInstallation;

    @Column(nullable = false)
    private String dernierReleve;

    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;

    @ManyToOne
    @JoinColumn(name = "zone", nullable = false)
    private Zone zone;

    @OneToMany(mappedBy = "compteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Consommation> consommations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Date getDateInstallation() {
        return dateInstallation;
    }

    public void setDateInstallation(Date dateInstallation) {
        this.dateInstallation = dateInstallation;
    }

    public String getDernierReleve() {
        return dernierReleve;
    }

    public void setDernierReleve(String dernierReleve) {
        this.dernierReleve = dernierReleve;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }


    
}
