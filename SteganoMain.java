/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Menu;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.MenuItem;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Scanner;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ import javax.swing.filechooser.FileNameExtensionFilter;
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
/*     */ public class SteganoMain
/*     */   extends JFrame
/*     */   implements ActionListener
/*     */ {
/*     */   private static final long serialVersionUID = 642140547550428960L;
/*     */   JLabel displayOutput;
/*     */   JFileChooser inputFile;
/*     */   JFileChooser outputSaveAs;
/*     */   JFileChooser txtFile;
/*     */   JLabel imageLabel;
/*     */   JLabel textLabel;
/*     */   JLabel outputLocationLabel;
/*     */   JTextField inputLocation;
/*     */   JTextField outputLocation;
/*     */   JButton inputBrowse;
/*     */   JButton outputBrowse;
/*     */   JButton resetButton;
/*     */   JButton createButton;
/*     */   JButton saveButton;
/*     */   JButton txtButton;
/*     */   JTextArea text;
/*     */   JPanel panel;
/*     */   MenuItem encodeDropDown;
/*     */   MenuItem decodeDropDown;
/*     */   MenuItem exitDropDown;
/*     */   Menu fileMenu;
/*     */   Menu exitMenu;
/*     */   MenuBar mb;
/*  66 */   FileFilter allImages = new FileNameExtensionFilter("All Images", ImageIO.getReaderFileSuffixes());
/*  67 */   FileFilter pngImages = new FileNameExtensionFilter("PNG Images", new String[] { "png" });
/*  68 */   FileFilter txtFiles = new FileNameExtensionFilter("TXT Files", new String[] { "txt" });
/*     */   File activeFile;
/*     */   File outputFile;
/*     */   File activeTxtFile;
/*     */   Image inputImg;
/*     */   Image outputImg;
/*     */   BufferedImage img;
/*     */   BufferedImage imgOut;
/*     */   int imgCols;
/*     */   int imgRows;
/*  78 */   boolean switcher = true;
/*     */   
/*     */ 
/*     */ 
/*  82 */   SteganoImgProcess processingObject = new SteganoImgProcess();
/*     */   
/*     */ 
/*     */   SteganoMain()
/*     */   {
/*     */     try
/*     */     {
/*  89 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/*  93 */     setDefaultCloseOperation(2);
/*     */     
/*     */ 
/*  96 */     this.displayOutput = new JLabel("");
/*  97 */     this.displayOutput.setBounds(20, 20, 100, 100);
/*     */     
/*     */ 
/* 100 */     this.imageLabel = new JLabel();
/* 101 */     this.imageLabel.setForeground(Color.WHITE);
/* 102 */     this.imageLabel.setText("Image:");
/* 103 */     this.imageLabel.setBounds(30, 20, 100, 30);
/* 104 */     this.inputLocation = new JTextField(30);
/* 105 */     this.inputLocation.setEditable(false);
/* 106 */     this.inputLocation.setBounds(30, 50, 220, 30);
/* 107 */     this.inputBrowse = new JButton("Choose");
/* 108 */     this.inputBrowse.addActionListener(this);
/* 109 */     this.inputBrowse.setBounds(260, 50, 80, 30);
/*     */     
/* 111 */     this.textLabel = new JLabel();
/* 112 */     this.textLabel.setText("Input text to be encoded:");
/* 113 */     this.textLabel.setForeground(Color.WHITE);
/* 114 */     this.textLabel.setBounds(30, 90, 200, 30);
/* 115 */     this.text = new JTextArea();
/* 116 */     this.text.setBounds(30, 120, 310, 140);
/*     */     
/* 118 */     this.txtButton = new JButton("or, Choose content from a file");
/* 119 */     this.txtButton.addActionListener(this);
/* 120 */     this.txtButton.setBounds(30, 260, 310, 30);
/* 121 */     this.txtButton.setVisible(true);
/*     */     
/* 123 */     this.outputLocationLabel = new JLabel();
/* 124 */     this.outputLocationLabel.setText("Destination File:");
/* 125 */     this.outputLocationLabel.setBounds(30, 300, 200, 30);
/* 126 */     this.outputLocationLabel.setForeground(Color.WHITE);
/* 127 */     this.outputLocation = new JTextField(30);
/* 128 */     this.outputLocation.setBounds(30, 330, 220, 30);
/* 129 */     this.outputBrowse = new JButton("Browse");
/* 130 */     this.outputBrowse.addActionListener(this);
/* 131 */     this.outputBrowse.setBounds(260, 330, 80, 30);
/*     */     
/* 133 */     this.resetButton = new JButton("Reset");
/* 134 */     this.resetButton.addActionListener(this);
/* 135 */     this.resetButton.setBounds(30, 380, 150, 30);
/* 136 */     this.createButton = new JButton("Encode");
/* 137 */     this.createButton.addActionListener(this);
/* 138 */     this.createButton.setBounds(190, 380, 150, 30);
/* 139 */     this.saveButton = new JButton("Save");
/* 140 */     this.saveButton.addActionListener(this);
/* 141 */     this.saveButton.setBounds(190, 380, 150, 30);
/* 142 */     this.saveButton.setVisible(false);
/*     */     
/* 144 */     this.panel = new JPanel()
/*     */     {
/*     */       private static final long serialVersionUID = 8104705757223466700L;
/*     */       
/*     */       public void paintComponent(Graphics g)
/*     */       {
/* 150 */         Image background = null;
/*     */         try {
/* 152 */           background = ImageIO.read(getClass().getResource("C:/Users/KAJAL/Downloads/simple/images/login_background%20-%20Copy.jpg"));
/*     */         }
/*     */         catch (IOException e) {
/* 155 */           e.printStackTrace();
/*     */         }
/* 157 */         g.drawImage(background, 0, 0, this);
/*     */       }
/* 159 */     };
/* 160 */     this.panel.setLayout(null);
/* 161 */     this.panel.add(this.displayOutput);
/* 162 */     this.panel.add(this.imageLabel);
/* 163 */     this.panel.add(this.inputLocation);
/* 164 */     this.panel.add(this.inputBrowse);
/* 165 */     this.panel.add(this.textLabel);
/* 166 */     this.panel.add(this.text);
/* 167 */     this.panel.add(this.outputLocation);
/* 168 */     this.panel.add(this.outputLocationLabel);
/* 169 */     this.panel.add(this.outputBrowse);
/* 170 */     this.panel.add(this.resetButton);
/* 171 */     this.panel.add(this.createButton);
/* 172 */     this.panel.add(this.saveButton);
/* 173 */     this.panel.add(this.txtButton);
/*     */     
/* 175 */     setTitle("Steganography - Encode(kunal)");
/* 176 */     add(this.panel);
/* 177 */     setVisible(true);
/* 178 */     setResizable(false);
/* 179 */     setSize(770, 480);
/* 180 */     setLocation(350, 100);
/*     */     
/* 182 */     this.encodeDropDown = new MenuItem("Encode");
/* 183 */     this.encodeDropDown.addActionListener(this);
/* 184 */     this.decodeDropDown = new MenuItem("Decode");
/* 185 */     this.decodeDropDown.addActionListener(this);
/* 186 */     this.fileMenu = new Menu("File");
/* 187 */     this.fileMenu.add(this.encodeDropDown);
/* 188 */     this.fileMenu.add(this.decodeDropDown);
/*     */     
/* 190 */     this.exitDropDown = new MenuItem("Exit Program");
/* 191 */     this.exitDropDown.addActionListener(this);
/* 192 */     this.exitMenu = new Menu("Exit");
/* 193 */     this.exitMenu.add(this.exitDropDown);
/*     */     
/* 195 */     this.mb = new MenuBar();
/* 196 */     this.mb.add(this.fileMenu);
/* 197 */     this.mb.add(this.exitMenu);
/* 198 */     setMenuBar(this.mb);
/*     */   }
/*     */   
/*     */ 
/*     */   public void actionPerformed(ActionEvent mouseClick)
/*     */   {
/* 204 */     if (mouseClick.getSource() == this.exitDropDown) {
/* 205 */       System.out.println("Exiting the software.");
/* 206 */       System.exit(0);
/*     */     }
/* 208 */     else if (mouseClick.getSource() == this.resetButton) {
/* 209 */       this.displayOutput.setIcon(null);
/* 210 */       this.inputLocation.setText(null);
/* 211 */       this.outputLocation.setText(null);
/* 212 */       this.text.setText(null);
/*     */     }
/* 214 */     else if (mouseClick.getSource() == this.encodeDropDown) {
/* 215 */       this.resetButton.doClick();
/* 216 */       this.createButton.setVisible(true);
/* 217 */       this.saveButton.setVisible(false);
/* 218 */       setTitle("Steganography - Encode(kunal)");
/* 219 */       this.textLabel.setText("Input text to be encoded:");
/* 220 */       this.switcher = true;
/* 221 */       this.text.setBounds(30, 120, 310, 140);
/* 222 */       this.txtButton.setVisible(true);
/*     */     }
/* 224 */     else if (mouseClick.getSource() == this.decodeDropDown) {
/* 225 */       this.resetButton.doClick();
/* 226 */       this.createButton.setVisible(false);
/* 227 */       this.saveButton.setVisible(true);
/* 228 */       setTitle("Steganography - Decode(kunal)");
/* 229 */       this.textLabel.setText("Decoded text:");
/* 230 */       this.switcher = false;
/* 231 */       this.text.setBounds(30, 120, 310, 170);
/* 232 */       this.txtButton.setVisible(false);
/*     */     }
/* 234 */     else if (mouseClick.getSource() == this.inputBrowse) {
/* 235 */       if (this.inputFile == null) {
/* 236 */         this.inputFile = new JFileChooser();
/*     */       }
/*     */       
/*     */ 
/* 240 */       this.inputFile.setAcceptAllFileFilterUsed(false);
/* 241 */       if (this.switcher) {
/* 242 */         this.inputFile.removeChoosableFileFilter(this.pngImages);
/* 243 */         this.inputFile.addChoosableFileFilter(this.allImages);
/*     */       } else {
/* 245 */         this.inputFile.removeChoosableFileFilter(this.allImages);
/* 246 */         this.inputFile.addChoosableFileFilter(this.pngImages);
/*     */       }
/*     */       
/*     */ 
/* 250 */       int openFile = this.inputFile.showDialog(null, "Open file");
/* 251 */       if (openFile == 0) {
/* 252 */         this.activeFile = this.inputFile.getSelectedFile();
/* 253 */         this.inputLocation.setText(this.activeFile.getAbsolutePath());
/* 254 */         this.outputSaveAs = new JFileChooser(this.activeFile);
/*     */       }
/*     */     }
/* 257 */     else if (mouseClick.getSource() == this.outputBrowse) {
/* 258 */       if (this.outputSaveAs == null) {
/* 259 */         this.outputSaveAs = new JFileChooser();
/*     */       }
/*     */       
/*     */ 
/* 263 */       this.outputSaveAs.setAcceptAllFileFilterUsed(false);
/* 264 */       if (this.switcher) {
/* 265 */         this.outputSaveAs.removeChoosableFileFilter(this.txtFiles);
/* 266 */         this.outputSaveAs.addChoosableFileFilter(this.pngImages);
/*     */       }
/*     */       else {
/* 269 */         this.outputSaveAs.removeChoosableFileFilter(this.pngImages);
/* 270 */         this.outputSaveAs.addChoosableFileFilter(this.txtFiles);
/*     */       }
/*     */       
/*     */ 
/* 274 */       int openDirectory = this.outputSaveAs.showDialog(null, "Save As");
/* 275 */       if (openDirectory == 0) {
/* 276 */         this.outputFile = this.outputSaveAs.getSelectedFile();
/* 277 */         if (this.switcher) {
/* 278 */           this.outputLocation.setText(this.outputFile.getAbsolutePath() + ".png");
/*     */         } else {
/* 280 */           this.outputLocation.setText(this.outputFile.getAbsolutePath() + ".txt");
/*     */         }
/*     */       }
/* 283 */     } else if (mouseClick.getSource() == this.txtButton) {
/* 284 */       if (this.txtFile == null) {
/* 285 */         this.txtFile = new JFileChooser();
/*     */       }
/*     */       
/* 288 */       int openText = this.txtFile.showDialog(null, "Open file");
/* 289 */       if (openText == 0)
/*     */       {
/* 291 */         this.activeTxtFile = this.txtFile.getSelectedFile();
/*     */         try {
/* 293 */           this.text.setText(new Scanner(this.activeTxtFile).useDelimiter("\\A").next());
/*     */         } catch (FileNotFoundException e) {
/* 295 */           System.out.println("Text file not found!");
/* 296 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 300 */     else if (mouseClick.getSource() == this.createButton) {
/*     */       try {
/* 302 */         this.img = ImageIO.read(new File(this.activeFile.getAbsolutePath()));
/* 303 */         this.imgOut = ImageIO.read(new File(this.activeFile.getAbsolutePath()));
/*     */       }
/*     */       catch (IOException e) {
/* 306 */         System.out.println("error reading file!");
/* 307 */         e.printStackTrace();
/*     */       }
/* 309 */       this.imgCols = this.img.getWidth();
/* 310 */       this.imgRows = this.img.getHeight();
/*     */       
/*     */ 
/* 313 */       boolean success = this.processingObject.encode(this.img, this.imgOut, this.imgCols, this.imgRows, this.text.getText(), this.outputLocation.getText());
/*     */       
/* 315 */       if (success) {
/* 316 */         JOptionPane.showMessageDialog(this, "Successfully created image", "Success", 1);
/*     */       } else {
/* 318 */         JOptionPane.showMessageDialog(this, "Problem in saving file!", "Error", 0);
/*     */ 
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */     }
/* 326 */     else if (mouseClick.getSource() == this.saveButton) {
/*     */       try {
/* 328 */         this.img = ImageIO.read(new File(this.activeFile.getAbsolutePath()));
/*     */       } catch (IOException e) {
/* 330 */         System.out.println("error reading file!");
/* 331 */         e.printStackTrace();
/*     */       }
/* 333 */       this.imgCols = this.img.getWidth();
/* 334 */       this.imgRows = this.img.getHeight();
/*     */       
/*     */ 
/* 337 */       String decodedMsg = this.processingObject.decode(this.img, this.imgCols, this.imgRows);
/*     */       
/* 339 */       if (decodedMsg == null) {
/* 340 */         JOptionPane.showMessageDialog(this, "Incorrect file or corrupted message", "Error", 0);
/* 341 */         this.resetButton.doClick();
/*     */       } else {
/* 343 */         this.text.setText(decodedMsg);
/*     */         try
/*     */         {
/* 346 */           PrintWriter out = new PrintWriter(this.outputLocation.getText());
/* 347 */           out.print(decodedMsg);
/* 348 */           out.close();
/* 349 */           JOptionPane.showMessageDialog(this, "Successfully created TXT file", "Success", 1);
/*     */         } catch (FileNotFoundException e) {
/* 351 */           System.out.println("text file could not be saved.");
/* 352 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\life of kunal\DCIM\kunalst1.jar!\SteganoMain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */