import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import java.util.List;


public class EditController
{
    
    private Stage stage;
    private SceneController parent;
    
     @FXML   private Button saveButton;
     @FXML   private TextField nameField;
     @FXML   private ChoiceBox genreField;
     @FXML   private ChoiceBox developerField;
     @FXML   private TextField descriptionField;
     
     private videogame videogame;
    
    public void EditController()
    {
        System.out.println("Initialising controllers...");
    }
    
    public void prepareStageEvents(Stage stage)
    {
        System.out.println("Preparing stage events....");
        
        this.stage = stage;
        
        stage.setOnCloseRequest(new EventHandlet<WindowEvent>() {
               public void handle(WindowEvent we){
                   System.out.println("Close button was clicked!");
                }
            });
    }
    
    @FXML void initialize()
    {
        System.out.println("Asserting controls...");
        try
        {
            assert saveButton != null : "Can't find saveButton";
            assert nameField != null : "Can't find nameField";
            assert genreField != null : "Can't find genreField";
            assert developerField != null : "Can't find developerField";
            assert descriptionField != null : "Can't find descriptionField";
        }
        catch (AssertionError ae)
        {
            System.out.println("FXML assertion failure: " + ae.getMessage());
            Application.terminate();
        }
        
        System.out.println("Populating scene with items from the database...");        
        @SuppressWarnings("unchecked")
        List<Genre> targetList = genreField.getItems();  // Grab a reference to the listView's current item list.
        genre.readAll(targetList);       
        genreField.getSelectionModel().selectFirst();
        
        List<Developer> targetList = developerField.getItems();  // Grab a reference to the listView's current item list.
        Developer.readAll(targetList);       
        developerField.getSelectionModel().selectFirst();
        
    }
    
    public void setParent(SceneController parent)
    {
        this.parent = parent;
    }
    
    public void loadItem(int id)
    {
        videogame = videogame.getById(id);
        nameField.setText(video.name);
        
        List<Genre> targetList = genreField.getItems();
        
        for(Genre g : targetList)
        {
            if (g.id == videogame.genreId)
            {
                genreField.getSelectionModel().select(g);
            }
        }
        
         List<Developer> targetList = developerField.getItems();
        
        for(Developer d : targetList)
        {
            if (d.id == videogame.developerId)
            {
                developerField.getSelectionModel().select(d);
            }
        }
    }
    
    @FXML void saveButtonClicked()
    {
        System.out.println("Save button clicked!");
        
        if (videogame == null)
        {
            videogame = new videogame(0, "", 0);
        }
        
        videogame.name = nameField.gettext();
        
        Genre selectedGenre = (Genre) genreField.getSelectionModel().getSelectedItem();
        videogame.genreId = delectedgenre.id;
        
        Developer selectedDeveloper = (Developer) developerField.getSelectionModel().getSelectedItem();
        videogame.developerId = delecteddeveloper.id;
        
        videogame.save();
    }
    
    
    
    
}
