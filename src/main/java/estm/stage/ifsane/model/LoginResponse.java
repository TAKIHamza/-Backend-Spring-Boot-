package estm.stage.ifsane.model;

public class LoginResponse {
    private String jwtToken;
    private Long id;
    private String email;
    private String nom;
    private String zone ;
    private String role;

    public LoginResponse(String jwtToken, Long id, String email, String nom,String zone, String role) {
        this.jwtToken = jwtToken;
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.zone=zone ;
        this.role = role;

    }

    // Getters and Setters
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}