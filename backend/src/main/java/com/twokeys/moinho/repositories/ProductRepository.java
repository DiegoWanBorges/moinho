package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByNameLikeIgnoreCase(String name,Pageable pageable);
	List<Product> findByNameLikeIgnoreCase(String nameConcat);
	
	@Query(value = " select "
			 	 + " * "
			 	 + " from tb_product "
			 	 + " where id not in( "
			 	 + " select "
			 	 + " product_id "
			 	 + " from TB_FORMULATION_ITEM  "
			 	 + " where FORMULATION_ID = :id) "
		, 	nativeQuery = true)
	List<Product> findProductNotInFormulation(Long id);
	
	@Query(value = " select "
		 	 + " * "
		 	 + " from tb_product "
		 	 + " where id in(select product_id from tb_formulation  where id = :id) "
		 	 + " or id in(select product_id from TB_FORMULATION_SECONDARY_PRODUCTION where formulation_id=:id) "
		 	 ,nativeQuery = true)
	List<Product> findProductProducedByFormulation(Long id);
	
}

