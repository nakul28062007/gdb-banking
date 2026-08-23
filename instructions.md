# `instruction.md` – Global Digital Bank (GDB) Project

**Purpose:** This file contains the full context of my GDB project – what I’ve built, my coding style, my output preferences, and my current stage. Upload it to any new AI chat so it can understand my work without re‑explaining everything. I update this file whenever I complete a new phase.

---

## 1. Project Overview

- **System:** Global Digital Bank (GDB) – a CLI-based banking platform with 30+ features (Base, Extended, AI).
- **Phases:** Core (Account class, test harness) → Extended (persistence, transactions, analytics, security) → AI (LangChain/LangGraph).
- **Activities 1 & 2** (current): Build a pure `Account` entity and a test harness to verify it.

---

## 2. Current Stage (Completed)

- ✅ **Activity 1:** `Account.java` – a pure entity class with:
  - Private fields: `accountNumber`, `name`, `age`, `balance`, `accountType`, `status`.
  - Constructor, getters/setters, `deposit()`, `withdraw()`.
  - No display logic – returns `boolean` for success/failure.
  - Validation: deposit/withdraw amount `<= 0` rejected; withdraw checks `amount > balance`.
- ✅ **Activity 2 (my version):** `TestAccount.java` – an interactive menu-driven test harness that:
  - Allows creating accounts, depositing, withdrawing, balance inquiry, listing all accounts.
  - Uses robust input validation (`getSafeInt`, `getSafeDouble`) to prevent crashes.
  - Validates name (non‑blank), age (≥18), account type ("Savings"/"Current").
  - Handles missing accounts gracefully (returns `-1` and continues).
  - Displays formatted output with `printf` and `%n`.

**Note:** I have deliberately chosen an **interactive test harness** over the fixed, hard‑coded sequence described in Activity 2. I believe my interactive version is more user‑friendly and better demonstrates the functionality. I only care about meeting functional requirements, not exact output formatting if my style is superior.

---

## 3. Code Overview

### `Account.java`

```java
public class Account {
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    // Constructor, getters, deposit(), withdraw(), etc.
}
```

- **Key methods:**
  - `deposit(double amount)` – returns `true` if amount > 0, else `false`.
  - `withdraw(double amount)` – returns `true` if amount > 0 and ≤ balance, else `false`.
- **Status:** Default `"Active"`; set to `"Inactive"` later.

### `TestAccount.java`

- **Static lists:** `ArrayList<Account> accounts` and `HashMap<Integer, Integer> accountsIndex` for O(1) lookup.
- **Input helpers:**
  - `getSafeInt(Scanner, String)` – loops until a valid integer is entered.
  - `getSafeDouble(Scanner, String)` – loops until a valid double is entered.
  - `validateName(String)` – rejects blank/whitespace-only names.
  - `askAndCheckAccNum(Scanner)` – prompts for account number, returns its index or `-1`.
- **Main loop:** Interactive menu with choices 1–5 (Create, Deposit, Withdraw, Balance, List All). Continues until user enters `n`.

---

## 4. Coding Style & Preferences

### Principles I follow (when better than the spec)

- **Separation of concerns:** Entity classes (like `Account`) contain **no I/O**. All user interaction stays in the test/UI layer.
- **Robust input handling:** Always validate user input – never assume it’s correct. Use loops with `try-catch` to recover from bad input.
- **Descriptive error messages:** Tell the user exactly what went wrong (e.g., `"Invalid amount"` vs. just `"Error"`).
- **User‑friendly UX:** Prefer **interactive menus** over fixed test sequences. Let the user explore the functionality.
- **Efficiency:** Use `HashMap` for fast account lookup. O(1) is better than O(n) linear search.
- **Formatting:** Use `printf` with `%n` for platform‑independent newlines. Consistent spacing and alignment.
- **Readability:** Use meaningful variable names, comment only where necessary, keep methods short and focused.

### When I deviate from the spec

- If the spec requires a rigid output format or a hard‑coded sequence, but I have a more flexible and user‑friendly interactive version, I will **ignore the spec’s output style**.
- I still ensure **all functional requirements** are met – the code must work correctly.

---

## 5. Output Preferences

- **Prompt style:** Use `System.out.print(prompt)` so the user types on the same line.
- **Success/failure messages:** Always include the reason and show the updated balance when relevant.
- **Account listing:** Use a table‑like format with pipes (`|`) and aligned columns.
- **Separators:** Use `===` banners to mark sections (optional, but clean).

**Example of my preferred output:**

```
==============================================================
                    GLOBAL DIGITAL BANK - ACCOUNT TEST
===============================================================

1. Create Account.
2. Deposit Money.
3. Withdraw Money.
4. Check Balance.
5. Display All Accounts
Enter your choice: 1

Enter name: John Doe
Enter age: 25
Enter Account Type: Savings
Initial deposit amount: 1000
Account NO: 1001 | John Doe ( 25 yrs ) | Savings | 1000.00 | Active
```

---

## 6. Known Issues / Pending Fixes (as of last update)

- **`Account.withdraw()`** – currently uses `balance =- amount;` (assignment, not subtraction). Must change to `balance -= amount;`.
- **Main loop condition** – uses `ans.equals("y")` (case‑sensitive). Should use `equalsIgnoreCase` for better UX.
- **Extra `sc.nextLine()`** after `getSafeInt` in the menu – causes an unnecessary pause. Remove it.

These will be fixed in the next iteration.

---

## 7. Next Steps (Future Phases)

- **Phase 1 (Core Foundation):**
  - Add file persistence (`accounts.csv`, `transactions.log`).
  - Implement `Close Account` (set status to `"Inactive"`).
  - Build a full CLI menu (not just a test harness) with all Base features.
- **Phase 2 (Extended Features):**
  - Search, list, reopen, rename, delete, transaction history, transfers, analytics, etc.
- **Phase 3 (Agentic AI):**
  - Integrate LangChain/LangGraph for fraud detection, financial advisor, NLQI, predictive dashboard.

I plan to maintain my coding style and robust validation throughout.

---

## 8. How to Use This File

- Copy this file into the root of your project as `instruction.md`.
- Update it after each major phase (add new classes, describe changes).
- When starting a new chat with any AI, upload this file and say: *"Please read instruction.md to understand my project context."*

---

## 9. suggested folder structure
gdb-banking/
├── .gitignore
├── README.md
├── instruction.md          (your context file – keep it updated)
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── gdb/
│                   ├── model/
│                   │   └── Account.java
│                   ├── ui/
│                   │   └── TestAccount.java   (later rename to BankingCLI)
│                   ├── service/               (future: BankingService)
│                   ├── dao/                   (future: FileManager)
│                   └── util/                  (future: InputHelper, constants)
└── resources/                                 (future: accounts.csv, logs)

**Last updated:** 2026-08-23  
**Current version:** After completing Activities 1 & 2 (my interactive version).