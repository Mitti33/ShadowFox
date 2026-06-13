import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class LowStockRenderer extends DefaultTableCellRenderer{

    // the threshold
    static final int LOW_STOCK_THRESHOLD = 5;

    // the colors
    private static final Color COLOR_LOW_BG   = new Color(255, 200, 200); // light red bg
    private static final Color COLOR_LOW_FG   = new Color(160, 0,   0);   // dark red text
    private static final Color COLOR_ROW_EVEN = Color.WHITE;
    private static final Color COLOR_ROW_ODD  = new Color(242, 245, 250); // very light blue

    // a InventoryTableModel table
    private final InventoryTableModel model;

    // constructor
    public LowStockRenderer(InventoryTableModel model){
        this.model = model;
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value,
        boolean isSelected, boolean hasFocus,
        int row, int column
    ){
        // let parent class do default rendering
        Component c = super.getTableCellRendererComponent
            (table, value, isSelected, hasFocus, row, column);

        // get the product at row
        Product p = model.getProductAt(row);

        // if no product, return c
        if(p == null) return c;

        // if this row is selected, set default highlight formatting
        if(isSelected){
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }

        // else, check if it's suffering from low stock
        // if yes, set red color; if no, set normal color
        else if(p.getQuantity() < LOW_STOCK_THRESHOLD){
            c.setBackground(COLOR_LOW_BG);
            c.setForeground(COLOR_LOW_FG);
        }
        else{
            c.setBackground(row % 2 == 0 ? COLOR_ROW_EVEN : COLOR_ROW_ODD);
            c.setForeground(Color.BLACK);
        }

        // align numeric columns to the center
        JLabel label = (JLabel) c;
        if (column == 0 || column == 3 || column == 4 || column == 5) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            label.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // add a left-padding by setting an empty border on the label
        label.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));

        // return component
        return c;
    }
}