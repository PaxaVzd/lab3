import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Клас, що реалізує додаток для обчислення доходу від депозиту з використанням Swing.
 */
public class Menu extends JFrame {
    private JTextField y; // Поле для введення річної ставки відсотків
    private JTextField x; // Поле для введення суми депозиту
    private JTextField z; // Поле для введення строку депозиту в місяцях
    private JButton result; // Кнопка для обчислення результату
    private JTextArea field_for_results; // Поле для виведення результатів обчислень

    /**
     * Конструктор класу, встановлює заголовок вікна, його закриття при натисканні на хрестик,
     * забороняє зміну розміру вікна, та створює компоненти і розміщує їх на формі.
     */
    public Menu() {
        setTitle("Deposit Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        createUIComponents();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Метод для створення компонентів і розміщення їх на формі.
     */
    private void createUIComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        x = new JTextField(15); // Поле для введення суми депозиту
        y = new JTextField(15); // Поле для введення річної ставки відсотків
        z = new JTextField(15); // Поле для введення строку депозиту в місяцях
        result = new JButton("Calculate"); // Кнопка для обчислення результату
        field_for_results = new JTextArea(8, 20); // Поле для виведення результатів обчислень
        field_for_results.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Principal Amount (UAH):"), gbc);

        gbc.gridx = 1;
        panel.add(x, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Annual Interest Rate (%):"), gbc);

        gbc.gridx = 1;
        panel.add(y, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Term (Months):"), gbc);

        gbc.gridx = 1;
        panel.add(z, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(result, gbc);

        gbc.gridy = 4;
        panel.add(new JLabel("Result:"), gbc);

        gbc.gridy = 5;
        panel.add(new JScrollPane(field_for_results), gbc);

        getContentPane().add(panel);

        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAndSave();
            }
        });
    }

    /**
     * Метод для обчислення результатів та їх збереження.
     */
    private void calculateAndSave() {
        // Отримання введених користувачем даних
        String principalText = x.getText();
        String rateText = y.getText();
        String monthsText = z.getText();

        // Перевірка, чи всі поля заповнені
        if (principalText.isEmpty() || rateText.isEmpty() || monthsText.isEmpty()) {
            field_for_results.setText("Please fill in the fields.");
            return;
        }

        // Перевірка, чи введені дані є числами
        if (!principalText.matches("\\d*\\.?\\d*") || !rateText.matches("\\d*\\.?\\d*") || !monthsText.matches("\\d+")) {
            field_for_results.setText("Please enter numeric values.");
            return;
        }

        // Отримання числових значень для обчислення
        double principal = Double.parseDouble(principalText);
        double rate = Double.parseDouble(rateText);
        int months = Integer.parseInt(monthsText);

        // Обчислення результатів
        double resultWithoutCompounding = principal * (1 + (rate / 100) * (months / 12));
        double resultWithCompounding = principal * Math.pow(1 + (rate / 100) / 12, months);

        // Виведення результатів на екран
        field_for_results.setText("Without Compound: " + String.format("%.2f", resultWithoutCompounding) + " UAH\n"
                + "With Compound: " + String.format("%.2f", resultWithCompounding) + " UAH");

        // Збереження результатів у файл
        saveToFile("results.txt", field_for_results.getText());
    }

    /**
     * Метод для збереження результатів у файл.
     * @param fileName назва файлу
     * @param content вміст для збереження
     */
    private void saveToFile(String fileName, String content) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод, який запускає програму.
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
    }
}
