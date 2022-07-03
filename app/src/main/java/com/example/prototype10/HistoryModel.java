package com.example.prototype10;

public class HistoryModel {
    String senderName,receiverName, amount;

    public HistoryModel(){
    }

    public HistoryModel(String senderName, String receiverName, String amount) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amount = amount;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getAmount() {
        return amount;
    }
}
