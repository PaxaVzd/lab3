import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Menu extends JFrame {
    private JTextField y;
    private JTextField x;
    private JTextField z;
    private JButton result;
    private JTextArea field_for_results;

    public Menu() {
        setTitle("Deposit Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Заборонити зміну розміру вікна
        createUIComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        x = new JTextField(15); // Змінивши розмір текстового поля
        y = new JTextField(15); // Змінивши розмір текстового поля
        z = new JTextField(15); // Змінивши розмір текстового поля
        result = new JButton("Calculate");
        field_for_results = new JTextArea(8, 20); // Змінивши розмір текстового поля
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

    private void calculateAndSave() {
        double principal = Double.parseDouble(x.getText());
        double rate = Double.parseDouble(y.getText());
        int months = Integer.parseInt(z.getText());

        double resultWithoutCompounding = principal * (1 + (rate / 100) * (months / 12));
        double resultWithCompounding = principal * Math.pow(1 + (rate / 100) / 12, months);

        field_for_results.setText("Without Compound: " + String.format("%.2f", resultWithoutCompounding) + " UAH\n"
                + "With Compound: " + String.format("%.2f", resultWithCompounding) + " UAH");

        saveToFile("results.txt", field_for_results.getText());
    }

    private void saveToFile(String fileName, String content) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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
