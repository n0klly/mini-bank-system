package ru.noklly.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.noklly.accountservice.entity.AccountEntity;
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}
