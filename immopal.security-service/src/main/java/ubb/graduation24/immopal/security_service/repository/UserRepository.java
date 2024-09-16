package ubb.graduation24.immopal.security_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.security_service.domain.Role;
import ubb.graduation24.immopal.security_service.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
                                        QueryByExampleExecutor<User>,
                                        PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

}
