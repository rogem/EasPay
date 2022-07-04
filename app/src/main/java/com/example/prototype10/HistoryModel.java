package com.example.prototype10;

public class HistoryModel {
    String senderName,receiverNumber, amount,message;

    public HistoryModel(){
    }

    public HistoryModel(String senderName, String receiverNumber, String amount, String message) {
        this.senderName = senderName;
        this.receiverNumber = receiverNumber;
        this.amount = amount;
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public String getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }
}
