package com.practise.Simple_Bank_App.repo;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practise.Simple_Bank_App.entity.Transaction;


@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long>{
	
	
	
	@Query("SELECT t FROM Transaction t where t.account.id = :accountId "
			+ "AND (:type IS NULL OR t.type = :type) "
			+ "AND  t.date >= :startDate "
			+ "AND  t.date <= :endDate ")
	List<Transaction> findByAccountIdAndOptionalFilters(@Param("accountId") Long accountId, @Param("type") String type,  @Param("startDate") LocalDateTime startDate
			,@Param("endDate") LocalDateTime endDate);

}
