package com.twokeys.moinho.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.twokeys.moinho.entities.FormulationItem;
import com.twokeys.moinho.entities.pk.FormulationItemPK;

@Repository
public interface FormulationItemRepository extends JpaRepository<FormulationItem, FormulationItemPK> {
		
}

