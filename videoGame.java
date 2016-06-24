import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

/* Each table you wish to access in your database requires a model class, like this example: */
public class videoGame
{
    /* First, map each of the fields (columns) in your table to some public variables. */
    public int id;
    public String name;
    public int genreId;
    public int developerId;
    public String description;

    /* Next, prepare a constructor that takes each of the fields as arguements. */
    public videoGame(int id, String name, int genreId, int developerId, String description)
    {
        this.id = id;
        this.name = name;
        this.genreId = genreId;
        this.developerId = developerId;
        this.description = description;
    }

    /* A toString method is vital so that your model items can be sensibly displayed as text. */
    @Override public String toString()
    {
        return name;
    }

    /* Different models will require different read and write methods. Here is an example 'loadAll' method 
     * which is passed the target list object to populate. */
    public static void readAll(List<videoGame> list)
    {
        list.clear();       // Clear the target list first.

        /* Create a new prepared statement object with the desired SQL query. */
        PreparedStatement statement = Application.database.newStatement("SELECT id, name, FROM VideoGames"); 

        if (statement != null)      // Assuming the statement correctly initated...
        {
            ResultSet results = Application.database.runQuery(statement);       // ...run the query!

            if (results != null)        // If some results are returned from the query...
            {
                try {                               // ...add each one to the list.
                    while (results.next()) {                                               
                        list.add( new videoGame(results.getInt( "id"),
                                results.getString("name"),
                                results.getInt("genreId"),
                                results.getInt("developerId"),
                                results.getString("description")));
                    }
                }
                catch (SQLException resultsexception)       // Catch any error processing the results.
                {
                    System.out.println("Database result processing error: " + resultsexception.getMessage());
                }
            }
        }

    }

}
