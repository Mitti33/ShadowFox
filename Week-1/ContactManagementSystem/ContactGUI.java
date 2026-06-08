import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class ContactGUI{
    static ContactManager contactManager = new ContactManager();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Contacts");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(201, 211, 98));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Contacts");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(new Color(0, 132, 170));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnView = new JButton("  My Contacts  ");
        JButton btnAdd = new JButton("  Add Contact  ");
        JButton btnDelete = new JButton("Delete Contact");
        JButton btnEdit = new JButton("  Edit Contact  ");
        JButton btnSearch = new JButton("Search Contact");

        btnView.setBorderPainted(true);
        btnView.setContentAreaFilled(true);
        btnView.setBackground(new Color(201, 190, 98));
        btnView.setFocusPainted(false);
        btnView.setFont(new Font("Arial", Font.BOLD, 30));

        btnAdd.setBorderPainted(true);
        btnAdd.setContentAreaFilled(true);
        btnAdd.setBackground(new Color(201, 190, 98));
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 30));

        btnDelete.setBorderPainted(true);
        btnDelete.setContentAreaFilled(true);
        btnDelete.setBackground(new Color(201, 190, 98));
        btnDelete.setFocusPainted(false);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 30));

        btnEdit.setBorderPainted(true);
        btnEdit.setContentAreaFilled(true);
        btnEdit.setBackground(new Color(201, 190, 98));
        btnEdit.setFocusPainted(false);
        btnEdit.setFont(new Font("Arial", Font.BOLD, 30));

        btnSearch.setBorderPainted(true);
        btnSearch.setContentAreaFilled(true);
        btnSearch.setBackground(new Color(201, 190, 98));
        btnSearch.setFocusPainted(false);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 30));

        btnView.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Logic

        btnView.addActionListener(e -> {
            new ViewContactsFrame(contactManager);
        });
        btnAdd.addActionListener(e -> {
            new AddContactFrame(contactManager);
        });
        btnSearch.addActionListener(e -> {
            new SearchContactFrame(contactManager);
        });
        btnDelete.addActionListener(e -> {
            new DeleteContactFrame(contactManager);
        });
        btnEdit.addActionListener(e -> {
            new EditContactFrame(contactManager);
        });

        panel.add(Box.createVerticalStrut(40));
        panel.add(title);
        panel.add(Box.createVerticalStrut(50));
        panel.add(btnView);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnAdd);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnDelete);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnEdit);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnSearch);

        frame.add(panel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class ViewContactsFrame extends JFrame{

    JLabel slLabel;
    JLabel nameLabel;
    JLabel phoneLabel;
    JLabel emailLabel;

    ContactManager contactManager;

    Color g = new Color(0, 200, 0);

    public ViewContactsFrame(ContactManager contactManager) {
        this.contactManager = contactManager;

        setTitle("My Contacts");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        int size = contactManager.size();

        JLabel titleLabel = new JLabel("Total Contacts: " + size,SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 150, 0));

        add(titleLabel, BorderLayout.NORTH);

        if(size == 0){
            JLabel messageLabel = new JLabel("No Contacts Found!", SwingConstants.CENTER);
            messageLabel.setFont(new Font("Ariel", Font.ITALIC, 45));
            messageLabel.setForeground(Color.red);
            add(messageLabel);
            setVisible(true);
            return;
        }

        // Column Names
        String[] columns = {
                "Sl.No.",
                "Name",
                "Phone",
                "Email"
        };

        // Table Model
        DefaultTableModel model =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        // Add Contact Data
        int sl = 1;

        for(Contact c : contactManager.contacts) {

            model.addRow(new Object[] {
                    sl,
                    c.name,
                    c.phone,
                    c.email
            });

            sl++;
        }

        // Create Table
        JTable table = new JTable(model);

        table.setRowHeight(30);

        table.setFont(
                new Font("Arial",
                        Font.PLAIN,
                        14));

        table.getTableHeader().setFont(
                new Font("Arial",
                        Font.BOLD,
                        16));

        table.getTableHeader().setBackground(new Color(0, 200, 0));
        table.getTableHeader().setForeground(Color.WHITE);
        
        table.setSelectionBackground(
                new Color(201, 211, 98));

        // Scroll Pane
        JScrollPane scrollPane =
                new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);


        setVisible(true);
    }
}

class AddContactFrame extends JFrame{

    JLabel nameLabel;
    JLabel phoneLabel;
    JLabel emailLabel;
    JLabel messageLabel;

    JTextField nameField;
    JTextField phoneField;
    JTextField emailField;

    JButton btnSave;

    ContactManager contactManager;

    public AddContactFrame(ContactManager contactManager) {
        this.contactManager = contactManager;

        setTitle("Add Contact");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        nameLabel = new JLabel("Name:");
        phoneLabel = new JLabel("Phone:");
        emailLabel = new JLabel("Email:");
        messageLabel = new JLabel("");

        nameLabel.setFont(new Font("Arial", Font.ITALIC, 45));
        phoneLabel.setFont(new Font("Arial", Font.ITALIC, 45));
        emailLabel.setFont(new Font("Arial", Font.ITALIC, 45));
        messageLabel.setForeground(Color.red);

        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);

        btnSave = new JButton("SAVE");
        btnSave.setBackground(new Color(0, 200, 0)); //bright green color
        btnSave.setForeground(Color.white);
        btnSave.setFont(new Font("Arial", Font.PLAIN, 35));

