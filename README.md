# Global Digital Bank (GDB) – Core Foundation

**GDB** is a layered, command‑line banking system. This repository contains the entity layer and the interactive test harness – the foundation for 30+ features including account management, transaction rules, analytics, and LangChain/LangGraph Agentic AI.

## Tech Stack
- **Java** (17+)
- Core Collections (`ArrayList`, `HashMap` for O(1) lookups)
- File I/O (planned for Phase 2)

## Architecture (Current Phase)
- **`model`** – Pure entities (`Account`). No I/O, no business logic.
- **`ui`** – Interactive test harness. Handles all user interaction and input validation.
- **Data Access** – Planned for Phase 2 (CSV persistence, transaction logs).

## Key Features (Implemented)
- ✅ Account creation (age ≥ 18, auto‑generated number)
- ✅ Deposit / Withdraw (validates positive amounts, insufficient balance)
- ✅ Balance inquiry
- ✅ List all accounts
- ✅ Robust input validation (`getSafeInt`/`getSafeDouble` loops)
- ✅ Name validation (non‑blank)
- ✅ Account type validation ("Savings"/"Current")

## Setup & Run
```bash
# Clone
git clone <url>

# Compile
javac src/main/java/com/gdb/model/Account.java src/main/java/com/gdb/ui/TestAccount.java

# Run
java -cp src/main/java com.gdb.ui.TestAccount