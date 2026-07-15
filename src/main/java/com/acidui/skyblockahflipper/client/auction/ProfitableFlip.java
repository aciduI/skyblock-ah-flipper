package com.acidui.skyblockahflipper.client.auction;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ProfitableFlip {
    private String itemName;
    private String itemId;
    private long buyPrice;
    private long sellPrice;
    private long profit;
    private int quantity;

    public ProfitableFlip(String itemName, String itemId, long buyPrice, long sellPrice, int quantity) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.profit = (sellPrice - buyPrice) * quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public long getBuyPrice() {
        return buyPrice;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public long getProfit() {
        return profit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void sendClickableMessage(MinecraftClient client) {
        String profitFormatted = formatNumber(profit);
        String buyFormatted = formatNumber(buyPrice);
        String sellFormatted = formatNumber(sellPrice);

        MutableText message = Text.literal("")
                .append(Text.literal("[AH Flipper] ").formatted(Formatting.GOLD))
                .append(Text.literal(itemName).formatted(Formatting.AQUA))
                .append(Text.literal(" | ").formatted(Formatting.DARK_GRAY))
                .append(Text.literal(profitFormatted + " profit").formatted(Formatting.GREEN))
                .append(Text.literal(" | ").formatted(Formatting.DARK_GRAY))
                .append(Text.literal("[CLICK TO VIEW]").formatted(Formatting.YELLOW))
                .styled(style -> style
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://hypixel.net/auction/search?query=" + itemId))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Text.literal("Buy: " + buyFormatted + "\nSell: " + sellFormatted + "\nProfit: " + profitFormatted)
                                        .formatted(Formatting.YELLOW)))
                );

        client.inGameHud.getChatHud().addMessage(message);
    }

    private static String formatNumber(long num) {
        if (num >= 1_000_000) {
            return String.format("%.1fM", num / 1_000_000.0);
        } else if (num >= 1_000) {
            return String.format("%.0fK", num / 1_000.0);
        }
        return String.valueOf(num);
    }
}