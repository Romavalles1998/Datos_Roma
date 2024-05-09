package jpaswing.repository;

import jpaswing.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Animal findFirstByOrderByIdAsc();
    List<Animal> findAllByOrderByIdAsc();  // Nuevo método
}
