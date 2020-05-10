package com.bsd.multiparameterspoc.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class POCController {
    @GetMapping
    private ResponseEntity<?> processMultipleRequestParams(
            @RequestParam(value = "q", required = false) List<String> expenseDetails,
            @RequestParam(value = "amount", required = false) String amount,
            @RequestParam(value = "amount_gt", required = false) String amountGreaterThan,
            @RequestParam(value = "amount_lt", required = false) String amountLessThan,
            @RequestParam(value = "type", required = false) List<ExpenseType> expenseType,
            @RequestParam(value = "date", required = false) String expenseDateTime,
            @RequestParam(value = "date_gt", required = false) String expenseDateTimeGreaterThan,
            @RequestParam(value = "date_lt", required = false) String expenseDateTimeLessThan,
            @RequestParam(value = "category", required = false) List<String> category
    ) {
        Map<String, Map<String, ?>> params = new HashMap<>();
        checkAndAssign(expenseDetails, ":", "expenseDetails", params);
        checkAndAssign(amount, ":", "amount", params);
        checkAndAssign(amountGreaterThan, ">", "amount greater than", params);
        checkAndAssign(amountLessThan, "<", "amount less than", params);
        checkAndAssign(expenseType, ":", "expense type", params);
        checkAndAssign(expenseDateTime, ":", "expenseDateTime", params);
        checkAndAssign(expenseDateTimeGreaterThan, ">", "expenseDateTime greater than", params);
        checkAndAssign(expenseDateTimeLessThan, "<", "expenseDateTime less than", params);
        checkAndAssign(category, ":", "categories", params);
        return new ResponseEntity<>(params, HttpStatus.OK);
    }

    private <T> void checkAndAssign(T valueToAssign, String operatorToAssign, String paramName, Map<String, Map<String, ?>> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        if (valueToAssign != null) {
            if (valueToAssign instanceof String) {
                Map<String, String> criteria = new HashMap<>();
                if (!StringUtils.isBlank(valueToAssign.toString())) {
                    criteria.put(operatorToAssign, valueToAssign.toString());
                    params.put(paramName, criteria);
                }
            } else if (valueToAssign instanceof List) {
                Map<String, List<String>> criteria = new HashMap<>();
                List<String> values = new ArrayList<>();
                ((List<?>) valueToAssign).forEach(value -> values.add(value.toString()));
                criteria.put(operatorToAssign, values);
                params.put(paramName, criteria);
            }

        }
    }
}
