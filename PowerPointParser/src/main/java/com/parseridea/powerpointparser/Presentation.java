package com.parseridea.powerpointparser;

import com.parseridea.powerpointparser.Iterator.Slide;
import com.parseridea.powerpointparser.Iterator.Slides;
import com.parseridea.powerpointparser.PresentationElements.Hyperlink;
import com.parseridea.powerpointparser.PresentationElements.Image;
import com.parseridea.powerpointparser.PresentationElements.Text;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

/**
 * class for parse elements to pptx
 */
public class Presentation {
    /**
     * @param slides to convert
     */
   public static void createPresentation(Slides slides) throws IOException {
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Выбор папки для сохранения");
       fileChooser.setInitialFileName("Презентация");
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Презентация", "*.pptx"));
       File file = fileChooser.showSaveDialog(null);
       if (file != null) {
           XMLSlideShow ppt = new XMLSlideShow();
           for (Slide slide : slides.getSlides()) {
               XSLFSlide Xslide = ppt.createSlide();
               Xslide.getBackground().setFillColor(ColorParser.colorParseToAWT(slide.getBackground()));
               for (Text text : slide.getTexts()) {
                   XSLFTextBox shape = Xslide.createTextBox();
                   XSLFTextParagraph p = shape.getTextParagraphs().get(0);
                   XSLFTextRun r = p.addNewTextRun();
                   r.setText(text.getText());
                   r.setFontColor(Color.black);
                   r.setFontSize(10.);
                   shape.setAnchor(new Rectangle((int) text.getX(), (int) text.getY(), (int) text.getWidth()*2, (int) text.getHeight()));
               }
               for (Hyperlink hyperlink : slide.getHyperlinks()) {
                   XSLFTextBox shape = Xslide.createTextBox();
                   XSLFTextParagraph p = shape.getTextParagraphs().get(0);
                   XSLFTextRun r = p.addNewTextRun();
                   XSLFHyperlink link = r.createHyperlink();
                   r.setText(hyperlink.getAddress());
                   r.setFontColor(Color.black);
                   r.setFontSize(10.);
                   link.setAddress(hyperlink.getAddress());
                   shape.setAnchor(new Rectangle((int) hyperlink.getX(), (int) hyperlink.getY(), (int) hyperlink.getWidth()*2, (int) hyperlink.getHeight()));
               }
               for (Image image : slide.getImages()) {
                   byte[] pictureData = IOUtils.toByteArray(new FileInputStream(image.getImageSource()));
                   XSLFPictureData pd = ppt.addPicture(pictureData, PictureData.PictureType.PNG);
                   XSLFPictureShape picture = Xslide.createPicture(pd);
                   picture.setAnchor(new Rectangle((int) image.getX(), (int) image.getY(), (int) image.getWidth(), (int) image.getHeight()));
               }
           }
           FileOutputStream out = new FileOutputStream(file);
           ppt.write(out);
           out.close();
       }

   }

    /**
     * @param ppt presentation object
     */
   public static void getAllObjects(XMLSlideShow ppt) throws IOException {
       int i=0,j=1;
       try(FileWriter writer = new FileWriter("presentation-text.txt", false)) {
           for (XSLFSlide slide : ppt.getSlides()) {
               writer.write("Слайд № "+j+":\n");
               j++;
               for (XSLFShape myShape : slide.getShapes()) {
                   if (myShape != null && myShape.getClass().getName().contains("TextBox")) {
                       XSLFTextBox myTextBox = (XSLFTextBox) myShape;
                       for (XSLFTextParagraph paragraph : myTextBox.getTextParagraphs())
                           for (XSLFTextRun run :paragraph.getTextRuns()) {
                               if(run.getHyperlink()!=null) writer.write("Гиперссылка-" +run.getRawText() + "\n");
                               else writer.write("Текст-" + run.getRawText() + "\n");

                           }
                   }
               }
           }
           writer.flush();
       }
       catch(IOException ex){

           System.out.println(ex.getMessage());
       }
       for(XSLFPictureData picture:ppt.getPictureData())
       {
           i++;
           javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(picture.getData()));
           ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", new File(i +".png"));
       }
       System.out.println("Успешно!");
   }
}
