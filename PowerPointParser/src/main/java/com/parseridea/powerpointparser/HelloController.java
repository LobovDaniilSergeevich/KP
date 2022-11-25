package com.parseridea.powerpointparser;

import com.parseridea.powerpointparser.Iterator.Iterator;
import com.parseridea.powerpointparser.Iterator.Slide;
import com.parseridea.powerpointparser.Iterator.Slides;
import com.parseridea.powerpointparser.PresentationElements.Hyperlink;
import com.parseridea.powerpointparser.PresentationElements.Image;
import com.parseridea.powerpointparser.PresentationElements.PresentationNode;
import com.parseridea.powerpointparser.PresentationElements.Text;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * controller class
 */
public class HelloController implements Initializable {

    public TextField imageSource;
    public Spinner<Integer> slidenum;
    public Label slideCurrentNum;
    public Pane slide;
    public ColorPicker pallette;
    Slides slides;
    Slide currentSlide;
    private File imageFile;
    Iterator iterator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slides = new Slides(new ArrayList<>());
        currentSlide = new Slide();
        slides.addSlide(currentSlide);
        iterator = slides.getIterator();
        updateSpinner();
    }
    private void updateSpinner()
    {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,slides.getSlides().size(),1);
        slidenum.setValueFactory(valueFactory);
    }
    @FXML
    private void selectImageSource()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл с изображением");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Изображение", "*.png");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file!=null) {
            imageSource.setText(file.getAbsolutePath());
            imageFile = file;
        }
    }
    @FXML
    private void openAsImages() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Презентация (*.pptx)", "*.pptx"));
        fileChooser.setInitialFileName("Презентация");
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
            slides = new Slides(ppt);
            currentSlide = slides.getSlides().get(0);
            slide.setBackground(new Background(new BackgroundImage(SwingFXUtils.toFXImage(currentSlide.getPptxBG(),null),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(100,100,true,true,true,true))));
            iterator = slides.getIterator();
            updateSpinner();
        }
    }
    @FXML
    private void convertToImages() throws IOException {
        Presentation.convertToImages(Objects.requireNonNull(Presentation.createPresentation(slides)));
    }
    @FXML
    private void convertToImage() throws IOException {
        Presentation.convertToImage(Objects.requireNonNull(Presentation.createPresentation(slides)),iterator.getCurrent());
    }
    @FXML
    private void addImage()
    {
        if(imageFile!=null) {
            Image image = currentSlide.addImage(imageFile.getAbsolutePath());
            draw(image);
        }
    }
    @FXML
    private void addHyperlink()
    {
        Hyperlink link = currentSlide.addLink("ссылка");
        draw(link);
    }
    @FXML
    private void addText()
    {
        Text text = currentSlide.addText("текст");
        draw(text);
    }
    private void draw(PresentationNode node)
    {
        node.setOnMouseDragged(mouseEvent -> node.updatePosition(mouseEvent.getX(), mouseEvent.getY()));
        slide.getChildren().add(node);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction(event -> {
            slide.getChildren().remove(node);
            currentSlide.removeObject(node);
        });
        contextMenu.getItems().addAll(deleteItem);
        node.setOnContextMenuRequested(e->contextMenu.show(node,e.getScreenX(),e.getScreenY()));
        node.draw();
    }
    public void next()
    {
        clearNode(currentSlide);
            currentSlide = (Slide) iterator.next();
            setSlide(currentSlide);
            if(currentSlide.getPptxBG()!=null)
                slide.setBackground(new Background(new BackgroundImage(SwingFXUtils.toFXImage(currentSlide.getPptxBG(),null),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(100,100,true,true,true,true))));
        slideCurrentNum.setText(String.valueOf(iterator.getCurrent() + 1));
    }
    public void prev()
    {
        slideCurrentNum.setText(String.valueOf(iterator.getCurrent() + 1));
            Slide sld = currentSlide;
            currentSlide = (Slide) iterator.prev();
            clearNode(sld);
            setSlide(currentSlide);
            if(currentSlide.getPptxBG()!=null)
                slide.setBackground(new Background(new BackgroundImage(SwingFXUtils.toFXImage(currentSlide.getPptxBG(),null),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(100,100,true,true,true,true))));
    }
    public void setSlide(Slide slideObj)
    {
        slide.setBackground(new Background(new BackgroundFill(slideObj.getBackground(), CornerRadii.EMPTY, Insets.EMPTY)));
        for(Text text: slideObj.getTexts()) {
            draw(text);
        }
        for(Hyperlink hyperlink: slideObj.getHyperlinks()) {
            draw(hyperlink);
        }
        for(Image image: slideObj.getImages()) {
            draw(image);
        }
    }
    public void addSlide()
    {
        slides.addSlide(new Slide());
        updateSpinner();
    }
    public void replaceSlide()
    {
        clearNode(currentSlide);
        currentSlide = (Slide) iterator.replace(slidenum.getValue()-1);
        slideCurrentNum.setText(String.valueOf(iterator.getCurrent()+1));
        setSlide(currentSlide);
        updateSpinner();
    }
    @FXML
    private void removeSlide()
    {
        if(slides.getSlides().size()>1) {
            Slide deletable=currentSlide;
            next();
            slides.getSlides().remove(deletable);
            updateSpinner();
        }
    }
    public void clearNode(Slide slideObj)
    {
        for(Text text: slideObj.getTexts()) {
            slide.getChildren().remove(text);
        }
        for(Hyperlink hyperlink: slideObj.getHyperlinks()) {
            slide.getChildren().remove(hyperlink);
        }
        for(Image image: slideObj.getImages()) {
            slide.getChildren().remove(image);
        }
        slide.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
    }
    @FXML
    private void clearSlide()
    {
        clearNode(currentSlide);
        currentSlide.clear();
    }
    @FXML
    private void saveAsPresentation() throws IOException {
       Presentation.exportPresentation(slides);
    }
    @FXML
    private void getPresentationObjects() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Презентация (*.pptx)", "*.pptx"));
        fileChooser.setInitialFileName("Презентация");
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
           XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
           Presentation.getAllObjects(ppt);
        }
    }
    public void setBackgroundFill() {
            currentSlide.setBackground(pallette.getValue());
        slide.setBackground(new Background(new BackgroundFill(currentSlide.getBackground(), CornerRadii.EMPTY, Insets.EMPTY)));
    }
    @FXML
    private void recreatePresentation()
    {
        clearSlide();
        slides = new Slides(new ArrayList<>());
        currentSlide = new Slide();
        slides.addSlide(currentSlide);
        iterator = slides.getIterator();
        slideCurrentNum.setText(String.valueOf(iterator.getCurrent()+1));
    }
    @FXML
    private void showAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, """
                Данная программа предназначена для создания презентаций в формате pptx, а также для извлечения изображений из презентаций
                Управление:
                    Верхняя панель - инструменты для работы с файлами и слайдами
                    Левая панель - инструменты для работы с элементами, размещаемыми на слайдах
                ЛКМх2 - ввод текста
                ПКМ - контекстное меню
                Ctrl+ЛКМ по гиперссылке - гиперссылка становится доступна для перемещения
                ЛКМ(УДЕРЖ.) - перемещение объекта
                    Разработчик: Даниил Лобов
                """, ButtonType.OK);
        alert.setTitle("PowerPointParser");
        alert.setHeaderText("Руководство пользователя");
        alert.show();
    }
}