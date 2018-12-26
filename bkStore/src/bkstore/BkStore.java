package bkstore;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
public class BkStore extends JFrame implements ActionListener{
    JFrame jf;    
    JLabel heading1,heading2,quantitySell;
    JButton add,sell,delete,soldBooks,exit;
    JTextField searchTF,sellQuantityTF;
    JList searchList;
    PreparedStatement stat; 
    Font small,medium,mediumBig,large;
    String id  = null,oldQuantityTable = null,bAuthString,bnameString,bTypeString,bPriceString,bCondString,bQuantString;
    int bId, qId;
    public BkStore() {
        jf = new JFrame();
        jf.getContentPane().setBackground(new Color(223, 255, 150));
        jf.setSize(jf.getMaximumSize());
        jf.setLayout(null);
        jf.setVisible(true);
        
        // Fonts;
        small = new Font("ABeeZee",Font.PLAIN,16);
        medium = new Font("ABeeZee",Font.PLAIN,18);
        mediumBig = new Font("ABeeZee",Font.PLAIN,30);
        large = new Font("Brush Script MT",Font.PLAIN,80);
        
        // Heading;
        heading1 = new JLabel("Bookipedia");
        heading2 = new JLabel("Search !");
        heading1.setFont(large);
        heading2.setFont(mediumBig);
        int r=34 , g=0, b=7;
        heading1.setForeground(new Color(r, g, b));
        heading2.setForeground(new Color(r, g, b));
        heading1.setBounds(200,40,340,75);
        heading2.setBounds(200,200,380,50);
        jf.add(heading1);jf.add(heading2);
        
        // Buttons;
        add = new JButton("Add Books");
        sell = new JButton("Sell Book");
        delete = new JButton("Delete Books");
        soldBooks = new JButton("Sold Books");
        exit = new JButton("Exit");
        
        int rB=195,bB=225,gB=226;
        add.setBackground(new Color(rB,bB,gB));
        sell.setBackground(new Color(rB,bB,gB));
        delete.setBackground(new Color(rB,bB,gB));
        soldBooks.setBackground(new Color(rB,bB,gB));
        exit.setBackground(new Color(rB,bB,gB));
        
        jf.add(add);jf.add(sell);jf.add(delete);jf.add(soldBooks);jf.add(exit);
        //Button font color;
        add.setForeground(Color.DARK_GRAY);
        sell.setForeground(Color.DARK_GRAY);
        delete.setForeground(Color.DARK_GRAY);
        soldBooks.setForeground(Color.DARK_GRAY);
        exit.setForeground(Color.DARK_GRAY);
        //Button font size;
        add.setFont(small);
        sell.setFont(small);
        delete.setFont(small);
        soldBooks.setFont(small);
        exit.setFont(small);
        //Add action listener;
        add.addActionListener(this);
        sell.addActionListener(this);
        delete.addActionListener(this);
        soldBooks.addActionListener(this);
        exit.addActionListener(this);
        int shiftByX=100,shiftByY=-25,bSizeX = 150,bSizeY = 50;
        add.setBounds       (100+shiftByX,150+shiftByY,bSizeX,bSizeY);
        delete.setBounds    (300+shiftByX,150+shiftByY,bSizeX,bSizeY);
        soldBooks.setBounds (500+shiftByX,150+shiftByY,bSizeX,bSizeY);
        exit.setBounds      (700+shiftByX,150+shiftByY,bSizeX,bSizeY);
        
        // Search Bar;
        searchTF = new JTextField(16);
        searchTF.setFont(small);
        searchTF.addActionListener(this);
        jf.add(searchTF);
        searchTF.setBounds(200,275,400,30);
        
        // Sell quantity tf ;
        sellQuantityTF = new JTextField("1");
        sellQuantityTF.setFont(small);
        jf.add(sellQuantityTF);
        sellQuantityTF.setBounds(1100,375,50,30);
        sellQuantityTF.setVisible(false);
        
        quantitySell = new JLabel("Quantity");
        jf.add(quantitySell);
        quantitySell.setBounds(1100,350,100,30);
        quantitySell.setVisible(false);
        
        jf.add(sell);
        sell.setBounds(1200,375,150,30);
        sell.setVisible(false);
        
    }
        
