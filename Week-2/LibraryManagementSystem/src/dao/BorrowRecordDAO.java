package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.BorrowRecord;
import util.DatabaseConnection;

public class BorrowRecordDAO{

    public void addBorrowRecord(BorrowRecord borrowRecord){
        String sql = "INSERT INTO borrow_records(userId, bookId, borrowDate) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, borrowRecord.getUserId());
            pstmt.setInt(2, borrowRecord.getBookId());
            pstmt.setString(3, borrowRecord.getBorrowDate().toString());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int recordId, LocalDate returnDate){
        String sql = "UPDATE borrow_records SET returnDate = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, returnDate.toString());
            pstmt.setInt(2, recordId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BorrowRecord> getAllRecords() {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM borrow_records";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BorrowRecord record = new BorrowRecord(
                    rs.getInt("id"),
                    rs.getInt("userId"),
                    rs.getInt("bookId"),
                    LocalDate.parse(rs.getString("borrowDate")),
                    rs.getString("returnDate") != null ? LocalDate.parse(rs.getString("returnDate")) : null
                );
                records.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void createTable(){

        String sql = "CREATE TABLE IF NOT EXISTS borrow_records (" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT," + 
                        "    userId INTEGER NOT NULL," + 
                        "    bookId INTEGER NOT NULL," + 
                        "    borrowDate TEXT NOT NULL," + 
                        "    returnDate TEXT," + 
                        "    FOREIGN KEY(userId) REFERENCES users(id)," + 
                        "    FOREIGN KEY(bookId) REFERENCES books(id)" +
                        ")";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}