        btnSave.addActionListener(e -> {
            Contact c = new Contact(
                nameField.getText(),
                phoneField.getText(),
                emailField.getText()
            );

            String message = contactManager.addContact(c);

            if(message.isEmpty()){
                messageLabel.setText("Contact saved successfully.");
            } else {
                messageLabel.setText(message);
            }

            revalidate();
            repaint();
            System.out.println(c);
        });

        add(nameLabel);
        add(nameField);
        add(phoneLabel);
        add(phoneField);
        add(emailLabel);
        add(emailField);
        add(btnSave);
        add(messageLabel);

        setVisible(true);
    }
}

class DeleteContactFrame extends JFrame {

    JTextField phoneField;

    JLabel resultLabel;

    JButton btnDelete;

    ContactManager contactManager;

    public DeleteContactFrame(ContactManager contactManager) {

        this.contactManager = contactManager;

        setTitle("Delete Contact");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(4, 1, 10, 10));

        phoneField = new JTextField();

        btnDelete = new JButton("DELETE");
        btnDelete.setForeground(Color.white);
        btnDelete.setBackground(new Color(0, 200, 0));
        btnDelete.setFont(new Font("Arial", Font.PLAIN, 30));

        resultLabel = new JLabel("", SwingConstants.CENTER);

        btnDelete.addActionListener(e -> {

            String phone = phoneField.getText();

            boolean deleted =
                    contactManager.deleteContact(phone);

            if(deleted) {

                resultLabel.setForeground(
                        new Color(0, 150, 0));

                resultLabel.setText(
                        "Contact deleted successfully!");

            }
            else {

                resultLabel.setForeground(Color.RED);

                resultLabel.setText(
                        "Contact not found!");
            }
        });

        add(new JLabel(
                "Enter Phone Number:",
                SwingConstants.CENTER));

        add(phoneField);

        add(btnDelete);

        add(resultLabel);

        setVisible(true);
    }
}

class EditContactFrame extends JFrame {

    JTextField searchPhoneField;

    JTextField nameField;
    JTextField phoneField;
    JTextField emailField;

    JButton btnFind;
    JButton btnUpdate;

    JLabel messageLabel;

    ContactManager contactManager;

    Contact currentContact;

    public EditContactFrame(ContactManager contactManager) {

        this.contactManager = contactManager;

        setTitle("Edit Contact");

        setSize(500, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(7, 2, 10, 10));

        searchPhoneField = new JTextField();

        nameField = new JTextField();

        phoneField = new JTextField();

        emailField = new JTextField();

        btnFind = new JButton("FIND");
        btnFind.setForeground(Color.white);
        btnFind.setBackground(new Color(0, 200, 0));
        btnFind.setFont(new Font("Arial", Font.PLAIN, 30));

        btnUpdate = new JButton("UPDATE");
        btnUpdate.setForeground(Color.white);
        btnUpdate.setBackground(new Color(0, 200, 0));
        btnUpdate.setFont(new Font("Arial", Font.PLAIN, 30));

        messageLabel = new JLabel("");

        btnFind.addActionListener(e -> {

            currentContact =
                contactManager.searchContact(
                    searchPhoneField.getText());

            if(currentContact == null) {

                messageLabel.setText(
                        "Contact not found.");

                return;
            }

            nameField.setText(
                    currentContact.name);

            phoneField.setText(
                    currentContact.phone);

            emailField.setText(
                    currentContact.email);

            messageLabel.setText(
                    "Contact loaded.");
        });

        btnUpdate.addActionListener(e -> {

            if(currentContact == null) {

                messageLabel.setText("Search first.");

                return;
            }

            boolean updated =
                contactManager.updateContact(

                    currentContact.phone,

                    nameField.getText(),

                    phoneField.getText(),

                    emailField.getText()
                );

            if(updated) {

                messageLabel.setText("Contact updated.");
            }
            else {

                messageLabel.setText("Update failed.");
            }
        });

        add(new JLabel("Search Phone:"));
        add(searchPhoneField);

        add(btnFind);
        add(new JLabel(""));

        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("Phone:"));
        add(phoneField);

        add(new JLabel("Email:"));
        add(emailField);

        add(btnUpdate);
        add(messageLabel);

        setVisible(true);
    }
}

class SearchContactFrame extends JFrame {

    JLabel phoneLabel;
    JLabel resultLabel;

    JTextField phoneField;

    JButton btnSearch;

    ContactManager contactManager;

    public SearchContactFrame(ContactManager contactManager) {

        this.contactManager = contactManager;

        setTitle("Search Contact");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(4, 1, 10, 10));

        phoneLabel = new JLabel("Enter Phone:", SwingConstants.CENTER);

        phoneLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));

        phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.BOLD, 28));
        phoneField.setForeground(new Color(0, 200, 0));

        btnSearch = new JButton("SEARCH");
        btnSearch.setForeground(Color.white);
        btnSearch.setBackground(new Color(0, 200, 0));
        btnSearch.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));

        btnSearch.addActionListener(e -> {

            String phone = phoneField.getText();

            Contact c = contactManager.searchContact(phone);

            if(c == null) {

                resultLabel.setForeground(Color.RED);
                resultLabel.setText("Contact not found!");

            } else {

                resultLabel.setForeground(new Color(0, 150, 0));

                resultLabel.setText(
                    "<html>Name: " + c.name +
                    "<br>Phone: " + c.phone +
                    "<br>Email: " + c.email +
                    "</html>"
                );
            }
        });

        add(phoneLabel);
        add(phoneField);

        add(btnSearch);
        add(new JLabel(""));

        add(resultLabel);

        setVisible(true);
    }
}

