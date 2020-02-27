package Backend;

import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Similarity {
    private float  percentage=0,longestLength=0;
    private Scanner checkerScan, comparisonScan;
    private Matrix data = new Matrix();
    private String similarString = "";
    private FileHandling files= new FileHandling();
    private ArrayList<ProgramLine> arrayA,arrayB;
    private ArrayList<String> firstSameCode= new ArrayList<>();
    private ArrayList<String> secondSameCode= new ArrayList<>();

    public void ReadCodeLine() {
        int sameLines=0,totalLines=0;
        ProgramLine progLine;

        arrayA = new ArrayList<>();
        arrayB = new ArrayList<>();

        while(checkerScan.hasNextLine() || comparisonScan.hasNextLine()) {

            if(!checkerScan.hasNextLine() && comparisonScan.hasNextLine()) {
                String line2 = comparisonScan.nextLine();

                if(!line2.trim().isEmpty()) {
                    progLine = new ProgramLine(line2,false);
                    arrayB.add(progLine);
                    totalLines++;
                }
            }
            else if (checkerScan.hasNextLine() && !comparisonScan.hasNextLine()) {
                String line1 = checkerScan.nextLine();

                if(!line1.trim().isEmpty()) {
                    progLine = new ProgramLine(line1,false);
                    arrayA.add(progLine);
                    totalLines++;
                }
            }
            else {
                String line1 = checkerScan.nextLine();
                String line2 = comparisonScan.nextLine();

                if(!line1.trim().isEmpty()) {
                    progLine = new ProgramLine(line1,false);
                    arrayA.add(progLine);
                }
                if(!line2.trim().isEmpty()) {
                    progLine = new ProgramLine(line2,false);
                    arrayB.add(progLine);
                }
            }
        }

        if(arrayA.size() > arrayB.size()) totalLines = arrayA.size();
        else totalLines = arrayB.size();


        for(int x = 0; x< arrayA.size(); x++){  //arrayA.size()
            for(int y = 0; y< arrayB.size(); y++){  //arrayB.size()
                if(arrayA.get(x).getLine().equals(arrayB.get(y).getLine())){
                    int currentLength = arrayA.get(x).getLine().length();
                    if(currentLength > longestLength) {
                        similarString = arrayA.get(x).getLine();
                        longestLength = currentLength;
                    }
                    if(!arrayB.get(y).isCompared() && !arrayA.get(x).isCompared()) {
                        arrayB.get(y).setCompared(true);
                        arrayA.get(x).setCompared(true);
                        sameLines++;
                    }
                }
            }
        }

        checkerScan.close();
        comparisonScan.close();
        percentage = ((float)sameLines / (float)totalLines);
    }

    public ArrayList<ProgramLine> getArrayA(){
        return arrayA;
    }

    public ArrayList<ProgramLine> getArrayB(){
        return arrayB;
    }

    public String longestString(){
        return similarString;
    }

    public void ReadCodeString() {
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
                    firstSameCode.add(firstCode.get(j));
                    secondSameCode.add(secondCode.get(i));
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

    public ArrayList<String> getFirstCode(){
        return firstSameCode;
    }

    public ArrayList<String> getSecondCode(){
        return secondSameCode;
    }

    private void getAllStrings(Scanner scan, ArrayList<String> code)
    {
        while(scan.hasNext())
        {
            String[] line= scan.nextLine().split("^[a-zA-Z]+$");
            for(String s :line)
            {
                if(!s.equals("")) {
                    code.add(s);
                    if (s.length() >= similarString.length()) similarString = s;
                }
            }
        }
    }

    public void setCheckerScan(Scanner sc){
        this.checkerScan=sc;
    }

    public void setComparisonScan(Scanner cs){
        this.comparisonScan=cs;
    }


    //creating the correlation Matrix
    public void creationMatrix(String comparison,ArrayList<String> type,String filePath) throws IOException, NullPointerException {
        similarString=" ";

        File checkerFile, comparisonFile; //storing for files when comparing
        File prog1File;
        if(filePath.equals("")) {
            prog1File = new File("Codes");
        } else {
            filePath=filePath.replaceAll("\\\\", "\\\\\\\\");
            prog1File = new File(filePath);
        }
        File[] dir = prog1File.listFiles(); // getting all file in the Codes directory
        data.newMatrix(); // creating a new correlation matrix
        data.newUsers(); // clearing all of the data in the userArrayList
        data.newRepo();

        //Delete the files inside the mergedCodes directory to avoid duplication
        File mergeFile= new File("MergedCodes");
        files.deleteFilesMerge(mergeFile);

        files.setFilter(type);
        files.getAllFiles(dir,data);// starts to recursively check all of the codes in the folder

        //gets all of the merged files
        File[] codes= mergeFile.listFiles();

        //comparing files
        for(int i=0; i<codes.length; i++)   //codes.length
        {
            data.setNewArray();// creating a new array for the checker file
            checkerFile=codes[i];
            for(int j=0; j<codes.length;j++)   //codes.length
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
        generateScores();
    }

    public void generateScores()
    {
        for(int i=0; i<data.matrixSize(); i++)
        {
            for(int j=i+1; j<data.getMatrix().get(i).size(); j++)
            {
                data.setRowRepo(data.getUserFileNames().get(i));
                data.setColumnRepo(data.getUserFileNames().get(j));
                data.setResults(data.getMatrix().get(i).get(j));
            }
        }
        data.sortData();

        for(int i=0; i<data.matrixSize(); i++) {
            System.out.println();
            System.out.print(data.getRowRepo(i) + " " + data.getColumnRepo(i) + " " + data.getResults(i));
        }

    }

    public Matrix getMatrix(){
        return data;
    }



}
