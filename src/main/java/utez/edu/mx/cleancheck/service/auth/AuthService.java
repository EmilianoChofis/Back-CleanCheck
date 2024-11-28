package utez.edu.mx.cleancheck.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.auth.dto.SignDto;
import utez.edu.mx.cleancheck.controller.auth.dto.SignedDto;
import utez.edu.mx.cleancheck.controller.user.dto.UserDto;
import utez.edu.mx.cleancheck.model.role.Role;
import utez.edu.mx.cleancheck.model.role.RoleRepository;
import utez.edu.mx.cleancheck.model.user.User;
import utez.edu.mx.cleancheck.model.user.UserRepository;
import utez.edu.mx.cleancheck.security.jwt.JwtProvider;
import utez.edu.mx.cleancheck.security.service.UserDetailsServiceImpl;
import utez.edu.mx.cleancheck.service.user.UserService;
import utez.edu.mx.cleancheck.utils.ApiResponse;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authManager;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${receptionist.name}")
    private String receptionistName;
    @Value("${maid.name}")
    private String maidName;
    @Value("${manager.name}")
    private String managerName;

    private User userCreate(UserDto user, Role foundRole, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        User newUser = new User();
        String idUser = UUID.randomUUID().toString();
        user.setId(idUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(foundRole);
        newUser.setBlocked(true);
        user.setStatus(true);
        BeanUtils.copyProperties(user, newUser);
        newUser = userRepository.save(newUser);

        User userResponse = new User();
        BeanUtils.copyProperties(newUser, userResponse);
        userResponse.setPassword(null);

        return userResponse;
    }


    @Transactional(readOnly = true)
    public ApiResponse<SignedDto> signIn(SignDto user) {
        try {
            Optional<User> foundUser = userService.findByEmail(user.getEmail());
            if (foundUser.isEmpty())
                return new ApiResponse<>(
                        null, true, HttpStatus.NOT_FOUND.value(), "Usuario no encontrado"
                );
            User userFound = foundUser.get();
            if (Boolean.FALSE.equals(userFound.getStatus()))
                return new ApiResponse<>(
                        null, true, HttpStatus.UNAUTHORIZED.value(), "Usuario inactivo"
                );
            if (Boolean.FALSE.equals(userFound.getBlocked()))
                return new ApiResponse<>(
                        null, true, HttpStatus.UNAUTHORIZED.value(), "Usuario bloqueado"
                );
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtProvider.generateToken(userDetails);
            SignedDto signedDto = new SignedDto(token, userFound);
            signedDto.getUser().setPassword(null);
            return new ApiResponse<>(
                    signedDto, false, HttpStatus.OK.value(), "Login correcto!"
            );
        } catch (DisabledException e) {
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST.value(), "Usuario deshabilitado"
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST.value(), "Usuario y/o contraseña incorrectos"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<User> createReceptionist(UserDto user) {
        Role clientRole = roleRepository.findByName(receptionistName).orElse(null);
        if (clientRole == null)
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST.value(), "Rol no encontrado"
            );
        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (foundUser != null)
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST.value(), "Recepcionista ya registrado"
            );

        User saveUser = userCreate(user, clientRole, passwordEncoder, userRepository);

        return new ApiResponse<>(
                saveUser, false, HttpStatus.OK.value(), "Receptionista registrado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<User> createMaid(UserDto user) {
        Role clientRole = roleRepository.findByName(maidName).orElse(null);
        if (clientRole == null)
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST.value(), "Rol no encontrado"
            );
        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (foundUser != null)
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST.value(), "Mucama ya registrado"
            );

        User saveUser = userCreate(user, clientRole, passwordEncoder, userRepository);

        return new ApiResponse<>(
                saveUser, false, HttpStatus.OK.value(), "Mucama registrado correctamente"
        );
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<User> createManager(UserDto user) {
        Role clientRole = roleRepository.findByName(managerName).orElse(null);
        if (clientRole == null)
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST.value(), "Rol no encontrado"
            );
        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (foundUser != null)
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST.value(), "Gerente ya registrado"
            );

        User saveUser = userCreate(user, clientRole, passwordEncoder, userRepository);

        return new ApiResponse<>(
                saveUser, false, HttpStatus.OK.value(), "Gerente registrado correctamente"
        );
    }
}