    public static void main(String[] args) {
        new BkStore();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==add){
            heading2.setText("Add new books !");
            addBooks obj = new addBooks();
            jf.dispose();
            
        }else if(e.getSource()==sell){
            sellBook();
        }else if(e.getSource()==searchTF){
            searchTheDB();
        }else if(e.getSource()==delete){
            new deleteBooks();
            jf.dispose();
        }else if(e.getSource()==soldBooks){
            new soldBooks();
            jf.dispose();
        }else if(e.getSource()==exit){
            System.exit(0);
        }
    }
    public void searchTheDB(){
        String url = "jdbc:sqlserver://localhost:1433;databaseName=bookstore;integratedSecurity=true"; 
        Connection con = null;  
        Statement stmt = null; 
        ResultSet rs = null;   
        DefaultTableModel t = new DefaultTableModel(new String[]{"Id","Name", "Author", "Type", "Price", "Condition", "Quantity"},0){
            @Override
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        String column[] = {}; // "Id","Name", "Author", "Type", "Price", "Condition", "Quantity"
        String data[][] = {};
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url);
            String searchString = searchTF.getText();
            String query = "SELECT * FROM book WHERE bname LIKE '%"+searchString+"%' OR author LIKE '%"+searchString+"%' OR btype LIKE '%"+searchString+"%'";
            if(searchString.equals("")||searchString.equals(" ")||searchString.toLowerCase().equals("all")){
                query = "SELECT * FROM book";
            }
                
            stat = con.prepareStatement(query);    
            rs = stat.executeQuery();
            // Table;
            JTable jt = new JTable(data,column);
            jt.setRowSelectionAllowed(true);
              
            while(rs.next()){
                t.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(6),rs.getString(7),rs.getString(8)});
            }
            jt.setModel(t);
            jt.setFont(small);
            JScrollPane sp=new JScrollPane(jt);
            sp.setBounds(200,325,800,300);
            jf.add(sp);
            ListSelectionModel select= jt.getSelectionModel();
            select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            select.addListSelectionListener(new ListSelectionListener() {
                
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()){
                        int rows = jt.getSelectedRow();
                        id = (String)jt.getValueAt(rows, 0);
                        System.out.println(id);
                        bnameString = (String)jt.getValueAt(rows, 1);
                        bAuthString = (String)jt.getValueAt(rows, 2);
                        bTypeString = (String)jt.getValueAt(rows, 3);
                        bPriceString = (String)jt.getValueAt(rows, 4);
                        bCondString = (String)jt.getValueAt(rows, 5);
                        oldQuantityTable = (String)jt.getValueAt(rows, 6);
                        sellQuantityTF.setVisible(true);
                        quantitySell.setVisible(true);
                        sell.setVisible(true);
                        
                    }
                }
                
                
                
                
            });
            
            con.close();
        }catch(Exception se){
            se.printStackTrace();
        }
    }
    
    
    public void sellBook(){
        bId = Integer.parseInt(id);
        qId = Integer.parseInt(oldQuantityTable);
        int no_Of_books = Integer.parseInt(sellQuantityTF.getText());
        System.out.println("No of books : "+no_Of_books);
        int newQuantity = qId - no_Of_books;
        System.out.println("New quantity : "+newQuantity);
        if(newQuantity>0){
            String query2 = "UPDATE book SET quantity='"+newQuantity+"' WHERE id='"+bId+"' "
                    + "; INSERT INTO soldbook (bname,author,btype,price,condition,quantity)"
                    + "VALUES('"+bnameString+"','"+bAuthString+"','"+bTypeString+"','"+bPriceString+"','"+bCondString+"','"+no_Of_books+"');";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=bookstore;integratedSecurity=true"; 
            Connection con = null;  
            Statement stmt = null; 
            ResultSet rs = null;
            PreparedStatement stat;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(url);
                stat = con.prepareStatement(query2);
                stat.execute();
                con.close();
                System.out.println("Query should be executed !");
            }catch(Exception ae){
                ae.printStackTrace();
            }
            
            
        }
    }
    
    
}
