
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

public class Mainframe extends JFrame {

    // __ CONSTANTS ________________________________________________________________________

    // threshold
    private static final int LOW_STOCK_THRESHOLD = LowStockRenderer.LOW_STOCK_THRESHOLD;

    // colors 
    private static final Color C_HEADER = new Color(33, 47, 61);   // dark navy
    private static final Color C_ADD = new Color(39, 174, 96);   // green
    private static final Color C_UPDATE = new Color(41, 128, 185);   // blue
    private static final Color C_DELETE = new Color(192, 57, 43);   // red
    private static final Color C_CLEAR = new Color(127, 140, 141);  // grey
    private static final Color C_PANEL = new Color(245, 246, 250);  // near-white
    private static final Color C_CARD = Color.WHITE;
    private static final Color C_BORDER = new Color(210, 215, 220);


    // __ INSTANCE VARIABLES ________________________________________________________________

    // table model
    private InventoryTableModel tableModel;

    // table
    private JTable table;

    // search and filter controls
    private JTextField searchField;
    private JCheckBox  chkLowStock;

    // form input fields
    private JLabel lblAutoId;
    private JTextField tfName, tfCategory, tfPrice, tfQty, tfBarcode;

    // buttons
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    // header stats labels 
    private JLabel lblStatProducts, lblStatValue, lblStatLowCount;

    // status bar label at the bottom of the window
    private JLabel lblStatus;

    // tracks which product is currently selected for update/delete.
    // -1 = nothing selected.
    private int selectedId = -1;

    // reference to the Singleton
    private final InventoryManager manager = InventoryManager.getInstance();


    // __ CONSTRUCTOR ______________________________________________________________________
    public Mainframe() {

        setTitle("Inventory Management System");
        setSize(1150, 700);
        setMinimumSize(new Dimension(900, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);                    

        buildUI();          
        refreshTable(manager.getAllProducts()); 
        updateStats();      
        setStatus("Ready - " + manager.getTotalProducts() + " products loaded.");

    }


    
    //__ BUILD UI ________________________________________________________________________________

    private void buildUI() {

        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);

    }


