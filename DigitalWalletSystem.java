/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.digitalwalletsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DigitalWalletSystem extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private JTextField loginPhone;
    private JPasswordField loginPass;

    private JTextField signUpName, signUpPhone;
    private JPasswordField signUpPass;

    private final ArrayList<Account> accounts = new ArrayList<>();
    private Account currentUser = null;

    private final Font headingFont = new Font("Segoe UI", Font.BOLD, 26);
    private final Font buttonFont  = new Font("Segoe UI", Font.BOLD, 14);
    private final Font linkFont    = new Font("Segoe UI", Font.PLAIN, 12);

    private JLabel welcomeLabel;
    private JLabel balanceLabel;

    public DigitalWalletSystem() {
        setTitle("DigitalWalletSystem");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel loginPanel     = buildLoginPanel();
        JPanel signUpPanel    = buildSignUpPanel();
        JPanel dashboardPanel = buildDashboardPanel();

        mainPanel.add(loginPanel, "signin");
        mainPanel.add(signUpPanel, "signup");
        mainPanel.add(dashboardPanel, "dashboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "signin");
    }

    private JPanel buildLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("DigitalWalletSystem", SwingConstants.CENTER);
        title.setFont(headingFont);
        title.setForeground(new Color(0, 137, 209));
        loginPanel.add(title, BorderLayout.NORTH);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridLayout(3, 1, 10, 10));
        form.setBackground(Color.WHITE);

        loginPhone = new JTextField();
        loginPhone.setBorder(BorderFactory.createTitledBorder("Phone Number"));
        form.add(loginPhone);

        loginPass = new JPasswordField();
        loginPass.setBorder(BorderFactory.createTitledBorder("Password"));
        form.add(loginPass);

        JButton loginBtn = createStyledButton("Login", buttonFont, 35, 5);
        loginBtn.addActionListener(e -> handleLogin());
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(loginBtn);
        form.add(btnPanel);

        wrapper.add(form);
        loginPanel.add(wrapper, BorderLayout.CENTER);

        JLabel signUpLink = new JLabel("Don't have account? Sign Up", SwingConstants.CENTER);
        signUpLink.setFont(linkFont);
        signUpLink.setForeground(Color.BLUE);
        signUpLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { cardLayout.show(mainPanel, "signup"); }
        });
        loginPanel.add(signUpLink, BorderLayout.SOUTH);

        return loginPanel;
    }

    private JPanel buildSignUpPanel() {
        JPanel signUpPanel = new JPanel(new BorderLayout());
        signUpPanel.setBackground(Color.WHITE);

        JLabel signUpTitle = new JLabel("Sign Up", SwingConstants.CENTER);
        signUpTitle.setFont(headingFont);
        signUpTitle.setForeground(new Color(0, 137, 209));
        signUpPanel.add(signUpTitle, BorderLayout.NORTH);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridLayout(4, 1, 10, 10));
        form.setBackground(Color.WHITE);

        signUpName = new JTextField();
        signUpName.setBorder(BorderFactory.createTitledBorder("Name"));
        form.add(signUpName);

        signUpPhone = new JTextField();
        signUpPhone.setBorder(BorderFactory.createTitledBorder("Phone Number"));
        form.add(signUpPhone);

        signUpPass = new JPasswordField();
        signUpPass.setBorder(BorderFactory.createTitledBorder("Password"));
        form.add(signUpPass);

        JButton signUpBtn = createStyledButton("Sign Up", buttonFont, 35, 5);
        signUpBtn.addActionListener(e -> handleSignUp());
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(signUpBtn);
        form.add(btnPanel);

        wrapper.add(form);
        signUpPanel.add(wrapper, BorderLayout.CENTER);

        return signUpPanel;
    }

    private JPanel buildDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        JPanel top = new JPanel(new GridLayout(2,1));
        top.setBackground(Color.WHITE);

        welcomeLabel = new JLabel("Welcome to DigitalWalletSystem", SwingConstants.CENTER);
        welcomeLabel.setFont(headingFont);
        welcomeLabel.setForeground(new Color(0, 137, 209));

        balanceLabel = new JLabel("Balance: 0.0", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        top.add(welcomeLabel);
        top.add(balanceLabel);
        dashboardPanel.add(top, BorderLayout.NORTH);

        JPanel gridWrapper = new JPanel(new GridBagLayout());
        gridWrapper.setBackground(Color.WHITE);

        JPanel gridPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        gridPanel.setBackground(Color.WHITE);

        gridPanel.add(createActionButton("Send Money",          () -> sendMoney(true)));
        gridPanel.add(createActionButton("Receive Money",       () -> receiveMoney()));
        gridPanel.add(createActionButton("Send Remittance",     () -> sendRemittance()));
        gridPanel.add(createActionButton("Send Salami",         () -> simpleOutflow("Send Salami", true)));
        gridPanel.add(createActionButton("LenDen to Bank",      () -> bankTransfer()));
        gridPanel.add(createActionButton("Add Money",           () -> addMoneyFromMerchant()));
        gridPanel.add(createActionButton("History",             () -> showHistoryDialog()));

        gridWrapper.add(gridPanel);
        dashboardPanel.add(gridWrapper, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        JButton logoutBtn = createStyledButton("Logout", buttonFont, 35, 5);
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            JOptionPane.showMessageDialog(this, "Logged out.");
            cardLayout.show(mainPanel, "signin");
        });
        bottom.add(logoutBtn);
        dashboardPanel.add(bottom, BorderLayout.SOUTH);

        return dashboardPanel;
    }

    private void handleLogin() {
        String phone = loginPhone.getText().trim();
        String pass  = new String(loginPass.getPassword());

        for (Account acc : accounts) {
            if (acc.phone.equals(phone) && acc.password.equals(pass)) {
                currentUser = acc;
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + acc.name);
                refreshDashboardHeader();
                cardLayout.show(mainPanel, "dashboard");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Invalid phone or password", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleSignUp() {
        String name  = signUpName.getText().trim();
        String phone = signUpPhone.getText().trim();
        String pass  = new String(signUpPass.getPassword());

        if (name.isEmpty() || phone.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (findAccountByPhone(phone) != null) {
            JOptionPane.showMessageDialog(this, "Phone already registered", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        accounts.add(new Account(name, phone, pass));
        JOptionPane.showMessageDialog(this, "Account created successfully!");
        cardLayout.show(mainPanel, "signin");
    }

    private void sendMoney(boolean requirePassword) {
        if (!ensureLoggedIn()) return;
        if (requirePassword && !confirmPassword()) return;

        String recipientPhone = promptString("Enter recipient phone:");
        if (recipientPhone == null) return;

        Account recipient = findAccountByPhone(recipientPhone);
        if (recipient == null) {
            JOptionPane.showMessageDialog(this, "Recipient not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Double amount = promptAmount("Enter amount to send:");
        if (amount == null || amount <= 0) return;

        if (currentUser.balance < amount) {
            JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentUser.balance -= amount;
        recipient.balance += amount;

        addHistory(currentUser, "Send Money to " + recipient.phone, amount);
        addHistory(recipient, "Received Money from " + currentUser.phone, amount);

        refreshDashboardHeader();
        JOptionPane.showMessageDialog(this, "Send successful!\nYour Balance: " + currentUser.balance);
    }

    private void sendRemittance() {
        if (!ensureLoggedIn()) return;
        if (!confirmPassword()) return;

        String recipientPhone = promptString("Enter recipient phone:");
        if (recipientPhone == null) return;

        String zip = promptString("Enter recipient ZIP code:");
        if (zip == null) return;

        Double amount = promptAmount("Enter amount to send:");
        if (amount == null || amount <= 0) return;

        if (currentUser.balance < amount) {
            JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentUser.balance -= amount;
        addHistory(currentUser, "Sent Remittance to " + recipientPhone + " ZIP:" + zip, amount);

        refreshDashboardHeader();
        JOptionPane.showMessageDialog(this, "Remittance sent successfully!\nYour Balance: " + currentUser.balance);
    }

    private void receiveMoney() {
        if (!ensureLoggedIn()) return;

        String senderPhone = promptString("Enter sender phone:");
        if (senderPhone == null) return;

        Double amount = promptAmount("Enter amount to receive:");
        if (amount == null || amount <= 0) return;

        Account sender = findAccountByPhone(senderPhone);
        if (sender == null) {
            JOptionPane.showMessageDialog(this, "No account exists with this number.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (sender.balance < amount) {
            JOptionPane.showMessageDialog(this, "Sender has insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sender.balance -= amount;
        currentUser.balance += amount;

        addHistory(sender, "Send Money to " + currentUser.phone, amount);
        addHistory(currentUser, "Received Money from " + sender.phone, amount);

        refreshDashboardHeader();
        JOptionPane.showMessageDialog(this, "Received successfully!\nYour Balance: " + currentUser.balance);
    }

    private void addMoneyFromMerchant() {
        if (!ensureLoggedIn()) return;

        String merchant = promptString("Enter Merchant Name:");
        if (merchant == null) return;

        Double amount = promptAmount("Enter amount to add:");
        if (amount == null || amount <= 0) return;

        currentUser.balance += amount;
        addHistory(currentUser, "Added Money from Merchant: " + merchant, amount);

        refreshDashboardHeader();
        JOptionPane.showMessageDialog(this, "Money added successfully!\nYour Balance: " + currentUser.balance);
    }

    private void simpleOutflow(String type, boolean requirePassword) {
        if (!ensureLoggedIn()) return;
        if (requirePassword && !confirmPassword()) return;

        Double amount = promptAmount("Enter amount:");
        if (amount == null || amount <= 0) return;

        if (currentUser.balance < amount) {
            JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentUser.balance -= amount;
        addHistory(currentUser, type, amount);

        refreshDashboardHeader();
        JOptionPane.showMessageDialog(this, type + " successful!\nYour Balance: " + currentUser.balance);
    }

    private void bankTransfer() {
        if (!ensureLoggedIn()) return;
        if (!confirmPassword()) return;

        String bankNumber = promptString("Enter bank account number:");
        if (bankNumber == null) return;

        Double amount = promptAmount("Enter amount to transfer:");
        if (amount == null || amount <= 0) return;

        if (currentUser.balance < amount) {
            JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentUser.balance -= amount;
        addHistory(currentUser, "Transferred to bank " + bankNumber, amount);

        refreshDashboardHeader();
        JOptionPane.showMessageDialog(this, "Bank transfer successful!\nYour Balance: " + currentUser.balance);
    }

    private void showHistoryDialog() {
        if (!ensureLoggedIn()) return;
        JTable table = new JTable(currentUser.history);
        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(520, 260));
        JOptionPane.showMessageDialog(this, sp, "Transaction History — " + currentUser.name, JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshDashboardHeader() {
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.name + " (" + currentUser.phone + ")");
            balanceLabel.setText("Balance: " + currentUser.balance);
        } else {
            welcomeLabel.setText("Welcome to DigitalWalletSystem");
            balanceLabel.setText("Balance: 0.0");
        }
    }

    private boolean ensureLoggedIn() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please login first.", "Error", JOptionPane.ERROR_MESSAGE);
            cardLayout.show(mainPanel, "signin");
            return false;
        }
        return true;
    }

    private boolean confirmPassword() {
        String pass = JOptionPane.showInputDialog(this, "Enter Password:");
        if (pass == null) return false;
        if (!pass.equals(currentUser.password)) {
            JOptionPane.showMessageDialog(this, "Authentication failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private String promptString(String prompt) {
        String s = JOptionPane.showInputDialog(this, prompt);
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        return s;
    }

    private Double promptAmount(String prompt) {
        String amtStr = JOptionPane.showInputDialog(this, prompt);
        if (amtStr == null) return null;
        amtStr = amtStr.trim();
        if (amtStr.isEmpty()) return null;
        try { return Double.parseDouble(amtStr); }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void addHistory(Account acc, String type, double amount) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        acc.history.addRow(new Object[]{date, type, amount});
    }

    private Account findAccountByPhone(String phone) {
        for (Account a : accounts) {
            if (a.phone.equals(phone)) return a;
        }
        return null;
    }

    private JButton createStyledButton(String text, Font font, int height, int padding) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(new Dimension(160, height));
        button.setBackground(new Color(0, 137, 209));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(padding, 10, padding, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(new Color(0, 180, 255)); }
            public void mouseExited (MouseEvent e) { button.setBackground(new Color(0, 137, 209)); }
        });
        return button;
    }

    private JButton createActionButton(String text, Runnable action) {
        JButton btn = createStyledButton(text, buttonFont, 45, 5);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    static class Account {
        final String name;
        final String phone;
        final String password;
        double balance = 0.0;
        final DefaultTableModel history =
                new DefaultTableModel(new String[]{"Date", "Transaction", "Amount"}, 0);

        Account(String name, String phone, String password) {
            this.name = name;
            this.phone = phone;
            this.password = password;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DigitalWalletSystem().setVisible(true));
    }
}
