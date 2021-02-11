import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.shape.Line; 
import javafx.stage.Stage;  
import javafx.scene.text.*;
import javafx.scene.layout.StackPane; 
import javafx.scene.paint.Color;
import javafx.scene.layout.*; 
import javafx.scene.paint.*; 
import javafx.scene.layout.*; 
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.control.TextArea;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;  
import java.io.*;
import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class TextEditor extends Application{
    Clipboard clipboard;
    @Override 
    public void start(Stage primaryStage){
        primaryStage.setTitle("Text Editor");
        TextArea txtArea= new TextArea();
        txtArea.setPrefHeight(500);
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu AboutMenu = new Menu("About");
        
        //file menu items
        MenuItem new1 = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");
        //setting acceletators
        new1.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        //adding file menu items
        menu1.getItems().add(new1);
        menu1.getItems().add(open);
        menu1.getItems().add(save);
        menu1.getItems().add(exit);
        //edit menu items
        MenuItem undo = new MenuItem("Undo");
        MenuItem copy = new MenuItem("copy");
        MenuItem paste = new MenuItem("paste");
        MenuItem cut = new MenuItem("cut");
        MenuItem selectAll = new MenuItem("select all");
        MenuItem delete = new MenuItem("delete");

        //adding edit menu items
        editMenu.getItems().add(undo);
        editMenu.getItems().add(cut);
        editMenu.getItems().add(copy);
        editMenu.getItems().add(paste);
        editMenu.getItems().add(delete);
        editMenu.getItems().add(selectAll);
        //about menu items
          MenuItem about = new MenuItem("About NotePad");
        //adding an event handler on the about ment item
        about.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                 Alert alert = new Alert(Alert.AlertType.INFORMATION,"Simple Text Editor made by Mahmood Fathy");
                 alert.setTitle("About..");
                 alert.showAndWait();
            }
        }); 
        //adding an event handler on the open file
          open.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                openFile(primaryStage,txtArea);
            }
        }); 
        //adding event handler on the exit 
            exit.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                exit();
            }
        }); 
        //adding an event handler on the new file
         new1.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                newFile(txtArea);
            }
        }); 
        //adding an event handler on the save file
        save.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                saveFile(primaryStage,txtArea);
            }
        }); 
        //adding an event handler for copying text
         copy.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                copyText(txtArea);
            }
        }); 
        //adding an event handler for pasting text 
        paste.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                pasteText(txtArea);
            }
        }); 
        //adding event handler for selecting all text
        selectAll.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                selectAll(txtArea);
            }
        }); 
        //adding an event handler for cutting text
        cut.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                cutText(txtArea);
            }
        }); 
        //adding an event handler for undo
        undo.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                undoText(txtArea);
            }
        }); 
        //adding an event handler for delete
         delete.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                deleteText(txtArea);
            }
        }); 

        //adding About menu items
        AboutMenu.getItems().add(about);
        //adding menus
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(editMenu);
        menuBar.getMenus().add(AboutMenu);
        VBox root = new VBox();
        root.getChildren().addAll(menuBar,txtArea);
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void openFile(Stage stage,TextArea txt){
        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(stage);
        if (file!=null){
            FileInputStream fs;
            try{
                fs = new FileInputStream(file);
                int size =(int) file.length();
                byte [] data = new byte[size];
                fs.read(data);
                txt.setText(new String(data));
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    public void exit(){
        Platform.exit();
    }
    public void newFile(TextArea txt){
        txt.clear();
    }
    public void saveFile(Stage stage,TextArea txt){
         
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As");
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PrintWriter savedText = new PrintWriter(file);
                BufferedWriter out = new BufferedWriter(savedText);
                out.write(txt.getText());
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    public void copyText(TextArea txt){
        String copyText = txt.getSelectedText();
         clipboard = Clipboard.getSystemClipboard();
         ClipboardContent content = new ClipboardContent();
        content.putString(copyText);
        clipboard.setContent(content);
    }
    public void pasteText(TextArea txt){
        txt.paste();
    }
    public void cutText(TextArea txt){
        txt.cut();
    }
    public void selectAll(TextArea txt){
        txt.selectAll();
    }
    public void undoText (TextArea txt){
        txt.undo();
    }
    public void deleteText(TextArea txt){
        // String deleteText = (IndexRange)txt.getSelectedText();
        txt.replaceSelection("");
    }
    public static void main(String [] args){
        Application.launch(args);
    }
}