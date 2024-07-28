import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class FileHandlingGUI {
    public static void addBook() {
        JFrame frame1 = new JFrame("Add New Book");
        frame1.setSize(400, 400);
        frame1.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel ilabel1 = new JLabel("Enter Book ID:");
        JTextField bookid = new JTextField();
        JLabel ilabel2 = new JLabel("Enter Book Name:");
        JTextField bookName = new JTextField();
        JLabel ilabel3 = new JLabel("Enter Author:");
        JTextField author = new JTextField();
        JLabel ilabel4 = new JLabel("Enter Price:");
        JTextField price = new JTextField();

        inputPanel.add(ilabel1);
        inputPanel.add(bookid);
        inputPanel.add(ilabel2);
        inputPanel.add(bookName);
        inputPanel.add(ilabel3);
        inputPanel.add(author);
        inputPanel.add(ilabel4);
        inputPanel.add(price);

        JButton submit = new JButton("Submit");
        submit.setBackground(new Color(60, 179, 113));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Arial", Font.BOLD, 16));
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = bookid.getText();
                String name = bookName.getText();
                String aut = author.getText();
                double prc = Double.parseDouble(price.getText());

                try {
                    BufferedReader f = new BufferedReader(new FileReader("C:\\SUMMER TRAINING\\books.xls"));
                    BufferedWriter fh = new BufferedWriter(new FileWriter("C:\\SUMMER TRAINING\\temp.xls"));
                    boolean ext = true;
                    String st;
                    while ((st = f.readLine()) != null) {
                        fh.write(st + "\n");
                    }
                    fh.write("\n");
                    f.close();
                    while (ext) {
                        fh.write("" + id);
                        fh.write("\t" + name);
                        fh.write("\t" + aut);
                        fh.write("\tRs." + prc);
                        ext = false;
                        fh.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                deleteFile();
                renameFile();

                JOptionPane.showMessageDialog(null, "Details have been updated!");
                frame1.dispose();
            }

            void deleteFile() {
                File f = new File("C:\\SUMMER TRAINING\\books.xls");
                if (f.exists()) {
                    f.delete();
                }
            }

            void renameFile() {
                File f = new File("C:\\SUMMER TRAINING\\temp.xls");
                File fi = new File("C:\\SUMMER TRAINING\\books.xls");
                System.out.println(f.renameTo(fi));
            }
        });

        frame1.add(inputPanel, BorderLayout.CENTER);
        frame1.add(submit, BorderLayout.SOUTH);

        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void viewAll() {
        JFrame frame2 = new JFrame("View All Books");
        frame2.setSize(400, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);

        try {
            File f = new File("C:\\SUMMER TRAINING\\books.xls");
            BufferedReader fh = new BufferedReader(new FileReader("C:\\SUMMER TRAINING\\books.xls"));
            StringBuilder content = new StringBuilder();
            if (f.exists()) {
                int i;
                while ((i = fh.read()) != -1) {
                    content.append((char) i);
                }
                fh.close();
            } else {
                JOptionPane.showMessageDialog(null, "No records.");
            }
            textArea.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame2.add(scrollPane);

        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void specificBook() {
        JFrame frame3 = new JFrame("Search Book by ID");
        frame3.setSize(400, 400);
        frame3.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel input = new JLabel("Enter Book ID:");
        JTextField st = new JTextField();
        inputPanel.add(input);
        inputPanel.add(st);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(60, 179, 113));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookid = st.getText();
                try {
                    BufferedReader f = new BufferedReader(new FileReader("C:\\SUMMER TRAINING\\books.xls"));
                    String stx;
                    boolean found = false;
                    while ((stx = f.readLine()) != null) {
                        String[] records = stx.split("\t");
                        if (records[0].equals(bookid)) {
                            resultArea.setText("Book ID: " + records[0] + "\n" +
                                               "Book Name: " + records[1] + "\n" +
                                               "Author Name: " + records[2] + "\n" +
                                               "Price: " + records[3]);
                            found = true;
                            break;
                        }
                    }
                    f.close();
                    if (!found) {
                        resultArea.setText("Book not found.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame3.add(inputPanel, BorderLayout.NORTH);
        frame3.add(scrollPane, BorderLayout.CENTER);
        frame3.add(searchButton, BorderLayout.SOUTH);

        frame3.setVisible(true);
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Book Store Management System");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel display = new JLabel("Welcome to Book Store Management System");
        display.setHorizontalAlignment(SwingConstants.CENTER);
        display.setFont(new Font("Times New Roman", Font.BOLD, 36));
        display.setForeground(Color.BLACK);
        display.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton enter = new JButton("Add a New Book");
        JButton view = new JButton("View All Records");
        JButton check = new JButton("Show Details of a Book");

        enter.setBackground(new Color(100, 149, 237));
        enter.setForeground(Color.WHITE);
        enter.setFont(new Font("Arial", Font.BOLD, 16));
        view.setBackground(new Color(100, 149, 237));
        view.setForeground(Color.WHITE);
        view.setFont(new Font("Arial", Font.BOLD, 16));
        check.setBackground(new Color(100, 149, 237));
        check.setForeground(Color.WHITE);
        check.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 20, 0));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.add(enter);
        buttonPanel.add(view);
        buttonPanel.add(check);

        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAll();
            }
        });

        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                specificBook();
            }
        });
        
        enter.setPreferredSize(new Dimension(100, 40));
        view.setPreferredSize(new Dimension(100, 40));
        check.setPreferredSize(new Dimension(100, 40)); 

        frame.add(display, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
