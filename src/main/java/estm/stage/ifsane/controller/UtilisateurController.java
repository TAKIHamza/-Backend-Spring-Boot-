package estm.stage.ifsane.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import estm.stage.ifsane.model.LoginRequest;
import estm.stage.ifsane.model.LoginResponse;
import estm.stage.ifsane.model.UpdatePasswordRequest;
import estm.stage.ifsane.model.Utilisateur;
import estm.stage.ifsane.security.JwtTokenProvider;
import estm.stage.ifsane.service.CustomUserDetailsService;
import estm.stage.ifsane.service.MembreService;
import estm.stage.ifsane.service.UtilisateurService;


@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private MembreService membreService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
       
        Authentication authentication;
       try {
        authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword()
            )
        );
    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utilisateur utilisateur = customUserDetailsService.loadUserByEmail(userDetails.getUsername());
        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        LoginResponse response = new LoginResponse(
            jwt,
            utilisateur.getId(),
            utilisateur.getEmail(),
            utilisateur.getNom(),
            membreService.getMembreUtilisateurById(utilisateur.getId()).getZone().getName(),
            utilisateur.getRole()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        return ResponseEntity.ok(utilisateur);
    }
@PutMapping("/{id}/update-password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        try {
            utilisateurService.updatePassword(id, updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<Utilisateur> createUser(@RequestBody Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurService.createUtilisateur(utilisateur);
        return ResponseEntity.ok(savedUtilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id, @RequestBody Utilisateur utilisateurDetails) {
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateurDetails);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.ok().build();
    }
}
