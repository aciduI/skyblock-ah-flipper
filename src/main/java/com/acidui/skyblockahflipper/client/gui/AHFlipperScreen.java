package com.acidui.skyblockahflipper.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import com.acidui.skyblockahflipper.client.auction.AuctionScanner;
import com.acidui.skyblockahflipper.client.auction.ProfitableFlip;
import java.util.List;

public class AHFlipperScreen extends Screen {
    private TextFieldWidget minProfitField;
    private TextFieldWidget maxPriceField;
    private ButtonWidget scanButton;
    private ButtonWidget refreshButton;
    private List<ProfitableFlip> currentFlips;
    private AuctionScanner scanner;
    private int selectedFlipIndex = -1;
    private int scrollOffset = 0;

    public AHFlipperScreen() {
        super(Text.literal("Skyblock AH Flipper"));
        this.scanner = new AuctionScanner();
        this.currentFlips = List.of();
    }

    @Override
    protected void init() {
        super.init();
        this.clearChildren();

        int centerX = this.width / 2;
        int startY = 20;

        // Close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✕"), button -> this.close())
                .dimensions(this.width - 30, 10, 20, 20)
                .build());

        // Min Profit Label
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Min Profit:"), button -> {})
                .dimensions(20, startY, 100, 20)
                .build());

        minProfitField = new TextFieldWidget(this.textRenderer, 130, startY, 100, 20, Text.literal("Min Profit"));
        minProfitField.setText("100000");
        this.addSelectableChild(minProfitField);
        this.setInitialFocus(minProfitField);

        // Max Price Label
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Max Price:"), button -> {})
                .dimensions(250, startY, 100, 20)
                .build());

        maxPriceField = new TextFieldWidget(this.textRenderer, 360, startY, 100, 20, Text.literal("Max Price"));
        maxPriceField.setText("50000000");
        this.addSelectableChild(maxPriceField);

        // Scan Button
        scanButton = this.addDrawableChild(ButtonWidget.builder(Text.literal("Scan Auctions"), button -> scanAuctions())
                .dimensions(centerX - 100, startY + 30, 200, 20)
                .build());

        // Refresh Button
        refreshButton = this.addDrawableChild(ButtonWidget.builder(Text.literal("Refresh Data"), button -> refreshData())
                .dimensions(centerX - 100, startY + 60, 200, 20)
                .build());
    }

    private void scanAuctions() {
        try {
            long minProfit = Long.parseLong(minProfitField.getText());
            long maxPrice = Long.parseLong(maxPriceField.getText());
            currentFlips = scanner.findProfitableFlips(minProfit, maxPrice);
            scrollOffset = 0;
            selectedFlipIndex = -1;
        } catch (NumberFormatException e) {
            // Invalid input
        }
    }

    private void refreshData() {
        scanner.refreshAuctionData();
        scanAuctions();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int startY = 110;
        int lineHeight = 25;
        int visibleLines = (this.height - startY - 20) / lineHeight;

        // Draw results header
        context.drawTextWithShadow(this.textRenderer, "Found: " + currentFlips.size() + " profitable flips", 20, startY - 20, 0xFFFFFF);

        for (int i = 0; i < Math.min(visibleLines, currentFlips.size()); i++) {
            int flipIndex = scrollOffset + i;
            if (flipIndex >= currentFlips.size()) break;

            ProfitableFlip flip = currentFlips.get(flipIndex);
            int y = startY + (i * lineHeight);
            boolean isHovered = mouseX > 20 && mouseX < this.width - 20 && mouseY > y && mouseY < y + lineHeight - 5;

            if (selectedFlipIndex == flipIndex) {
                context.fill(15, y - 2, this.width - 15, y + lineHeight - 7, 0xFF4CAF50);
            } else if (isHovered) {
                context.fill(15, y - 2, this.width - 15, y + lineHeight - 7, 0xFF333333);
            }

            String itemName = flip.getItemName();
            String profitText = "Profit: " + formatNumber(flip.getProfit()) + " coins";
            String priceText = "Buy: " + formatNumber(flip.getBuyPrice()) + " | Sell: " + formatNumber(flip.getSellPrice());

            context.drawTextWithShadow(this.textRenderer, itemName, 25, y, 0xFFFFFF);
            context.drawTextWithShadow(this.textRenderer, profitText, 25, y + 10, 0xFF4CAF50);
            context.drawTextWithShadow(this.textRenderer, priceText, 25, y + 18, 0xFFBBBBBB);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int visibleLines = (this.height - 110 - 20) / 25;
        if (verticalAmount > 0) {
            scrollOffset = Math.max(0, scrollOffset - 1);
        } else {
            scrollOffset = Math.min(Math.max(0, currentFlips.size() - visibleLines), scrollOffset + 1);
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int startY = 110;
            int lineHeight = 25;
            int visibleLines = (this.height - startY - 20) / lineHeight;

            for (int i = 0; i < Math.min(visibleLines, currentFlips.size()); i++) {
                int flipIndex = scrollOffset + i;
                if (flipIndex >= currentFlips.size()) break;

                int y = startY + (i * lineHeight);
                if (mouseX > 20 && mouseX < this.width - 20 && mouseY > y && mouseY < y + lineHeight - 5) {
                    ProfitableFlip flip = currentFlips.get(flipIndex);
                    flip.sendClickableMessage(this.client);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    private String formatNumber(long num) {
        if (num >= 1_000_000) {
            return String.format("%.1fM", num / 1_000_000.0);
        } else if (num >= 1_000) {
            return String.format("%.0fK", num / 1_000.0);
        }
        return String.valueOf(num);
    }
}