package ru.kirill.rolemanager.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kirill.rolemanager.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @NonNull
    @EntityGraph(attributePaths = "roles")
    Optional<User> findById(@NonNull Long id);
}
