import java.io.File;

public class getFileList {

  public static void main(String[] args){
    String[] pathnames;
    File f = new File("userPortfolios/");

    // Populates the array with names of files and directories
    pathnames = f.list();

    // For each pathname in the pathnames array
    for (String pathname : pathnames) {

      // Print the names of files and directories
      System.out.println(pathname);
    }
  }
}
