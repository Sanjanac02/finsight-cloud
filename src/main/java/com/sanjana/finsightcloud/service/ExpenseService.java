package com.sanjana.finsightcloud.service;

import com.sanjana.finsightcloud.exception.ExpenseNotFoundException;
import com.sanjana.finsightcloud.exception.UserNotFoundException;
import com.sanjana.finsightcloud.dto.ExpenseRequest;
import com.sanjana.finsightcloud.dto.ExpenseResponse;
import com.sanjana.finsightcloud.entity.Expense;
import com.sanjana.finsightcloud.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import com.sanjana.finsightcloud.entity.User;
import com.sanjana.finsightcloud.repository.UserRepository;
import com.sanjana.finsightcloud.dto.ExpenseResponse;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public List<ExpenseResponse> getAllExpenses(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        return expenseRepository.findByUserId(user.getId())
                    .stream()
                    .map(this::toExpenseResponse)
                    .toList();
    }

    public ExpenseResponse getExpenseById(Long id, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Expense expense = expenseRepository.findByIdAndUserId(id, user.getId())
                    .orElseThrow(() ->
                            new ExpenseNotFoundException(
                                        "Expense not found with id: " + id
                            ));

        return toExpenseResponse(expense);
    }

    public ExpenseResponse updateExpense(Long id, ExpenseRequest updatedExpense, String userEmail) {

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

        Expense savedExpense = expenseRepository.save(expense);

        return toExpenseResponse(savedExpense);
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

    public ExpenseResponse saveExpense(ExpenseRequest request, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());

        expense.setUser(user);

        Expense savedExpense = expenseRepository.save(expense);
        return toExpenseResponse(savedExpense);
    }

    private ExpenseResponse toExpenseResponse(Expense expense) {

        ExpenseResponse response = new ExpenseResponse();

        response.setId(expense.getId());
        response.setTitle(expense.getTitle());
        response.setAmount(expense.getAmount());
        response.setCategory(expense.getCategory());
        response.setExpenseDate(expense.getExpenseDate());
        response.setDescription(expense.getDescription());

        return response;
    }
}
