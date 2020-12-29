package com.homework.epam.dao;

import com.homework.epam.model.BillingDetails;

import java.util.List;

public interface BillingDetailsDao {
    List<BillingDetails> get(Integer userId);
}
