package bkstore;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
public class addBooks extends JFrame implements ActionListener{
    JFrame jf;
    JButton add,backButton;
    JLabel lname,lauthor,lbtype,lyp,lprice,lquantity,heading1;
    JTextField bname,author,btype,yearpublished,price,quantity;
    Checkbox oldB,newB;
    CheckboxGroup condition;
    PreparedStatement stat;
    Font small,medium,large,mediumBig;
    public addBooks() { 
        small = new Font("ABeeZee",Font.PLAIN,16);
        medium = new Font("ABeeZee",Font.PLAIN,18);
        mediumBig = new Font("ABeeZee",Font.PLAIN,30);
        large = new Font("Brush Script MT",Font.PLAIN,80);
        
        jf = new JFrame();
        condition = new CheckboxGroup();
        lname = new JLabel("Name");
        lauthor = new JLabel("Author");
        lbtype = new JLabel("Type of book");
        lyp = new JLabel("Year Published");
        lprice = new JLabel("Price");
        lquantity = new JLabel("Quantity");
        
        heading1 = new JLabel("Sold Books ");
        heading1.setFont(large);
        int r=34 , g=0, b=7;
        heading1.setForeground(new Color(r, g, b));
        heading1.setBounds(200,40,340,75);
        jf.add(heading1);
        
        
        //Setting font ! ;
        lname.setFont(medium);
        lauthor.setFont(medium);
        lbtype.setFont(medium);
        lyp.setFont(medium);
        lprice.setFont(medium);
        lquantity.setFont(medium);
        
        //Buttons and TextFields ;
        add = new JButton("Add Books");
        backButton = new JButton("Back");
        int sizeOfTF = 30;
        bname = new JTextField();
        author = new JTextField();
        btype = new JTextField();
        yearpublished = new JTextField();
        price = new JTextField();
        quantity = new JTextField();

        bname.setFont(small);
        author.setFont(small);
        btype.setFont(small);
        yearpublished.setFont(small);
        price.setFont(small);
        quantity.setFont(small);
        
        int rB=195,bB=225,gB=226;
        add.setBackground(new Color(rB,bB,gB));
        backButton.setBackground(new Color(rB,bB,gB));
        

        //Checkboxes;
        oldB = new Checkbox("Old");
        newB = new Checkbox("New");
        oldB.setCheckboxGroup(condition);
        newB.setCheckboxGroup(condition);
        oldB.setFont(medium);
        newB.setFont(medium);
        int XboxSize=150,YboxSize=25,lYspot=100;
        //Label Location ;
        lname.setBounds         (50          ,lYspot+YboxSize,XboxSize,YboxSize);
        lauthor.setBounds       (100+XboxSize,lYspot+YboxSize,XboxSize,YboxSize);
        lbtype.setBounds        (300+XboxSize,lYspot+YboxSize,XboxSize,YboxSize);
        lyp.setBounds           (500+XboxSize,lYspot+YboxSize,XboxSize,YboxSize);
        lprice.setBounds        (700+XboxSize,lYspot+YboxSize,XboxSize,YboxSize);
        lquantity.setBounds     (1000+XboxSize,lYspot+YboxSize,XboxSize,YboxSize);
        
        //Text Fields Location
        bname.setBounds         (50          ,150+YboxSize,XboxSize,YboxSize);
        author.setBounds        (100+XboxSize,150+YboxSize,XboxSize,YboxSize);
        btype.setBounds         (300+XboxSize,150+YboxSize,XboxSize,YboxSize);
        yearpublished.setBounds (500+XboxSize,150+YboxSize,XboxSize,YboxSize);
        price.setBounds         (700+XboxSize,150+YboxSize,XboxSize,YboxSize);
        oldB.setBounds          (875+XboxSize,150+YboxSize,50,YboxSize);        // ChkBox Location;
        newB.setBounds          (925+XboxSize,150+YboxSize,75,YboxSize);        // ChkBox Location;
        quantity.setBounds      (1000+XboxSize,150+YboxSize,XboxSize,YboxSize);
        add.setBounds           (1200+XboxSize,150+YboxSize,XboxSize,YboxSize);
        backButton.setBounds    (1200+XboxSize,200+YboxSize,XboxSize,YboxSize);
        
//        Add Labels;
        jf.add(lname);
        jf.add(lauthor);
        jf.add(lbtype);
        jf.add(lyp);
        jf.add(lprice);
        jf.add(lquantity);
//        Add TextFields and Buttons
        jf.add(bname);
        jf.add(author);
        jf.add(btype);
        jf.add(yearpublished);
        jf.add(price);
        jf.add(quantity);
        jf.add(oldB);
        jf.add(newB);
        jf.add(add);
        jf.add(backButton);
        add.setForeground(Color.DARK_GRAY);
        backButton.setForeground(Color.DARK_GRAY);
        add.setFont(small);
        backButton.setFont(small);
        add.addActionListener(this);
        backButton.addActionListener(this);
        
        jf.setLayout(null);
        jf.getContentPane().setBackground(new Color(223, 255, 150));
        jf.setSize(jf.getMaximumSize());
        jf.setTitle("Add Books | Bookipedia");
        jf.setVisible(true);
                
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==add){
            String url = "jdbc:sqlserver://localhost:1433;databaseName=bookstore;integratedSecurity=true"; 
            Connection con = null;  
            Statement stmt = null; 
            ResultSet rs = null;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(url);
                stat = con.prepareStatement("INSERT INTO book(bname,author,btype,yearpublished,price,condition,quantity)VALUES(?,?,?,?,?,?,?)");
                stat.setString(1, bname.getText());
                stat.setString(2, author.getText());
                stat.setString(3, btype.getText());
                stat.setInt(4, Integer.parseInt(yearpublished.getText()));
                stat.setInt(5, Integer.parseInt(price.getText()));
                String condStr = "New";
                if(oldB.getState()==true){
                    condStr = "Old";
                }else if(newB.getState() == true){
                    condStr = "New";
                }
                stat.setString(6, condStr);
                stat.setInt(7, Integer.parseInt(quantity.getText()));
                
                stat.execute();
                con.close();
                bname.setText(""); 
                author.setText("");
                btype.setText(""); 
                yearpublished.setText("");
                price.setText("");
                quantity.setText("");
                
                
                
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        }else if(e.getSource()==backButton){
            BkStore obj = new BkStore();
            jf.dispose();
        }
    }
    
}
