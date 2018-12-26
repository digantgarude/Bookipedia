package bkstore;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
public class deleteBooks extends JFrame implements ActionListener{
    JFrame jf;    
    JLabel heading1;
    PreparedStatement stat; 
    JButton back,delete;
    Font small,medium,mediumBig,large;
    String id;
    public deleteBooks(){
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
        heading1 = new JLabel("Delete Books");
        heading1.setFont(large);
        int r=34 , g=0, b=7;
        heading1.setForeground(new Color(r, g, b));
        heading1.setBounds(100,40,340,75);
        jf.add(heading1);
        
        
        //Back Button;
        back = new JButton("Go Back");
        jf.add(back);
        back.setFont(small);
        back.setBounds(100,600,100,40);
        back.addActionListener(this);
        
        delete = new JButton("Delete");
        jf.add(delete);
        back.setFont(small);
        delete.setBounds(225,600,100,40);
        delete.setVisible(false);
        delete.addActionListener(this);
        
        int rB=195,bB=225,gB=226;
        back.setBackground(new Color(rB,bB,gB));
        delete.setBackground(new Color(rB,bB,gB));
        
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
        String column[] = {};
        String data[][] = {};
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url);
            String query = "SELECT * FROM book";
 
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
            sp.setBounds(100,150,1000,400);
            jf.add(sp);
            ListSelectionModel select= jt.getSelectionModel();
            select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            select.addListSelectionListener(new ListSelectionListener() {
                
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()){
                        int rows = jt.getSelectedRow();
                        id = (String)jt.getValueAt(rows, 0);
                        System.out.println(id);
                        delete.setVisible(true);
                    }
                }
  
            });
            
            con.close();

            
        }catch(Exception se){
            se.printStackTrace();
        }
    }
    
    
    
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back){
            BkStore obj = new BkStore();
            jf.dispose();
        }else if(e.getSource()==delete){
            String url = "jdbc:sqlserver://localhost:1433;databaseName=bookstore;integratedSecurity=true"; 
            Connection con = null;  
            Statement stmt = null; 
            ResultSet rs = null;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(url);
                String query = "DELETE FROM book WHERE id = '"+Integer.parseInt(id)+"'";
                stat = con.prepareStatement(query);
                
                stat.execute();
                con.close();
                new deleteBooks();
                jf.dispose();
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        }
    }
    
}
