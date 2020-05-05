package com.authorization.service;

import com.authorization.CustomProfilesDetails;
import com.authorization.exception.ResourceNotFoundException;
import com.authorization.model.Profiles;
import com.authorization.model.Role;
import com.authorization.model.crud_model.FavoriteLink;
import com.authorization.model.dto.RegisterUser;
import com.authorization.repository.ProfilesRepository;
import com.authorization.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfileService implements UserDetailsService {

    private ProfilesRepository profilesRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public ProfileService(ProfilesRepository profilesRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.profilesRepository = profilesRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Profiles> profilesOptional = profilesRepository.getProfilesByLogin(login);
        profilesOptional.orElseThrow(() -> new UsernameNotFoundException("Profile not found"));
        return profilesOptional.map(CustomProfilesDetails::new).get();
    }

    public Profiles registerUser(Profiles user) {
        Role role = roleRepository.findById(2);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        Profiles result = profilesRepository.save(user);
        return result;
    }

    /*  ----------------------- CRUD -------------------  */

    public List<Profiles> allProfile() {
        return profilesRepository.findAll();
    }

    public Profiles addProfile(Profiles profiles) {
        return profilesRepository.save(profiles);
    }

    public Profiles getProfileById(long id) {
        return profilesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("can't find id"));
    }

    public Profiles updateProfile(long id, Profiles profiles) {
        return profilesRepository.findById(id).map(f -> {
            f.setName(profiles.getName());
            f.setLastName(profiles.getLastName());
            f.setEmail(profiles.getEmail());
            f.setLogin(profiles.getLogin());
            f.setPassword(profiles.getPassword());
            return profilesRepository.save(f);
        }).orElseThrow(() -> new ResourceNotFoundException("link by id: " + id + " not found"));
    }

    public ResponseEntity<?> deleteProfile(long id) {
        return profilesRepository.findById(id).map(f -> {
            profilesRepository.deleteById(id);
            return new ResponseEntity<>("profile " + id + " was delete", HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("profile by id: " + id + " not found"));
    }

}
