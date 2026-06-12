package hub.isaacode.bolao.application.service;

import hub.isaacode.bolao.domain.enums.Role;
import hub.isaacode.bolao.domain.model.User;
import hub.isaacode.bolao.infra.repository.UserRepository;
import hub.isaacode.bolao.web.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String name, String email, String rawPassword) {
        if (userRepository.existsByEmail(email))
            throw new BusinessException("E-mail já cadastrado");

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public User authenticate(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Credenciais inválidas"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword()))
            throw new BusinessException("Credenciais inválidas");

        return user;
    }
}