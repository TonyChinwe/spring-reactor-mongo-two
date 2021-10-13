package com.phisoft.springreactormongoone.repositories;
import com.phisoft.springreactormongoone.models.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * The Application interface for saving and fetching data from mongodb database
 */
@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee,String> {

    /**
     * {@inheritDoc}
     */
    public Flux<Employee> findBySalaryBetween(Long min,Long max);

    @Tailable
    Flux<Employee> findWithTailableCursorBy();

}
