# Skyblock AH Flipper

A Fabric mod for automatically finding and notifying you of profitable Auction House flips in Hypixel Skyblock.

## Features

- **Automatic AH Scanning**: Continuously scans the Auction House for profitable flip opportunities
- **Customizable Filters**: Set minimum profit thresholds and maximum buying prices
- **Interactive GUI**: User-friendly interface accessible via F6 key
- **Clickable Chat Messages**: Receive notifications with one-click access to auction listings
- **Profit Display**: See exactly how much profit each flip will generate
- **Price Comparison**: View buy and sell prices with formatted coin amounts

## Installation

1. Download the latest release from the [releases page](https://github.com/aciduI/skyblock-ah-flipper/releases)
2. Place the JAR file in your `.minecraft/mods` folder
3. Ensure you have Fabric Loader and Fabric API installed
4. Launch Minecraft with the Fabric profile

## Usage

### Opening the GUI
- Press **F6** to open the Skyblock AH Flipper interface

### Scanning for Flips
1. Set your minimum profit requirement (default: 100k coins)
2. Set your maximum buy price (default: 50M coins)
3. Click **"Scan Auctions"** to find profitable flips
4. Results are sorted by profit in descending order

### Viewing Flips
- **Scroll** through the list of profitable flips
- **Hover** over a flip to see detailed pricing
- **Click** on any flip to open its auction listing in your browser
- Profit amounts are color-coded (green for profit)

### Refreshing Data
- Click **"Refresh Data"** to update auction information
- The mod caches data for 1 minute to reduce API calls

## GUI Controls

| Control | Action |
|---------|--------|
| F6 | Open/Close GUI |
| Scroll | Navigate through flip list |
| Click on Item | Open auction page |
| Refresh Data | Update AH prices |
| Scan Auctions | Find new profitable flips |

## Requirements

- Minecraft 1.20.1
- Fabric Loader 0.14.24+
- Fabric API 0.90.0+
- Java 17+

## Configuration

Edit the following in the GUI:
- **Min Profit**: Minimum coins profit per flip (default: 100,000)
- **Max Price**: Maximum price to buy items at (default: 50,000,000)

## Chat Messages

When a profitable flip is found, a clickable chat message appears:
```
[AH Flipper] Item Name | 1.2M profit | [CLICK TO VIEW]
```

Hover over the message to see:
- Buy price
- Sell price
- Total profit

Click the message to open the auction page in your browser.

## Development

To build from source:

```bash
./gradlew build
```

The compiled mod will be in `build/libs/`

## License

MIT License - See LICENSE file for details

## Disclaimer

This mod is for educational purposes. Use responsibly and comply with Hypixel's Terms of Service.
