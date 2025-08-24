package estm.stage.ifsane.model;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "factures")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double montant;
    @Column(nullable = false)
    private Date  dateEmission;
    @Column(nullable = false)
    private Date  dateLimitePaiement;
    @Column(nullable = true)
    private Date   datePaiement;
    @Column(nullable = false)
    private Integer estPayee;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "consommation_id", nullable = false)
    private Consommation consommation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public Date getDateLimitePaiement() {
        return dateLimitePaiement;
    }

    public void setDateLimitePaiement(Date dateLimitePaiement) {
        this.dateLimitePaiement = dateLimitePaiement;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Integer getEstPayee() {
        return estPayee;
    }

    public void setEstPayee(Integer estPayee) {
        this.estPayee = estPayee;
    }

    public Consommation getConsommation() {
        return consommation;
    }

    public void setConsommation(Consommation consommation) {
        this.consommation = consommation;
    }
}
