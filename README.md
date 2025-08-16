DigitalWalletSystem

A simple Java Swing-based Digital Wallet Application that allows users to create accounts, log in, and perform digital wallet transactions such as sending money, remittance, salami transfers, and bank transfers. It also supports a money request system and maintains transaction histories for all accounts.

🚀 Features

Account Management

User sign up and secure login with phone number & password.

Individual account balance and transaction history.

Transactions

Send Money – transfer funds to another wallet user.

International Remittance – send money outside the country.

Send Salami – send festive gift money.

Bank Transfer (LenDen) – transfer wallet balance to a bank account.

Money Request System

Instead of direct receiving, users send a money request to another account.

Requested user can approve and complete the transaction.

Transaction History

Both sender and receiver see detailed logs.

Timestamp included for every transaction.

🛠️ Tech Stack

Language: Java

GUI Framework: Swing (with CardLayout)

Data Handling: ArrayList (to store user accounts & transaction history)

IDE Recommendation: NetBeans / IntelliJ / Eclipse

📂 How to Run

Clone or download this repository.

Open the project in your Java IDE.

Run the DigitalWalletSystem.java file.

Sign up for a new account or log in with existing credentials.

Explore the features: send money, request money, check history, etc.

📌 Example Use Case

User A signs up with phone 017XXXXXXXX.

User B signs up with phone 018XXXXXXXX.

User A selects Send Money, enters User B’s phone and amount.

User B’s history shows “Received from 017XXXXXXXX” while User A’s history shows “Sent to 018XXXXXXXX”.

If User B needs funds, they use Request Money from User A.

📖 Future Improvements

Database integration (MySQL/SQLite) instead of ArrayList.

OTP verification for login.

Mobile app version (JavaFX or Android).

👨‍💻 Author

Name: Mehedi Hasan Emon

Project: DigitalWalletSystem (Java OOP Project)
This project is ideal for learning Java Swing, object-oriented programming, and basic wallet system implementation in a desktop application environment.
