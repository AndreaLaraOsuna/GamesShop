package lara.carmen.GamesShop.repository;

import lara.carmen.GamesShop.models.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    List<Juego> findByTituloContainingIgnoreCase(String palabra);
}