    // __ 1. HEADER ________________________________________________________________________________
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_HEADER);

        // empty border
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // app title 
        JLabel title = new JLabel("  INVENTORY MANAGEMENT SYSTEM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 19));
        title.setForeground(Color.WHITE);

        // stats strip by using FlowLayout
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 24, 0));
        statsPanel.setOpaque(false); 

        lblStatProducts  = headerStatLabel("Products: -");
        lblStatValue     = headerStatLabel("Total Value: -");
        lblStatLowCount  = headerStatLabel("!Low Stock: -");
        lblStatLowCount.setForeground(new Color(255, 170, 50)); // amber color

        statsPanel.add(lblStatProducts);
        statsPanel.add(makeSeparator());
        statsPanel.add(lblStatValue);
        statsPanel.add(makeSeparator());
        statsPanel.add(lblStatLowCount);

        header.add(title, BorderLayout.WEST);
        header.add(statsPanel, BorderLayout.EAST);
        return header;
    }

    // header stat label
    private JLabel headerStatLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(new Color(190, 210, 235));
        return l;
    }

    // visual divider between stats
    private JLabel makeSeparator() {
        JLabel sep = new JLabel("|");
        sep.setForeground(new Color(90, 110, 130));
        return sep;
    }


    // __ 2. CENTER SPLIT PANE ____________________________________________________________
    // JSplitPane = a container split into two panels with a draggable divider.
    // HORIZONTAL_SPLIT = side by side (left | right)
    private JSplitPane buildCenter() {
        JSplitPane split = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            buildTablePanel(),  
            buildFormPanel()    
        );
        split.setDividerLocation(720); 
        split.setDividerSize(5);
        split.setResizeWeight(0.72); 
        split.setBorder(null);       
        return split;
    }


    // __ 3. TABLE PANEL _________________________________________________________________
    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 6));
        panel.setBackground(C_PANEL);

        panel.add(buildSearchBar(), BorderLayout.NORTH);
        panel.add(buildTable(), BorderLayout.CENTER);
        return panel;
    }

    // __ Search bar ____________________________
    private JPanel buildSearchBar() {
        JPanel bar = new JPanel(new BorderLayout(8, 0));
        bar.setOpaque(false);

        // left label
        JLabel lbl = new JLabel("Search:");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // search text field
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setToolTipText("Type a product name OR Barcode ID (number)");

        // LIVE search
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { 
                onSearch(); 
            }
            @Override public void removeUpdate(DocumentEvent e)  { 
                onSearch(); 
            }
            @Override public void changedUpdate(DocumentEvent e) { 
                onSearch(); 
            }
        });

        // low stock filter checkbox
        chkLowStock = new JCheckBox("!! Low Stock Only");
        chkLowStock.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkLowStock.setOpaque(false);

        chkLowStock.addActionListener(e -> onSearch());

        bar.add(lbl, BorderLayout.WEST);
        bar.add(searchField, BorderLayout.CENTER);
        bar.add(chkLowStock, BorderLayout.EAST);
        return bar;
    }

    // __ JTable setup ___________________________
    private JScrollPane buildTable() {
        // create the table model 
        tableModel = new InventoryTableModel();

        table = new JTable(tableModel);

        // table appearance 
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);

        // selection highlight colour
        table.setSelectionBackground(new Color(52, 152, 219, 180));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 225, 230));
        table.setFillsViewportHeight(true); 

        // header 
        javax.swing.table.DefaultTableCellRenderer headerRenderer = 
            (javax.swing.table.DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        headerRenderer.setBackground(C_HEADER);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setOpaque(true);
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        table.getTableHeader().setReorderingAllowed(false); 

        // column width
        int[] colWidths = {45, 180, 110, 90, 55, 120};
        for (int i = 0; i < colWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
        }

        // apply Low-Stock Renderer to all columns
        LowStockRenderer renderer = new LowStockRenderer(tableModel);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // disable cell editing in table
        table.setDefaultEditor(Object.class, null);

        // row selection listener 
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                onRowSelected();
            }
        });

        // wrap in JScrollPane 
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(C_BORDER));
        return scroll;
    }


    // __ 4. FORM PANEL _________________________________________________________
    private JPanel buildFormPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 10));
        outer.setBackground(C_PANEL);
        outer.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 12));

        outer.add(buildFormCard(), BorderLayout.CENTER); 
        outer.add(buildHintCard(), BorderLayout.SOUTH); 
        return outer;
    }

    // input form card 
    private JPanel buildFormCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); 
        card.setBackground(C_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),          
            BorderFactory.createEmptyBorder(18, 18, 18, 18)    
        ));

        // section title
        JLabel title = new JLabel("ADD / EDIT PRODUCT");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(C_HEADER);
        title.setAlignmentX(Component.LEFT_ALIGNMENT); 
        card.add(title);
        card.add(Box.createVerticalStrut(14)); 

        // GridBagLayout
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(C_CARD);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(5, 2, 5, 6); 
        gbc.anchor  = GridBagConstraints.WEST; 

        // create form fields
        lblAutoId  = new JLabel("(auto-assigned)");
        lblAutoId.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblAutoId.setForeground(new Color(120, 130, 140));

        tfName = formField("e.g. Laptop");
        tfCategory = formField("e.g. Electronics");
        tfPrice = formField("e.g. 55000.00");
        tfQty = formField("e.g. 10");
        tfBarcode = formField("Type a product ID to find it...");

        // add rows to grid
        addRow(grid, gbc, 0, "ID:", lblAutoId);
        addRow(grid, gbc, 1, "Name:", tfName);
        addRow(grid, gbc, 2, "Category:", tfCategory);
        addRow(grid, gbc, 3, "Price (₹):", tfPrice);
        addRow(grid, gbc, 4, "Quantity:", tfQty);
        addRow(grid, gbc, 5, "Barcode ID:", tfBarcode);

        // barcode field: LIVE SEARCH 
        tfBarcode.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e){ 
                onBarcodeTyped(); 
            }
            @Override public void removeUpdate(DocumentEvent e){ 
                onBarcodeTyped(); 
            }
            @Override public void changedUpdate(DocumentEvent e){
                onBarcodeTyped(); 
            }
        });

        card.add(grid);
        card.add(Box.createVerticalStrut(16));

        // buttons
        btnAdd    = styledButton("  ADD",    C_ADD);
        btnUpdate = styledButton("  UPDATE", C_UPDATE);
        btnDelete = styledButton("  DELETE", C_DELETE);
        btnClear  = styledButton("  CLEAR",  C_CLEAR);

        // update and delete are disabled initially
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        // adding listeners
        btnAdd.addActionListener( e -> onAdd());
        btnUpdate.addActionListener( e -> onUpdate());
        btnDelete.addActionListener( e -> onDelete());
        btnClear.addActionListener( e -> clearForm());

        JPanel row1 = buttonRow(btnAdd, btnUpdate);
        JPanel row2 = buttonRow(btnDelete, btnClear);
        row1.setAlignmentX(Component.LEFT_ALIGNMENT);
        row2.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(row1);
        card.add(Box.createVerticalStrut(7));
        card.add(row2);

        return card;
    }

    // adding one label + field pair to the GridBag form
    private void addRow(JPanel grid, GridBagConstraints gbc,
                        int rowIndex, String labelText, JComponent field) {
        // label cell (column 0)
        gbc.gridx = 0;
        gbc.gridy = rowIndex;
        gbc.weightx = 0;           
        gbc.fill = GridBagConstraints.NONE;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(70, 80, 90));
        grid.add(lbl, gbc);

        // field cell (column 1)
        gbc.gridx = 1;
        gbc.weightx = 1.0;         
        gbc.fill = GridBagConstraints.HORIZONTAL;
        grid.add(field, gbc);
    }

    private JTextField formField(String tooltip) {
        JTextField tf = new JTextField(14);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setToolTipText(tooltip);
        return tf;
    }

    private JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);   
        btn.setBorderPainted(false);  
        btn.setOpaque(true);          
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        btn.setPreferredSize(new Dimension(118, 36));
        return btn;
    }

    private JPanel buttonRow(JButton b1, JButton b2) {
        JPanel row = new JPanel(new GridLayout(1, 2, 8, 0));
        row.setBackground(C_CARD);
        row.add(b1);
        row.add(b2);
        return row;
    }

    // hint card below the form
    private JPanel buildHintCard() {
        JPanel card = new JPanel(new GridLayout(3, 1, 0, 4));
        card.setBackground(C_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        String[] hints = {
            "   Click any row to auto-fill the form",
            "   RED rows have quantity below 5",
            "   Thank you for choosing us."
        };
        Font hintFont  = new Font("Segoe UI", Font.ITALIC, 11);
        Color hintColor = new Color(130, 140, 150);

        for (String hint : hints) {
            JLabel l = new JLabel(hint);
            l.setFont(hintFont);
            l.setForeground(hintColor);
            card.add(l);
        }
        return card;
    }


    // __ 5. STATUS BAR ________________________________________________________
    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        
        bar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, C_BORDER),
            BorderFactory.createEmptyBorder(4, 14, 4, 14)
        ));
        bar.setBackground(new Color(240, 241, 244));

        lblStatus = new JLabel("Initialising...");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStatus.setForeground(new Color(80, 90, 100));

        bar.add(lblStatus, BorderLayout.WEST);
        return bar;
    }

    //__ EVENT HANDLERS________________________________________________________________________________________

    // __ ADD _______________________________________________________________
    private void onAdd() {

        String name = tfName.getText().trim();
        String category = tfCategory.getText().trim();
        String priceStr = tfPrice.getText().trim();
        String qtyStr = tfQty.getText().trim();

        // validation
        if (name.isEmpty() || category.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
            error("All fields (Name, Category, Price, Quantity) are required.");
            return;
        }

        double price;
        int qty;
        try {
            price = Double.parseDouble(priceStr); 
            qty   = Integer.parseInt(qtyStr); 
        } catch (NumberFormatException ex) {
            error("Price must be a decimal number (e.g. 999.00)\nQuantity must be a whole number.");
            return;
        }

        if (price < 0) { 
            error("Price cannot be negative."); 
            return; 
        }
        if (qty < 0) { 
            error("Quantity cannot be negative."); 
            return; 
        }

        // create and save
        Product added = manager.addProduct(new Product(0, name, category, price, qty));

        setStatus("  Product '" + added.getName() + "' added (ID: " + added.getId() + ").");
        clearForm();                          
        refreshTable(manager.getAllProducts());
        updateStats();
    }


    // __ UPDATE _______________________________________________________________
    private void onUpdate() {
        if (selectedId == -1) {
            error("No product selected. Click a row in the table first."); 
            return;
        }

        String name     = tfName.getText().trim();
        String category = tfCategory.getText().trim();
        String priceStr = tfPrice.getText().trim();
        String qtyStr   = tfQty.getText().trim();

        if (name.isEmpty() || category.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
            error("All fields are required."); 
            return;
        }

        double price;
        int qty;
        try{
            price = Double.parseDouble(priceStr);
            qty = Integer.parseInt(qtyStr);
        } 
        catch (NumberFormatException ex){
            error("Invalid number format."); 
            return;
        }

        if (price < 0) { 
            error("Price cannot be negative."); 
            return; 
        }
        if (qty < 0) { 
            error("Quantity cannot be negative."); 
            return; 
        }

        manager.updateProduct(selectedId, name, category, price, qty);
        
        setStatus("  Product ID " + selectedId + " updated.");
        clearForm();
        refreshTable(manager.getAllProducts()); 
        updateStats();
    }

    // __ DELETE _______________________________________________________________
    private void onDelete() {
        if (selectedId == -1) {
            error("No product selected."); 
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete product ID " + selectedId + "?\nThis action cannot be undone.",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            manager.deleteProduct(selectedId);
            setStatus("  Product deleted.");
            clearForm();
            refreshTable(manager.getAllProducts());
            updateStats();
        }
    }

    // __ ROW SELECTED _______________________________________________________________
    private void onRowSelected() {
        int row = table.getSelectedRow();
        if (row == -1) 
            return; 

        Product p = tableModel.getProductAt(row);
        if (p == null) 
            return;

        // store the selected product's ID
        selectedId = p.getId();

        // populate the form fields
        lblAutoId.setText(String.valueOf(p.getId()));
        tfName.setText(p.getName());
        tfCategory.setText(p.getCategory());
        tfPrice.setText(String.format("%.2f", p.getPrice()));
        tfQty.setText(String.valueOf(p.getQuantity()));
        tfBarcode.setText("");

        // enable UPDATE and DELETE now that a product is selected
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);

        setStatus("  Selected: " + p.getName() + " | ID: " + p.getId()
                + " | Qty: " + p.getQuantity()
                + " | Total Value: ₹" + String.format("%,.2f", p.getTotalValue()));
    }


    //  __ LIVE SEARCH _______________________________________________________________
    private void onSearch() {
        String query = searchField.getText().trim();

        List<Product> results;

        if (chkLowStock.isSelected()) {
            
            results = manager.getLowStock(LOW_STOCK_THRESHOLD);
            if (!query.isEmpty()) {
                final String q = query.toLowerCase();
                
                results = results.stream()
                    .filter(p -> p.getName().toLowerCase().contains(q)
                              || String.valueOf(p.getId()).contains(q))
                    .collect(Collectors.toList());
            }
        } else {
            
            results = manager.search(query);
        }

        refreshTable(results);
        setStatus("  Showing " + results.size() + " product(s)" +
                  (query.isEmpty() ? "." : " matching \"" + query + "\"."));
    }


    //  __ BARCODE LOOKUP _______________________________________________________________
    private void onBarcodeTyped() {
        String text = tfBarcode.getText().trim();
        if (text.isEmpty()) return;

        try {
            int id = Integer.parseInt(text); 
            Product p = manager.findById(id);

            if (p != null) {
                
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Product row = tableModel.getProductAt(i);
                    if (row != null && row.getId() == id) {

                        // select that row in the table
                        table.setRowSelectionInterval(i, i);

                        // scrollRectToVisible scrolls so the selected row is visible
                        table.scrollRectToVisible(table.getCellRect(i, 0, true));
                        setStatus("  Barcode found: " + p.getName() + " (ID: " + id + ")");
                        break;
                    }
                }
            } else {
                setStatus("  No product found with Barcode ID: " + id);
            }
        } catch (NumberFormatException ignored) {
            // user is still typing - do nothing!
        }
    }

    // __ UTILITY METHODS ________________________________________________________________________________

    // push a new list of products into the table model - triggers repaint
    private void refreshTable(List<Product> products) {
        tableModel.setProducts(products); 
    }

    // recalculate and update the three header stat labels
    private void updateStats() {
        int total = manager.getTotalProducts();
        double value = manager.getTotalInventoryValue();
        long lowCount = manager.getLowStockCount(LOW_STOCK_THRESHOLD);

        lblStatProducts.setText("Products: " + total);
        
        lblStatValue.setText(String.format("Total Value: \u20B9%,.0f", value)); 
        lblStatLowCount.setText(" Low Stock: " + lowCount);

        lblStatLowCount.setForeground(lowCount > 0 ? new Color(255, 160, 40) : new Color(190, 210, 235));
    }

    // reset the form to its initial empty state
    private void clearForm() {
        selectedId = -1;
        lblAutoId.setText("(auto-assigned)");
        tfName.setText("");
        tfCategory.setText("");
        tfPrice.setText("");
        tfQty.setText("");
        tfBarcode.setText("");
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        table.clearSelection(); 
        setStatus("  Form cleared. Ready to add a new product.");
    }

    // update the status bar
    private void setStatus(String msg) {
        lblStatus.setText(msg);
    }

    // show a red-icon error dialog
    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}