package Backend;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Similarity {
    private float  percentage = 0;
    private Scanner checkerScan, comparisonScan;
    private Matrix data = new Matrix();
    private int fileFilter =0;
    private String[] filter= new String[2];
    private String similarString = "";

    private void ReadCodeLine() {
        float sameLines=0,totalLines=0,longestLength=0;

        ArrayList<String> arrayA = new ArrayList<>();
        ArrayList<String> arrayB = new ArrayList<>();

        while(checkerScan.hasNextLine() || comparisonScan.hasNextLine()) {

            if(!checkerScan.hasNextLine() && comparisonScan.hasNextLine()) {
                String line2 = comparisonScan.nextLine();

                if(!line2.trim().isEmpty()) {
                    arrayB.add(line2);
                    totalLines++;
                }
            }
            else if (checkerScan.hasNextLine() && !comparisonScan.hasNextLine()) {
                String line1 = checkerScan.nextLine();

                if(!line1.trim().isEmpty()) {
                    arrayA.add(line1);
                    totalLines++;
                }
            }
            else {
                String line1 = checkerScan.nextLine();
                String line2 = comparisonScan.nextLine();

                if(!line1.trim().isEmpty()) arrayA.add(line1);
                if(!line2.trim().isEmpty()) arrayB.add(line2);
                //if(!line1.trim().isEmpty() && !line2.trim().isEmpty()) totalLines++;
            }
        }

        if(arrayA.size() > arrayB.size()) totalLines = arrayA.size();
        else totalLines = arrayB.size();

//        totalLines = arrayA.size() + arrayB.size();

//        System.out.println("\nNUMBER OF PROG1 LINES: " + arrayA.size());
//        System.out.println("NUMBER OF PROG2 LINES: " + arrayB.size() + "\n");


        for(int x = 0; x< arrayA.size(); x++){  //arrayA.size()
//            System.out.println("PROG 1 LINE: "+arrayA.get(x));
            boolean compared = false;
            for(int y = 0; y< arrayB.size(); y++){  //arrayB.size()
//                System.out.println("PROG 2 LINE: "+arrayB.get(y));
                if(arrayA.get(x).equals(arrayB.get(y))){
                    int currentLength = arrayA.get(x).length();
                    if(currentLength > longestLength) similarString = arrayA.get(x);

                    if(!compared) {
//                        System.out.println("PROG 1 LINE #"+(x+1)+": "+ arrayA.get(x));
//                        System.out.println("PROG 2 LINE #"+(y+1)+": "+ arrayB.get(y));
                        sameLines++;
//                        System.out.println(sameLines);
                    }
                    compared = true;
                }
            }
        }

        checkerScan.close();
        comparisonScan.close();
        System.out.println("\nNUMBER OF SAME LINES: " + sameLines);
        System.out.println("NUMBER OF TOTAL LINES: " + totalLines + "\n");
        percentage = (sameLines / totalLines);
        //percentage = (float)((sameLines/totalLines)-0.5) * 2; //testing for negatives
    }

    public String getSimilarString(){
        return similarString;
    }

    private void ReadCodeString() {
        int countString = 0;
        int countTotal;
        int j=0;
        boolean compare;
        ArrayList<String> firstCode= new ArrayList<>();
        ArrayList<String> secondCode= new ArrayList<>();
        getAllStrings(checkerScan,firstCode);
        getAllStrings(comparisonScan,secondCode);

        if(firstCode.size()>=secondCode.size()) countTotal=firstCode.size();
        else countTotal=secondCode.size();

        while(j<firstCode.size())
        {
            compare=false;
            for(int i=0; i<secondCode.size(); i++)
            {
                if(firstCode.get(j).equals(secondCode.get(i)))
                {
                    countString++;
                    firstCode.remove(j);
                    secondCode.remove(i);
                    compare=true;
                    break;
                }
            }
            if(!compare)
            {
                j++;
            }
        }

        checkerScan.close();
        comparisonScan.close();
        percentage = ((float) countString / (float) countTotal);
    }


    private void getAllStrings(Scanner scan, ArrayList<String> code)
    {
        while(scan.hasNext())
        {
            String[] line= scan.nextLine().split(" ");
            for(String s :line)
            {
                if(!s.equals(""))
                {
                    code.add(s);
                }
            }
        }
    }


    //creating the correlation Matrix
    public void creationMatrix(String comparison,String type) throws IOException {
        File checkerFile, comparisonFile; //storing for files when comparing
        File prog1File = new File("Codes");
        File[] dir = prog1File.listFiles(); // getting all file in the Codes directory
        data.newMatrix(); // creating a new correlation matrix
        data.newUsers(); // clearing all of the data in the userArrayList

        //Delete the files inside the mergedCodes directory to avoid duplication
        File mergeFile= new File("MergedCodes");
        deleteFilesMerge(mergeFile);

        if(type.equals("java"))
        {
            filter[0]=".java";
            filter[1]=".java";
        }
        else if(type.equals("cpp"))
        {
            filter[0]=".cpp";
            filter[1]=".cpp";
        }
        else
        {
            filter[0]=".java";
            filter[1]=".cpp";
        }

        getAllFiles(dir);// starts to recursively check all of the codes in the folder

        //gets all of the merged files
        File[] codes= mergeFile.listFiles();

        //comparing files
        for(int i=0; i<codes.length; i++)
        {
            data.setNewArray();// creating a new array for the checker file
            checkerFile=codes[i];
            for(int j=0; j<codes.length;j++)
            {
                comparisonFile=codes[j];
                checkerScan = new Scanner(checkerFile);
                comparisonScan = new Scanner(comparisonFile);
                //checks whether the user chose the to compare by line or by character
                if(comparison.equals("line")) ReadCodeLine();
                else ReadCodeString();
                data.addArray((float)(Math.round(percentage*100.0)/100.0));  //two decimal points
            }
            data.setMatrix();//saving the data gathered to another array list for the correlation matrix
        }

    }

    private void deleteFilesMerge(File mergeFile) {
        String[]entries = mergeFile.list();
        if (entries != null) {
            for(String s: entries){
                File currentFile = new File(mergeFile.getPath(),s);
                currentFile.delete();
            }
        }
    }

    private void getAllFiles(File[] dir) throws IOException //get all files recursively
    {
        for (File files : dir) {
            if (files.isDirectory())// if the list found is a directory
            {
                File[] newDir = files.listFiles(); //gets the filename of the files inside the directory
                getFilesRecursively(newDir, files.getName());// goes to the file method to recursively merge all of the codes
                fileFilter = 0;
            }
        }
    }

    private void getFilesRecursively(File[] dir,String fileName) throws IOException {
        for(File files: dir)
        {
            if(files.isDirectory())// if the list found is a directory
            {
                File[] newDir=files.listFiles(); //gets the filename of the files inside the directory
                getFilesRecursively(newDir,fileName);// going to the method that will check every file recursively
            }
            else
            {
                if(files.getName().contains(filter[0]) || files.getName().contains(filter[1])) // checks whether the file is a .java or .ccp
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



    public Matrix getMatrix(){
        return data;
    }


}
