package com.sanjana.finsightcloud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.sanjana.finsightcloud.entity.Expense;
import com.sanjana.finsightcloud.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import com.sanjana.finsightcloud.dto.ExpenseRequest;
import com.sanjana.finsightcloud.dto.ExpenseResponse;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseResponse saveExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        
        String userEmail = SecurityContextHolder.getContext()
                                                .getAuthentication()
                                                .getName();

        return expenseService.saveExpense(expenseRequest, userEmail);
    }

    @GetMapping
    public List<ExpenseResponse> getAllExpenses() {

        String userEmail = SecurityContextHolder 
                        .getContext() 
                        .getAuthentication()
                        .getName();

        return expenseService.getAllExpenses(userEmail);
    }

    @GetMapping("/{id}")
    public ExpenseResponse getExpenseById(@PathVariable Long id) {

        String userEmail = SecurityContextHolder    
                    .getContext()
                    .getAuthentication()
                    .getName();

        return expenseService.getExpenseById(id, userEmail);
    }

    @PutMapping("/{id}")
    public ExpenseResponse updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequest updatedExpense) {

        String userEmail = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return expenseService.updateExpense(id, updatedExpense, userEmail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        String userEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        expenseService.deleteExpense(id, userEmail);

        return ResponseEntity.noContent().build();
    }
}
