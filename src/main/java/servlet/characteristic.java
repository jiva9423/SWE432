package servlet;

/**
 * characteristic data object
 * To support the Input Space Partitioning test criterion
 * A characteristic has two parts:
 *   a name (String)
 *   number of blocks (int)
 * @author Jeff Offutt
 * @date January 2022
 */

public class characteristic
{
   private String name;
   private int numBlocks;

// Default constructor has 2 blocks and is named "A"
public characteristic()
{
   name = "A";
   numBlocks = 2;
}

// Constructor that takes a num and number of blocks
public characteristic(String name, int blocks)
{
   this.name = name;
   this.numBlocks = blocks;
}

// Getter and setter for name
public String getName ()
{
   return this.name;
}
public void setName (String name)
{
   this.name = name;
}

// Getter and setter for number of blocks
public int getNumBlocks ()
{
   return numBlocks;
}
public void setNumBlocks (int numBlocks)
{
   this.numBlocks = numBlocks;
}

public String toString()
{
   return ("[" + name + ", " + numBlocks + "]");
}

public String getBlocks()
{
   String returnVal = "[ ";
   for (int j=0; j<numBlocks; j++)
   {
      returnVal += name + (j+1);
      if (j<numBlocks-1)
         returnVal += ",";
      returnVal += " ";
   }
   returnVal += "]";
   return returnVal;
}

} // end class characteristic
