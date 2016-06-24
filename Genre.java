import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

/* Each table you wish to access in your database requires a model class, like this example: */
public class Genre
{
    /* First, map each of the fields (columns) in your table to some public variables. */
    public int id;
    public String name;

    /* Next, prepare a constructor that takes each of the fields as arguements. */
    public Genre(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /* A toString method is vital so that your model items can be sensibly displayed as text. */
    @Override public String toString()
    {
        return name;
    }
  
    /* Different models will require different read and write methods. Here is an example 'loadAll' method 
     * which is passed the target list object to populate. */
    public static void readAll(List<Genre> list)
    {
        list.clear();       // Clear the target list first.

        /* Create a new prepared statement object with the desired SQL query. */
        PreparedStatement statement = Application.database.newStatement("SELECT genreId, name FROM Genre ORDER BY genreId"); 

        if (statement != null)      // Assuming the statement correctly initated...
        {
            ResultSet results = Application.database.runQuery(statement);       // ...run the query!

            if (results != null)        // If some results are returned from the query...
            {
                try {								// ...add each one to the list.
                    while (results.next()) {        			                           
                        list.add( new Genre(results.getInt("genreId"), results.getString("name")));
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