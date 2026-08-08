package com.sanjana.finsightcloud.repository;

import com.sanjana.finsightcloud.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
