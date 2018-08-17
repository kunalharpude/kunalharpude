/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ public class UnusedSteganoImgProcess
/*     */ {
/*     */   void encode(BufferedImage input, BufferedImage output, int width, int height, String msg, String outputName)
/*     */   {
/*  14 */     String message = msg + "!";
/*  15 */     int msgLength = message.length();
/*     */     
/*  17 */     int count = 0;
/*     */     
/*  19 */     int[] twoBitMessage = new int[4 * msgLength];
/*     */     
/*  21 */     for (int i = 0; i < msgLength; i++) {
/*  22 */       char currentChar = message.charAt(i);
/*  23 */       twoBitMessage[(4 * i + 0)] = (currentChar >> '\006' & 0x3);
/*  24 */       twoBitMessage[(4 * i + 1)] = (currentChar >> '\004' & 0x3);
/*  25 */       twoBitMessage[(4 * i + 2)] = (currentChar >> '\002' & 0x3);
/*  26 */       twoBitMessage[(4 * i + 3)] = (currentChar & 0x3);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  31 */     for (int i = 0; i < width; i++) {
/*  32 */       for (int j = 0; j < height; j++) {
/*  33 */         if (count >= 4 * msgLength) break;
/*  34 */         int pixel = input.getRGB(i, j);
/*     */         
/*  36 */         int alpha = pixel >> 24 & 0xFF;
/*  37 */         int red = pixel >> 16 & 0xFF;
/*  38 */         int green = pixel >> 8 & 0xFF;
/*  39 */         int blue = pixel >> 0 & 0xFF;
/*     */         
/*  41 */         System.out.println("al" + alpha);
/*  42 */         System.out.println(red);
/*  43 */         System.out.println(green);
/*  44 */         System.out.println(blue);
/*     */         
/*  46 */         int rOut = red & 0xFC | twoBitMessage[(count++)];
/*  47 */         if (count == 4 * msgLength) {
/*     */           break;
/*     */         }
/*  50 */         int gOut = green & 0xFC | twoBitMessage[(count++)];
/*  51 */         if (count == 4 * msgLength) {
/*     */           break;
/*     */         }
/*  54 */         int bOut = blue & 0xFC | twoBitMessage[(count++)];
/*  55 */         int aOut = alpha;
/*     */         
/*  57 */         System.out.println(rOut);
/*  58 */         System.out.println(gOut);
/*  59 */         System.out.println(bOut);
/*  60 */         System.out.println(aOut);
/*     */         
/*  62 */         int pixOut = aOut << 24 | rOut << 16 | gOut << 8 | bOut;
/*     */         
/*  64 */         System.out.println("ths " + pixOut);
/*     */         
/*  66 */         output.setRGB(i, j, pixOut);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/*  76 */       ImageIO.write(output, "png", new File(outputName));
/*     */     } catch (IOException e) {
/*  78 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   String decode(BufferedImage input, int width, int height)
/*     */   {
/*  85 */     StringBuffer decodedMsg = new StringBuffer();
/*  86 */     Deque<Integer> listChar = new ArrayDeque();
/*     */     
/*     */ 
/*  89 */     for (int i = 0; i < width; i++) {
/*  90 */       for (int j = 0; j < height; j++) {
/*  91 */         int pixel = input.getRGB(i, j);
/*     */         
/*  93 */         int red = pixel >> 16 & 0xFF;
/*  94 */         int green = pixel >> 8 & 0xFF;
/*  95 */         int blue = pixel >> 0 & 0xFF;
/*     */         
/*  97 */         int rOut = red & 0x3;
/*  98 */         int gOut = green & 0x3;
/*  99 */         int bOut = blue & 0x3;
/*     */         
/* 101 */         listChar.add(Integer.valueOf(rOut));
/* 102 */         listChar.add(Integer.valueOf(gOut));
/* 103 */         listChar.add(Integer.valueOf(bOut));
/*     */         
/* 105 */         if (listChar.size() >= 4) {
/* 106 */           int charOut = ((Integer)listChar.pop()).intValue() << 6 | ((Integer)listChar.pop()).intValue() << 4 | ((Integer)listChar.pop()).intValue() << 2 | ((Integer)listChar.pop()).intValue();
/* 107 */           if ((char)charOut == '!') {
/*     */             break;
/*     */           }
/* 110 */           decodedMsg.append((char)charOut);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 117 */     String outputMsg = new String(decodedMsg);
/*     */     
/* 119 */     return outputMsg;
/*     */   }
/*     */ }


/* Location:              D:\life of kunal\DCIM\kunalst1.jar!\UnusedSteganoImgProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */