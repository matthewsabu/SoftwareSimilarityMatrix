package Backend;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandling {

    private int fileFilter=0;
    private ArrayList<String> type= new ArrayList<>();
    private PythonScript script = new PythonScript();

    public void deleteFilesMerge(File mergeFile) {
        String[]entries = mergeFile.list();
        if (entries != null) {
            for(String s: entries){
                File currentFile = new File(mergeFile.getPath(),s);
                currentFile.delete();
            }
        }
    }

    public void gitFileRetriever() throws IOException
    {
        script.changeScript();
        File codes= new File("Codes");
        deleteCodes(codes);
        Process p = Runtime.getRuntime().exec("python RepoGrabber.py"); //python RepoGrabber.py
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        System.out.println("Completed:"+in.readLine());
        
    }

    public void deleteCodes(File codes)
    {
        String[]entries = codes.list();
        if (entries != null) {
            for(String s: entries){
                File currentFile = new File(codes.getPath(),s);
                if(currentFile.isDirectory()) deleteCodes(currentFile);
                currentFile.delete();
            }
        }
    }



    public void getAllFiles(File[] dir, Matrix data) throws IOException //get all files recursively
    {
        for (File files : dir) {
            if (files.isDirectory())// if the list found is a directory
            {
                File[] newDir = files.listFiles(); //gets the filename of the files inside the directory
                getFilesRecursively(newDir, files.getName(), data);// goes to the file method to recursively merge all of the codes
                fileFilter = 0;
            }
            else
            {
                if(filtering(files)) // checks whether the file is a .java or .ccp
                {
                    FileWriter fstream = new FileWriter("MergedCodes/"+files.getName(),true);
                    //To get the file names of the users that satisfied the filter option
                    data.addUser(files.getName());//add the names for the GUI

                    //writes the files into a text file for comparison
                    BufferedWriter writing = new BufferedWriter(fstream);
                    Scanner input = new Scanner(files);
                    while(input.hasNext())
                    {
                        writing.write(input.nextLine());
                        writing.newLine();
                    }
                    writing.close();
                }
            }
        }
    }

    private void getFilesRecursively(File[] dir,String fileName, Matrix data) throws IOException {
        for(File files: dir)
        {
            if(files.isDirectory())// if the list found is a directory
            {
                File[] newDir=files.listFiles(); //gets the filename of the files inside the directory
                getFilesRecursively(newDir,fileName,data);// going to the method that will check every file recursively
            }
            else
            {
                if(filtering(files)) // checks whether the file is a .java or .ccp
                {
                    FileWriter fstream = new FileWriter("MergedCodes/"+fileName+".txt",true);
                    //To get the file names of the users that satisfied the filter option
                    if(fileFilter ==0)
                    {
                        data.addUser(fileName);//add the names for the GUI
                        fileFilter =1;
                    }
                    //writes the files into a text file for comparison
                    BufferedWriter writing = new BufferedWriter(fstream);
                    Scanner input = new Scanner(files);
                    while(input.hasNext())
                    {
                        writing.write(input.nextLine());
                        writing.newLine();
                    }
                    writing.close();
                }

            }
        }
    }

    //Filters out the files that are not included in the checkbox
    private boolean filtering(File files)
    {
        for (String s : type) {
            if (files.getName().endsWith(s)) return true;
        }
        return false;
    }


    public void setFilter(ArrayList<String> type) {
        this.type=type;
    }


    public void writeScoreFile(Matrix matrix, String fileName) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("Logs/"+fileName+".txt",false));
        out.write("No.\tRepository 1\t\tRepoitory 2\t\tScores");
        out.newLine();
        for(int i=0; i<matrix.getResultsSize(); i++)
        {
            out.write((i+1)+"\t"+matrix.getRowRepo(i)+"\t\t"+matrix.getColumnRepo(i)+"\t\t"+matrix.getResults(i));
            out.newLine();
        }
        out.close();
    }
}
