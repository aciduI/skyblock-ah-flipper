package com.acidui.skyblockahflipper.client.auction;

import java.util.*;

public class AuctionScanner {
    private List<AuctionItem> cachedAuctions;
    private long lastRefreshTime;
    private static final long CACHE_DURATION = 60000; // 1 minute

    public AuctionScanner() {
        this.cachedAuctions = new ArrayList<>();
        this.lastRefreshTime = 0;
    }

    public List<ProfitableFlip> findProfitableFlips(long minProfit, long maxBuyPrice) {
        List<ProfitableFlip> flips = new ArrayList<>();
        
        // Group auctions by item
        Map<String, List<AuctionItem>> itemMap = new HashMap<>();
        for (AuctionItem auction : cachedAuctions) {
            itemMap.computeIfAbsent(auction.getItemId(), k -> new ArrayList<>()).add(auction);
        }

        // Find profitable flips
        for (Map.Entry<String, List<AuctionItem>> entry : itemMap.entrySet()) {
            List<AuctionItem> auctions = entry.getValue();
            if (auctions.size() < 2) continue;

            // Sort by price
            auctions.sort(Comparator.comparingLong(AuctionItem::getPrice));

            AuctionItem cheapest = auctions.get(0);
            AuctionItem mostExpensive = auctions.get(auctions.size() - 1);

            long buyPrice = cheapest.getPrice();
            long sellPrice = mostExpensive.getPrice();
            long profit = (sellPrice - buyPrice) * cheapest.getQuantity();

            // Check if profitable and meets filters
            if (profit >= minProfit && buyPrice <= maxBuyPrice && sellPrice > buyPrice) {
                flips.add(new ProfitableFlip(
                        cheapest.getItemName(),
                        cheapest.getItemId(),
                        buyPrice,
                        sellPrice,
                        cheapest.getQuantity()
                ));
            }
        }

        // Sort by profit descending
        flips.sort(Comparator.comparingLong(ProfitableFlip::getProfit).reversed());
        return flips;
    }

    public void refreshAuctionData() {
        // Simulate fetching auction data
        // In a real implementation, this would query the Hypixel API
        cachedAuctions.clear();
        lastRefreshTime = System.currentTimeMillis();
        
        // Add sample data for testing
        cachedAuctions.add(new AuctionItem("ASPECT_OF_THE_END", "Aspect of the End", 5_000_000, 1));
        cachedAuctions.add(new AuctionItem("ASPECT_OF_THE_END", "Aspect of the End", 6_500_000, 1));
        cachedAuctions.add(new AuctionItem("LIVID_DAGGER", "Livid Dagger", 800_000, 1));
        cachedAuctions.add(new AuctionItem("LIVID_DAGGER", "Livid Dagger", 1_200_000, 1));
    }

    public boolean isCacheValid() {
        return System.currentTimeMillis() - lastRefreshTime < CACHE_DURATION;
    }
}