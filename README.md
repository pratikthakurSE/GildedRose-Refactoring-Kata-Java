# Gilded Rose — Refactored (Java)

A clean, SRP-compliant refactor of the classic **Gilded Rose Kata**. The original
tangled `updateQuality()` method has been replaced with a **Strategy + Factory**
design where every item type owns its own update logic.

---

## Solution Highlights

- **Single Responsibility Principle** — each class has one reason to change.
- **Open/Closed Principle** — add a new item type with one new class + one line
  in the factory; no existing code is modified.
- **Strategy Pattern** — `ItemUpdater` defines the update contract.
- **Factory Pattern** — `ItemUpdaterFactory` resolves item name → updater.
- **Template Method** — shared `adjustQuality()` lives in the abstract base.
- **Quality clamped** to `[0, 50]` for all non-legendary items.

---

## Architecture

```
GildedRose (orchestrator)
    │  decrement sellIn (except Sulfuras)
    └── ItemUpdaterFactory.resolve(name) ──► ItemUpdater (abstract base)
                                                   ▲
         ┌──────────────┬──────────────┬───────────┴──────┬──────────────┐
   NormalUpdater  AgedBrieUpdater  BackstageUpdater  SulfurasUpdater  ConjuredUpdater
```

## Run the Tests

```bash
./gradlew -q test
```

## Run the TextTest Fixture from Command-Line

```
./gradlew -q text
```

### Specify Number of Days

For e.g. 10 days:

```
./gradlew -q text --args 10
```

To reproduce the approval run used for submission (30 days):

```
./gradlew -q text --args 30
```
