/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.swing.JWindow;
/*    */ 
/*    */ public class Start
/*    */   extends JWindow implements Runnable
/*    */ {
/*    */   private static final long serialVersionUID = -1348168212720533419L;
/*    */   
/*    */   Start()
/*    */   {
/* 15 */     setVisible(true);
/* 16 */     setSize(900, 250);
/* 17 */     setLocationRelativeTo(null);
/*    */     
/* 19 */     Thread t = new Thread(this);
/* 20 */     t.start();
/*    */   }
/*    */   
/*    */   public void run()
/*    */   {
/*    */     try {
/* 26 */       Thread.sleep(5000L);
/*    */       
/* 28 */       dispose();
/*    */       
/* 30 */       new Login();
/*    */     } catch (InterruptedException intEx) {
/* 32 */       System.out.print("Process interrupted abnormally!");
/* 33 */       System.exit(1);
/*    */     }
/*    */   }
/*    */   
/*    */   public void paint(Graphics g)
/*    */   {
/* 39 */     Image splashScreen = null;
/*    */     try {
/* 41 */       splashScreen = ImageIO.read(getClass().getResource("C:/Users/KAJAL/Downloads/simple/images/login_background%20-%20Copy.jpg"));
/*    */     }
/*    */     catch (IOException e) {
/* 44 */       e.printStackTrace();
/*    */     }
/*    */     
/*    */ 
/* 48 */     int xPosition = (getWidth() - splashScreen.getWidth(null)) / 2;
/* 49 */     int yPosition = (getHeight() - splashScreen.getHeight(null)) / 2;
/* 50 */     g.drawImage(splashScreen, xPosition, yPosition, this);
/*    */   }
/*    */   
/*    */   public static void main(String[] args)
/*    */   {
/* 55 */     Start newInstance = new Start();
/*    */   }
/*    */ }


/* Location:              D:\life of kunal\DCIM\kunalst1.jar!\Start.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */