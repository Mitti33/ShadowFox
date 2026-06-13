// This file focuses on DATA BINDING
// It is the bridge between the Product objects and the table rows

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class InventoryTableModel extends AbstractTableModel{

    // the columns - unchangeable
    private static final String[] COLUMNS = {
        "ID", "Name", "Category", "Price (₹)", "Qty", "Total Value (₹)"
    };

    // the list to be shown on the table
    private List<Product> products;

    // simple constructor to initialize empty list
    public InventoryTableModel(){
        this.products = new ArrayList<>();
    }

    // __ THE OVERRIDES _____________________________________________________________
    
    // how many rows are there? = no. of products
    @Override
    public int getRowCount(){
        return products.size();
    }

    // how many columns are there? = 6
    @Override
    public int getColumnCount(){
        return COLUMNS.length;
    }

    // what is the name of the column with index col?
    @Override
    public String getColumnName(int col){
        return COLUMNS[col];
    }

    // __ DATA BINDING METHOD ________________________________________________________

    // look up the product at row, look up its field at col
    @Override
    public Object getValueAt(int row, int col){

        if (row < 0 || row >= products.size())
            return null;

        // get the product
        Product p = products.get(row);

        // get the field
        switch (col){
            case 0: return p.getId();
            case 1: return p.getName();
            case 2: return p.getCategory();
            case 3: return String.format("%.2f", p.getPrice());
            case 4: return p.getQuantity();
            case 5: return String.format("%.2f", p.getTotalValue());
            default: return "";
        }
    }

    // __ DISABLE DIRECT CELL EDITING _____________________________________________________
    
    // users are not allowed to edit the table cells
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    // __ TABLE REFRESH METHOD _____________________________________________________________

    // after operations, the table must be refreshed
    // using the fireTableDataChanged() method for this
    public void setProducts(List<Product> products){
        this.products = new ArrayList<>(products); // using a copy, not the original list
        fireTableDataChanged();
    }

    // __ GETTING PRODUCT AT SPECIFIC ROW ____________________________________________________
    
    public Product getProductAt(int row){
        if (row < 0 || row >= products.size())
            return null;
        return products.get(row);
    }
}