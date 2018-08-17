/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Login
/*     */   extends JFrame
/*     */   implements ActionListener
/*     */ {
/*     */   private static final long serialVersionUID = -3010597082691149848L;
/*     */   final JTextField nameInput;
/*     */   final JTextField passInput;
/*     */   JLabel username;
/*     */   JLabel password;
/*     */   JButton submit;
/*     */   JPanel panel;
/*     */   
/*     */   Login()
/*     */   {
/*     */     try
/*     */     {
/*  55 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/*     */ 
/*  60 */     this.username = new JLabel();
/*  61 */     this.username.setText("USERNAME  :");
/*  62 */     this.username.setForeground(Color.WHITE);
/*  63 */     this.nameInput = new JTextField(15);
/*     */     
/*     */ 
/*  66 */     this.password = new JLabel();
/*  67 */     this.password.setText("PASSWORD :");
/*  68 */     this.password.setForeground(Color.WHITE);
/*  69 */     this.passInput = new JPasswordField(15);
/*     */     
/*     */ 
/*  72 */     this.submit = new JButton("SUBMIT");
/*  73 */     this.submit.setMnemonic(10);
/*  74 */     getRootPane().setDefaultButton(this.submit);
/*     */     
/*  76 */     setDefaultCloseOperation(3);
/*     */     
/*  78 */     this.panel = new JPanel()
/*     */     {
/*     */       private static final long serialVersionUID = 7202207766947468085L;
/*     */       
/*     */       public void paintComponent(Graphics g) {
/*  83 */         Image background = null;
/*     */         try {
/*  85 */           background = ImageIO.read(getClass().getResource("C:/Users/KAJAL/Downloads/simple/images/login_background%20-%20Copy.jpg"));
/*     */         }
/*     */         catch (IOException e) {
/*  88 */           e.printStackTrace();
/*     */         }
/*  90 */         g.drawImage(background, 0, 0, this);
/*     */       }
/*     */       
/*     */ 
/*  94 */     };
/*  95 */     this.panel.setLayout(null);
/*  96 */     this.username.setBounds(50, 125, 150, 50);
/*  97 */     this.nameInput.setBounds(130, 135, 150, 30);
/*  98 */     this.password.setBounds(50, 175, 150, 50);
/*  99 */     this.passInput.setBounds(130, 185, 150, 30);
/* 100 */     this.submit.setBounds(130, 230, 150, 30);
/* 101 */     this.panel.add(this.username);
/* 102 */     this.panel.add(this.nameInput);
/* 103 */     this.panel.add(this.password);
/* 104 */     this.panel.add(this.passInput);
/* 105 */     this.panel.add(this.submit);
/* 106 */     this.submit.addActionListener(this);
/* 107 */     add(this.panel);
/*     */     
/*     */ 
/* 110 */     setResizable(false);
/* 111 */     setSize(770, 480);
/* 112 */     setVisible(true);
/* 113 */     setLocation(350, 100);
/* 114 */     setTitle("LOGIN FORM");
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent submitCredentials) {
/* 118 */     String name = this.nameInput.getText();
/* 119 */     String pass = this.passInput.getText();
/*     */     
/* 121 */     if ((name.equals("kunal")) && (pass.equals("kunal123"))) {
/* 122 */       SteganoMain page = new SteganoMain();
/* 123 */       dispose();
/* 124 */       page.setVisible(true);
/*     */     } else {
/* 126 */       System.out.println("enter the valid username and password");
/* 127 */       JOptionPane.showMessageDialog(this, "Incorrect login or password", "Error", 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\life of kunal\DCIM\kunalst1.jar!\Login.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */