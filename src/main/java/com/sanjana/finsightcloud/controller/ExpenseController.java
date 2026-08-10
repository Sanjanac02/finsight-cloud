package com.sanjana.finsightcloud.controller;

import com.sanjana.finsightcloud.entity.Expense;
import com.sanjana.finsightcloud.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import com.sanjana.finsightcloud.dto.ExpenseRequest;
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
    public Expense saveExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        return expenseService.saveExpense(expenseRequest);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PutMapping("/{id}")
    public Expense updatExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequest updatedExpense) {
        return expenseService.updateExpense(id, updatedExpense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
