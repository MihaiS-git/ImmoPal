package ubb.graduation24.immopal.person_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ubb.graduation24.immopal.person_service.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long>,
                                            QueryByExampleExecutor<Person>,
                                            PagingAndSortingRepository<Person, Long> {

}
