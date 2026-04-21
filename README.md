# Gilded Rose Kata — Java Refactoring

## The Challenge
Refactor legacy spaghetti code (deeply nested if-else) into a clean,
extensible design — without breaking existing behaviour.

## Design Decision — Strategy Map Pattern

Adding a new item type in the original code required **6 changes** across
multiple methods. One missed change = a silent bug.

The solution: a **strategy registry map** where each item's full behaviour
lives in one line.

```java
private static final Map<String, ItemUpdater> UPDATERS = Map.of(
    "Aged Brie",          (item, expired) -> adjustQuality(item, expired ? +2 : +1),
    "Backstage passes…",  GildedRose::updateBackstage,
    "Conjured Mana Cake", (item, expired) -> adjustQuality(item, expired ? -4 : -2)
);
```

**Adding a new item now = 1 line. Nothing else changes.**

## Key Design Choices

| Decision                              | Reason                                                                 |
|---------------------------------------|------------------------------------------------------------------------|
| `@FunctionalInterface ItemUpdater`    | Domain-specific name, primitive `boolean`, compile-time contract       |
| `LEGENDARY` Set + early return        | Legendary items skip `sellIn--` too — not just a no-op update          |
| `NORMAL` as fallback                  | Unknown item names treated as normal — can't enumerate all names       |
| `adjustQuality()` centralised         | Quality bounds `[0, 50]` enforced structurally — impossible to violate |
| `MIN_QUALITY / MAX_QUALITY` constants | Requirements explicitly state these limits — named for traceability    |

## Principles Demonstrated
- **Open/Closed** — open for extension, closed for modification
- **Single Responsibility** — each method does exactly one thing
- **DRY** — quality clamping and expired check each live in one place

## Run Tests
```bash
./gradlew test
```

## Run Approval Test (30 days)
```bash
./gradlew -q text --args 30
```
