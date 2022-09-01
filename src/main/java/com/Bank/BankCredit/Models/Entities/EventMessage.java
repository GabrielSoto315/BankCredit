package com.Bank.BankCredit.Models.Entities;

import lombok.Data;

@Data
public class EventMessage<T> {
    private String action;
    private T data;
}
