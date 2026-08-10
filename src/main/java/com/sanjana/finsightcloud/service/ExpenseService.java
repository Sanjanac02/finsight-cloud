package com.sanjana.finsightcloud.service;

import com.sanjana.finsightcloud.exception.ExpenseNotFoundException;
import com.sanjana.finsightcloud.dto.ExpenseRequest;
import com.sanjana.finsightcloud.entity.Expense;
import com.sanjana.finsightcloud.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID " + id + " not found"));
    }

    public Expense updateExpense(Long id, ExpenseRequest updatedExpense) {
        Expense existingExpense = expenseRepository.findById(id)      // find does this expense exists? 
            .orElseThrow(() -> new ExpenseNotFoundException(
                    "Expense with ID " + id + " not found"
            ));

        existingExpense.setTitle(updatedExpense.getTitle());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setExpenseDate(updatedExpense.getExpenseDate());
        existingExpense.setDescription(updatedExpense.getDescription());

        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseNotFoundException(
                    "Expense with ID " + id + " not found"
            ));

        expenseRepository.delete(expense);
    }

    public Expense saveExpense(ExpenseRequest request) {

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());

        return expenseRepository.save(expense);
    }
}
