import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.*;
import java.util.Random;


public class Program {

    static final int board_width = 73;
    static final int board_height = 25;

    static final int button_width = 35;
    static final int button_height = 35;
    
    private static boolean allow;
    private static boolean trying;
    private static boolean pause;
    
    private static Color color;
    
    private static int gen_amount;
    private static int seconds;
    private static int secret_count;
    private static int run;
    
    static JFrame frame;
    static JFrame game_frame;
    
    static JTextField en_gen;
    static JTextField secon;
    
    static JButton confirm;
    
    static JLabel count;
    
    static Random rnd = new Random();
    
    public static void main(String[] args) throws InterruptedException, IOException
    {
	    	main_menu();
    }
	        

	public static void main_menu() throws InterruptedException, IOException {
		frame = new JFrame("Game of Life - Nevo Kaplan");
        JPanel open = new JPanel();
        JPanel choice = new JPanel();
        JPanel gene = new JPanel();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("hamar_s.png");
        BufferedImage img = ImageIO.read(input);
        open.setLayout(null);
        run = 0;
        allow = false;
        trying = true;
        pause = false;
        Font font = new Font("Segoe Script", Font.BOLD, 60);
        JButton hi = new JButton("Start");
        JTextArea text = new JTextArea("Game Of Life - Nevo Kaplan");
        JRadioButton cyan = new JRadioButton("Cyan");
        JRadioButton pink = new JRadioButton("Magenta");
        JRadioButton yellow = new JRadioButton("Red");
        JRadioButton black = new JRadioButton("Black");
        JRadioButton green = new JRadioButton("Green");
        JRadioButton orange = new JRadioButton("Orange");
        JButton example = new JButton();
        JLabel label = new JLabel("Color of living cells");
        JLabel gen = new JLabel("Enter amount of generations");
        JLabel time = new JLabel("Enter amount of time to place cells");
        Font font3 = new Font("TimesRoman", Font.ITALIC, 30);
        confirm = new JButton("Confirm");
        en_gen = new JTextField("100", 5);
        secon = new JTextField("10", 5);
        gen_amount = 100;
        seconds = 10;
        text.setEditable(false);
        text.setFont(font);
        frame.getContentPane().add(open);
        open.add(choice);
        open.add(gene);
        open.setBounds(0, 0, 1500, 1000);
        choice.setBounds(620, 300, 260, 100);
        gene.setBounds(475, 430, 550, 250);
        example.setBounds(880, 350, 50, 50);
        ButtonGroup group = new ButtonGroup();
        group.add(cyan);
        group.add(pink);
        group.add(black);
        group.add(green);
        group.add(orange);
        group.add(yellow);
        cyan.setSelected(true);
        choice.add(label);
        choice.add(cyan); 
        choice.add(pink);
        choice.add(black);
        choice.add(green);
        choice.add(orange);
        choice.add(yellow);
        gene.add(gen);
        gene.add(en_gen);
        gene.add(time);
        gene.add(secon);
        gene.add(confirm);
        label.setFont(font3);
        gen.setFont(font3);
        time.setFont(font3);
        open.add(example);
        hi.setBackground(Color.orange);
        hi.setBounds(710, 800, 100, 50);
        example.setBorderPainted(false);
        text.setBounds(275, 50, 940, 90);  
        open.add(text);
        open.add(hi);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1500,1000);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
        frame.setVisible(true);
        confirm.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ac) {
            	if (ac.getSource() == confirm) {
            		 if (check_int(en_gen.getText(), 1)) 
                     	gen_amount = Integer.parseInt(en_gen.getText());
                     if (check_int(secon.getText(), 2)) 
                    	 seconds = Integer.parseInt(secon.getText());
            	}}
        });
        hi.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
                	if ((a.getSource() == hi) && confirm.getBackground() != Color.white) {
                		allow = true;
                		trying = false;
                		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
                		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        		}
        	}
        });
        example.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
            	if (a.getSource() == example) 
            		secret_count += 1;
    	}
        });
        while (trying) {
	        if (cyan.isSelected())
	        	color = Color.cyan;
	        else if (pink.isSelected())
	        	color = Color.magenta;
	        else if (yellow.isSelected())
	        	color = Color.red;
	        else if (black.isSelected())
	        	color = Color.black;
	        else if (orange.isSelected())
	        	color = Color.orange;
	        else if (green.isSelected())
	        	color = Color.green;
	        example.setBackground(color);
	        if (secret_count >= 10) {
	        	example.setIcon(new ImageIcon (img));
	        }
        }
        create_board();
       }
	
	public static void create_board() throws InterruptedException{
		game_frame = new JFrame("Game of Life - Nevo Kaplan");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JButton[][] buttons = new JButton[board_width][board_height];
		JButton home = new JButton("Pause");
		JButton plus = new JButton("+");
		JButton random = new JButton("Random");
        boolean[][] guide = new boolean [board_width][board_height];
        boolean[][] was_alive = new boolean [board_width][board_height];
		count = new JLabel("Generations: 0 / " + gen_amount);
		game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game_frame.setSize(1500,1000);
		game_frame.setLocation(dim.width/2-game_frame.getSize().width/2, dim.height/2-game_frame.getSize().height/2);
		game_frame.setLayout(null);
		Font font2 = new Font("TimesRoman", Font.ITALIC, 30);
		if (allow) {
	        	for (int x = 0; x < board_width; x++)
	            {
	                for (int y = 0; y < board_height; y++)
	                {
	                    buttons[x][y] = new JButton("");
	                    buttons[x][y].setBounds(button_width * x, button_height * y ,button_width, button_height);  // x, y, width, height
	                    buttons[x][y].setBackground(Color.white);
	                    buttons[x][y].setVisible(true);
	                    buttons[x][y].addActionListener(new ActionListener()
	                    {
	                        public void actionPerformed(ActionEvent e)
	                        {
	                            JButton current_button = (JButton) e.getSource();
	                            if (current_button.getBackground() != color)
	                                current_button.setBackground(color);
	                            else 
	                                current_button.setBackground(Color.white);
	                            adding(buttons, guide, was_alive);
	                        }
	                    });
	
	                     game_frame.add(buttons[x][y]);
	                }
	            }
	        	game_frame.setVisible(true);
	        for (int i = 0; i < board_width; i++)
	        	for (int j = 0; j < board_height; j++) {
	        		if (buttons[i][j].getBackground() == color) {
	        			guide[i][j] = true;
	        			was_alive[i][j] = true;
	        		}
	        		else {
	        			guide[i][j] = false;
	        			was_alive[i][j] = false;
	        		}
	        	}
	     home.setBackground(Color.orange);
	     home.setBounds(700, 900, 100, 50);
	     plus.setBounds(25, 885, 41, 41);
	     random.setBackground(color);
	     random.setBounds(850, 900, 100, 50);
		 game_frame.add(count);
		 count.setBounds(70, 880, 500, 50);
		 count.setFont(font2);
	     home.addActionListener(new ActionListener() {
	    	 public void actionPerformed(ActionEvent e)
             {
	    		 if (e.getSource() == home) {
	    			 if (home.getText().equals("Pause")) {
	    				 pause = true;
	    				 home.setText("Resume");
	    				 
	    			 }
	    			 else {
	    				 home.setText("Pause");
	    				 pause = false;
	    			 }
	    		 }
             }
	     });
	     plus.addActionListener(new ActionListener() {
	    	 public void actionPerformed(ActionEvent e)
             {
	    		 if (e.getSource() == plus) {
	    			 gen_amount += 10;
	    			 count.setText("Generations: " + run + " / " + gen_amount);
	    		 }
             }
	     });
	     random.addActionListener(new ActionListener() {
	    	 public void actionPerformed(ActionEvent e) {
	    		 if (e.getSource() == random) {
	    			 for (int i = 0; i < board_width; i++)
	    		        	for (int j = 0; j < board_height; j++)
	    		        		if (rnd.nextInt(5) == 1) {
	    		        			buttons[i][j].setBackground(color);
	    		        			guide[i][j] = true;
	    		        			was_alive[i][j] = true;
	    		        		}
	    		 }
	    	 }
	     });
	     game_frame.add(random);
	     game_frame.add(home);
	     game_frame.add(plus);
        TimeUnit.SECONDS.sleep(seconds);
        running(buttons, guide, was_alive);
        }
	}
   
    public static void adding (JButton[][] buttons, boolean[][] guide, boolean[][] was_alive) {
    	for (int i = 0; i < board_width; i++)
        	for (int j = 0; j < board_height; j++) {
        		if (buttons[i][j].getBackground() == color) {
        			guide[i][j] = true;
        			was_alive[i][j] = true;
        		}
        		else 
        			guide[i][j] = false;
        	}
    }
    
    public static void running(JButton[][] buttons, boolean[][] guide, boolean[][] was_alive) throws InterruptedException {
    	int sum = 0;
    	boolean current_alive;
    	for (run = 1; run <= gen_amount; run++) {
	    	 for (int col = 0; col < board_height; col++){
	    		 for (int row = 0; row < board_width; row++) {
	    			 for (int i = -1; i <= 1; i++) 
	    				 for (int j = -1; j <= 1; j++)
	    					 if (!(j == 0 && i == 0))
	    						 try {
			    					 if (buttons[row + i][col + j].getBackground() == color)
			    						 sum++;
	    						 } catch(Exception e) {}
    				
	    			 if (buttons[row][col].getBackground() == color)
    					current_alive = true;
    				else
    					current_alive = false;

	    			// rule #1
    				if (current_alive && (sum <= 1 || sum > 3))
	    				guide[row][col] = false;
    				
	    			// rule #2 & #3
	    			else if ((!current_alive && sum == 3) || (current_alive && (sum > 1 && sum <= 3))) {
	    				guide[row][col] = true;
	    				was_alive[row][col] = true;
	    			}
    				sum = 0;
    			}
    		}
	    	// just so the pause would work
	    	while (pause) {
	    		System.out.print("");
	    	 	}
	    	count.setText("Generations: " + run + " / " + gen_amount);
	    	update(buttons, guide, was_alive);
	    	TimeUnit.MILLISECONDS.sleep(600);
    	}
    	JOptionPane.showMessageDialog(game_frame, "Program reched set generation amount.");
    	game_frame.dispatchEvent(new WindowEvent(game_frame, WindowEvent.WINDOW_CLOSING));
    }
    
    public static void update(JButton[][] buttons, boolean[][] guide, boolean[][] was_alive) {
    	for (int x = 0; x < board_width; x++)
    		for (int y = 0; y < board_height; y++) {
    			if (guide[x][y])
    				buttons[x][y].setBackground(color);
    			else if (was_alive[x][y])
    				buttons[x][y].setBackground(Color.lightGray);
    			else
    				buttons[x][y].setBackground(Color.white);
    		}
    }
    
    public static boolean check_int(String str, int num) {
    	boolean flag = false;
    	String s;
    	try {
    		Integer.parseInt(str);
    		flag = true;
    		confirm.setBackground(Color.green);
    	}
    	catch(NumberFormatException currentError) {
    		if (num == 1) {
	    		JOptionPane.showMessageDialog(frame, "Generations amount must be a full number.");
	    		s = "" + gen_amount;
	    		en_gen.setText(s);
	    		}
    		else {
    			JOptionPane.showMessageDialog(frame, "time amount must be a full number.");
    			s = "" + seconds;
    			secon.setText(s);
    			}
    		confirm.setBackground(Color.white);
    	}
    	return flag;
    }
}
