package estm.stage.ifsane.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parametres")
public class Parametre {
    @Id
    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    
}
