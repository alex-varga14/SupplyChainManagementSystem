package edu.ucalgary.ensf409;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.concurrent.CountDownLatch; 
import javax.imageio.ImageIO;
import java.io.*;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Kenny Jeon, Alex Varga and Ben Krett
 * @version 1.3
 * @since 1.0
 * 
 */
/* Input Class Documentation:
This class serves to receive an user input and instantiate an Order instance. Checks if the order is valid according to
database furniture items and type.
Fields:
JTextField in;
    - Initialize a textfield that allows for user input in the GUI.
JLabel l;
    - Initialize a label that gives the user directions on what to input.
JButton b;
    - Initialize a button that can be clicked on.
JButton exit; 
    - Initalize a button that can be clicked on.
private String input;
    - String to store user input 
private int counter = 1;
    - Counter for button clicks
private String input1;
private String input2;
private String input3;
    - all three receive various input strings for the order
private String regex = "[0-9]+";
    - regex for amount of items being ordered
public CountDownLatch loginSignal = new CountDownLatch(1);
    - Waits until order has been input before instantiating order object

Methods:
main()
	* Accept a user-input argument which specifies a user input for 1) furniture category, 2) its type,
    * and 3) the number of items requested.
	* The argument should be in order stated above as three strings.
    * EXAMPLE INPUTs:
    * mesh chair, 1
    * executive chair, 2
    * Therefore, the category followed by a space then its type followed by a comma then a space and 
    * finally the requested amount.
	* If no argument is specified, it throws a custom exception, 
    * OrderArugmentNotProvidedException. Additional arguments are ignored.
public Input()
public void actionPerformed(ActionEvent e)
public String getInput()
*/
public class Input implements ActionListener{

    JTextField in;
    JLabel l;      
    JButton b;     
    JButton exit;  
    private String input; 
    private int counter = 1; 
    private String t;
    private String f;
    private String input3;
    private String regex = "[0-9]+";
    public CountDownLatch loginSignal = new CountDownLatch(1);
    public Input(){
        JFrame frame = new JFrame("University of Calgary Supply Chain Management (SCM) Furniture Recycling Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,50);
        try{
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("furniture.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        in  = new JTextField();
        in.setText("");
        l = new JLabel("<html> Welcome to the Furniture Recycling Program:</html>", JLabel.CENTER); 
        l.setForeground(Color.red);
        l.setOpaque(true);
        l.setFont(new Font("Arial", Font.BOLD, 25));
        l.setBounds(100,50,600,50); // Sets position and size of Label
        b = new JButton("Begin Order"); 
        exit = new JButton("Exit");
        exit.setForeground(Color.red);
        exit.setFont(new Font("Arial", Font.PLAIN, 40));
        b.setFont(new Font("Arial", Font.PLAIN, 20));
        exit.setBounds(600,650,150,60);  // Sets position and size of Exit Button
        b.setBounds(300,650,150,30); // Sets position and size of TextField
        b.addActionListener(this);  // Adds ActionListener to the button
        exit.addActionListener(this); // Adds ActionListener to the button
        frame.add(b); //Adds button b to GUI
        frame.add(in); //Adds TextField in to GUI
        frame.add(l); //Adds Label l to GUI
        frame.add(exit); //Adds button exit to GUI
        
        /*Useless feature as is, can be used potentially */
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Order Menu");
        menu.setMnemonic(KeyEvent.VK_O);
        
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("Chair");
        
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = new JRadioButtonMenuItem("Desk");
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = new JRadioButtonMenuItem("Lamp");
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = new JRadioButtonMenuItem("Filing");
        group.add(menuItem);
        menu.add(menuItem);
        //menuItem = new JRadioButtonMenuItem(new JMenu("Chair"));


        bar.add(menu);
        frame.setJMenuBar(bar);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 800);
    }
    public String getInput(){
        return input.toString();
    }

    public void actionPerformed(ActionEvent e){
        in.setBounds(250,500,250,30);   // Sets position and size of TextField
        try{
            if(counter == 1){
                l.setText("Choose a furniture category:");
                b.setText("Continue");
                counter = 2;
            } else if (counter == 2){
                t = in.getText();
                t = t.toLowerCase().strip();
                if((t.strip().toLowerCase().equals("mesh")) || (t.strip().toLowerCase().equals("kneeling")) || 
                (t.strip().toLowerCase().equals("task")) || (t.strip().toLowerCase().equals("executive")) || (t.strip().toLowerCase().equals("ergonomic")) ||
                (t.strip().toLowerCase().equals("standing")) || (t.strip().toLowerCase().equals("traditional")) || (t.strip().toLowerCase().equals("adjustable") )
                || (t.strip().toLowerCase().equals("desk")) || (t.strip().toLowerCase().equals("study")) || (t.strip().toLowerCase().equals("swing arm")) ||
                (t.strip().toLowerCase().equals("small")) || (t.strip().toLowerCase().equals("medium")) || (t.strip().toLowerCase().equals("large"))){
                input = t;
                } else {
                    counter = 5;
                }
                in.setText("");
                l.setText("Now, choose a furniture type:");
                counter = 3;
            } else if (counter == 3){
                f = in.getText();
                f = f.toLowerCase().strip();
                if( ((t.strip().toLowerCase().equals("desk")) && (!(f.strip().toLowerCase().equals("desk"))))|| 
                ((!(t.strip().toLowerCase().equals("desk"))) && (f.strip().toLowerCase().equals("desk"))) ||
                (f.strip().toLowerCase().equals("chair")) || (f.strip().toLowerCase().equals("lamp")) || 
                (f.strip().toLowerCase().equals("filing"))){ 
                    input += " " + f;
                } else{
                    counter = 5;
                }
                in.setText("");
                l.setText("Finally, choose the amount:");
                counter = 4;
            } else if (counter == 4){
                input3 = in.getText().strip();
                if (input3.matches(regex)){
                    input += ", " + input3;
                } else{
                    counter = 5;
                }
                in.setText("");
                loginSignal.countDown();
            } else if (counter == 5){
                input = "";
                l.setText("Invalid Order! Please Restart Order");
                counter = 1;
            } if(e.getSource() == exit){
                System.exit(0);
            }
            
        } catch(Exception ex){System.out.println(ex);}
    }
    public void orderFailed(String x){
        in.setBounds(0,0,0,0);
        l.setBounds(100,50,600,100);
        l.setFont(new Font("Arial", Font.PLAIN, 15));
        l.setText("<html> Order cannot be fulfilled based on current inventory. Manufacturers include: <br/>" + x+ "</html>");
        b.setBounds(0,0,0,0);
        exit.setFont(new Font("Arial", Font.PLAIN, 25));
        exit.setBounds(300,450,130,30);
    }

    public void orderComplete(String x){
        in.setBounds(0,0,0,0);
        l.setBounds(100,50,600,100);
        l.setText("Order Complete!");
        b.setBounds(0,0,0,0);
        exit.setFont(new Font("Arial", Font.PLAIN, 25));
        exit.setBounds(300,450,130,30);
    }

    public static void main(String[] args) throws InterruptedException { 
        Input input = new Input(); 
        input.loginSignal.await();
        Inventory inventory = new Inventory();
        inventory.initializeConnection();
        if (input.getInput() == null) {
            throw new OrderArgumentInvalidException();
        }
        Order example = new Order(input.getInput());
        String result = inventory.executeOrder(example);
        if(inventory.getComp() == true){
            input.orderComplete(result);
        } else {
            input.orderFailed(result);
        }
    }
}