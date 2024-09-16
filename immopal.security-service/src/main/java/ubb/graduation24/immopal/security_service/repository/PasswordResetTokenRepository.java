package ubb.graduation24.immopal.security_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.graduation24.immopal.security_service.domain.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
