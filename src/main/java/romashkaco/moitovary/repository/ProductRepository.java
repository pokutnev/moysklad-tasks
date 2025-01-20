package romashkaco.moitovary.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import romashkaco.moitovary.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Transactional
    @Modifying
    @Query("UPDATE Product p set p.name = :newName where p.name = :oldName")
    int updateName(@Param("oldName")String oldName, @Param("newName") String newName);

    @Transactional
    int deleteByName(String name);

    @Query("select p from Product p where lower(p.name) like lower(:name)")
    Product findByName(@Param("name") String name);
}
