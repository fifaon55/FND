package fpt.captonse.dfn.service;

import fpt.captonse.dfn.config.Constants;
import fpt.captonse.dfn.domain.Authority;
import fpt.captonse.dfn.domain.User;
import fpt.captonse.dfn.repository.AuthorityRepository;
import fpt.captonse.dfn.repository.UserRepository;
import fpt.captonse.dfn.security.AuthoritiesConstants;
import fpt.captonse.dfn.security.SecurityUtils;
import fpt.captonse.dfn.service.dto.UserDTO;

import io.github.jhipster.security.RandomUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

//    public Optional<User> activateRegistration(String key) {
//        log.debug("Activating user for activation key {}", key);
//        return userRepository.findOneByActivationKey(key)
//            .map(user -> {
//                // activate given user for the registration key.
//                user.setActivated(true);
//                user.setActivationKey(null);
//                log.debug("Activated user: {}", user);
//                return user;
//            });
//    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByEmailIgnoreCase(key)
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
//                user.setResetKey(RandomUtil.generateResetKey());
//                user.setResetDate(Instant.now());
                return user;
            });
    }

    public User registerUser(UserDTO userDTO, String password) {
//        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
//            boolean removed = removeNonActivatedUser(existingUser);
//            if (!removed) {
//                throw new UsernameAlreadyUsedException();
//            }
//        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
//        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
//        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
//        newUser.setImageUrl(userDTO.getImageUrl());
//        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(true);
        // new user gets registration key
//        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Authority authorities = new Authority();
////        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        authorities.setName("ROLE_USER");
        authorities.setId(2);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
             return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
//        user.setLogin(userDTO.getLogin().toLowerCase());
//        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
//        user.setImageUrl(userDTO.getImageUrl());

//            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language

        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
//        user.setResetKey(RandomUtil.generateResetKey());
//        user.setResetDate(Instant.now());
        user.setActivated(true);
//        if (userDTO.getAuthorities() != null) {
//            Set<Authority> authorities = userDTO.getAuthorities().stream()
//                .map(authorityRepository::findById)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toSet());
//            user.setAuthorities(authorities);
//        }
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                user.setLastName(userDTO.getLastName());
                user.setActivated(userDTO.isActivated());
                Authority role = new Authority();
               String temp = userDTO.getAuthorities();
               if(temp.equals("ROLE_ADMIN")){
                   role.setId(1);
               }else{
                   role.setId(2);
               }
                user.setAuthorities(role);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByEmailIgnoreCase(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByEmailIgnoreCase)
            .ifPresent(user -> {
//                user.setFirstName(firstName);
                user.setLastName(lastName);
//                if (email != null) {
//                    user.setEmail(email.toLowerCase());
//                }
//                user.setLangKey(langKey);
//                user.setImageUrl(imageUrl);
                log.debug("Changed Information for User: {}", user);
            });
    }


    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByEmailIgnoreCase)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByEmailNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByEmailIgnoreCase(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByEmailIgnoreCase);
    }

//    /**
//     * Not activated users should be automatically deleted after 3 days.
//     * <p>
//     * This is scheduled to get fired everyday, at 01:00 (am).
//     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void removeNotActivatedUsers() {
//        userRepository
//            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
//            .forEach(user -> {
//                log.debug("Deleting not activated user {}", user.getLogin());
//                userRepository.delete(user);
//            });
//    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

}
