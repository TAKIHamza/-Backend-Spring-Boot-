package estm.stage.ifsane.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import estm.stage.ifsane.model.Utilisateur;
import estm.stage.ifsane.repository.UtilisateurRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
 @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new User(utilisateur.getEmail(), utilisateur.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole())));
    }

    public UserDetails loadUserById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        
        return new org.springframework.security.core.userdetails.User(
            utilisateur.getEmail(), 
            utilisateur.getPassword(), 
            Collections.emptyList());  // Roles should also be handled here.
    }

    public Utilisateur loadUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
