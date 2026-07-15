package com.acidui.skyblockahflipper.client.auction;

public class AuctionItem {
    private String itemId;
    private String itemName;
    private long price;
    private int quantity;

    public AuctionItem(String itemId, String itemName, long price, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}