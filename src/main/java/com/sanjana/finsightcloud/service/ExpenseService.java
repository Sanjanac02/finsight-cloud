package com.sanjana.finsightcloud.service;

import com.sanjana.finsightcloud.exception.ExpenseNotFoundException;
import com.sanjana.finsightcloud.exception.UserNotFoundException;
import com.sanjana.finsightcloud.dto.ExpenseRequest;
import com.sanjana.finsightcloud.entity.Expense;
import com.sanjana.finsightcloud.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import com.sanjana.finsightcloud.entity.User;
import com.sanjana.finsightcloud.repository.UserRepository;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public List<Expense> getAllExpenses(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        return expenseRepository.findByUserId(user.getId());
    }

    public Expense getExpenseById(Long id, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return expenseRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID " + id + " not found"));
    }

    public Expense updateExpense(Long id, ExpenseRequest updatedExpense, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        Expense expense = expenseRepository.findByIdAndUserId(id, user.getId())
                    .orElseThrow(() ->
                            new ExpenseNotFoundException(
                                        "Expense not found with id: " + id
                            ));

        expense.setTitle(updatedExpense.getTitle());
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setExpenseDate(updatedExpense.getExpenseDate());
        expense.setDescription(updatedExpense.getDescription());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Expense expense = expenseRepository.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new ExpenseNotFoundException(
                    "Expense with ID " + id + " not found"
            ));

        expenseRepository.delete(expense);
    }

    public Expense saveExpense(ExpenseRequest request, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());

        expense.setUser(user);
        return expenseRepository.save(expense);
    }
}
