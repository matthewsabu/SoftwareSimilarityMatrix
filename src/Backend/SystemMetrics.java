package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SystemMetrics {
    private File[] folders;
    private int numOfFiles=0;
    private String[] filesArray = new String[100];
    private String[] operators = {".","( )","if","for","=","+","while","!",";","==","<=","<","float","int",">",
    "++","new","&&","||","/","{ }","return"};
    private String[] operands = {"x","y","i","j"};
    private int dots,par,ifs,fors,equal,plus,whiles,not,colon,equals,lessEqual,less,
            floats,ints,greater,pluses,news,ands,ors,fslash,curly,returns;
    private int xs,ys,is,js;
    private int sum_operators,sum_operands,count_operators,count_operands;
    //private int[] operandsCount = new int[10];

    public void getAllFiles(File directory,boolean onlySourceFiles) throws IOException {
        folders = directory.listFiles();
        for(File file : folders){  //folders.length
            if(file.isDirectory()){  //folders[x]
                System.out.println("\nFOLDER: "+file.getCanonicalPath());
                getAllFiles(file,onlySourceFiles);
            } else {
                System.out.println("FILE: "+file.getCanonicalPath());
                if(onlySourceFiles){
                    if(file.getCanonicalPath().endsWith(".java")){
                        filesArray[numOfFiles++] = file.getCanonicalPath();
                    }
                }
            }
        }
        //System.out.println(numOfFiles);
    }

    public void searchOperationsAndOperands() throws FileNotFoundException {
        String word = "";
        boolean isComment = false;

        for(int i=0; i<numOfFiles; i++) {  //numOfFiles [change numOfFiles to 1 for the first file only)
            File file = new File(filesArray[i]);
            Scanner fileScanner = new Scanner(file);
            //System.out.println(filesArray[i]);

            while(fileScanner.hasNext()){
                word = fileScanner.nextLine();
                System.out.println(word);

                for(int x=0;x<word.length();x++){
                    //OPERATORS
                    if(word.charAt(x) == '/'){
                        if(x==word.length()-1) continue;
                        if(word.charAt(x+1) == '/' || word.charAt(x+1) == '*') isComment = true;
                        if(word.charAt(x+1) != '/' || word.charAt(x+1) != '*'){
                            if(x==0) continue;
                            if((word.charAt(x-1) != '/'  || word.charAt(x-1) != '*') && !isComment){
                                System.out.println("SLASH: " + word.charAt(x));
                                fslash++;
                            }
                        }
                    } else {
                        if(!isComment) {
                            if (word.charAt(x) == '.') dots++;
                            if (word.charAt(x) == '(') par++;
                            if (word.charAt(x) == 'i') {
                                if (x == word.length() - 1) continue;
                                if (word.charAt(x + 1) == 'f') ifs++;
                            }
                            if (word.charAt(x) == 'f' && word.charAt(x + 1) == 'o' && word.charAt(x + 2) == 'r') {
                                if (x == word.length() - 1) continue;
                                fors++;
                            }
                            if (word.charAt(x) == '=') equal++;
                            if (word.charAt(x) == '+') plus++;
                            if (word.charAt(x) == 'w') {
                                if (x == word.length() - 1) continue;
                                if (word.charAt(x + 1) == 'h' && word.charAt(x + 2) == 'i' && word.charAt(x + 3) == 'l' && word.charAt(x + 4) == 'e')
                                    whiles++;
                            }
                            if (word.charAt(x) == '!') not++;
                            if (word.charAt(x) == ';') colon++;
                            if (word.charAt(x) == '=') {
                                if (x == word.length() - 1) continue;
                                if (word.charAt(x + 1) == '=') equals++;
                            }
                            if (word.charAt(x) == '<') {
                                if (x == word.length() - 1) continue;
                                if (word.charAt(x + 1) == '=') lessEqual++;
                            }
                            if (word.charAt(x) == '<') less++;
                            if (word.charAt(x) == 'f') {
                                if (x == word.length() - 1) continue;
                                if (word.charAt(x + 1) == 'l' && word.charAt(x + 2) == 'o' && word.charAt(x + 3) == 'a' && word.charAt(x + 4) == 't')
                                    floats++;
                            }
                            if (word.charAt(x) == 'i') {
                                if (x == word.length() - 1) continue;
                                if (x + 1 == word.length() - 1) continue;
                                if (word.charAt(x + 1) == 'n' && word.charAt(x + 2) == 't') ints++;
                            }
                            if (word.charAt(x) == '>') greater++;
                            if (word.charAt(x) == '+'){
                                if (x == word.length() - 1) continue;
                                if(word.charAt(x + 1) == '+') pluses++;
                            }
                            if (word.charAt(x) == 'n') {
                                if (x == word.length() - 1) continue;
                                if (x + 1 == word.length() - 1) continue;
                                if (word.charAt(x + 1) == 'e' && word.charAt(x + 2) == 'w') news++;
                            }
                            if (word.charAt(x) == '&') {
                                if (x == word.length() - 1) continue;
                                if (word.charAt(x + 1) == '&') ands++;
                            }
                            if (word.charAt(x) == '|') {
                                if (x == word.length() - 1) continue;
                                if (word.charAt(x + 1) == '|') ors++;
                            }
                            if (word.charAt(x) == '{') curly++;
                            if (word.charAt(x) == 'r') {
                                if (x == word.length() - 1) continue;
                                if (word.charAt(x+1) == 'e'){
                                    if (x+1 == word.length() - 1) continue;
                                    if(word.charAt(x+2) == 't') {
                                        if (x == word.length() - 1) continue;
                                        if(word.charAt(x+3) == 'u' && word.charAt(x+4) == 'r' && word.charAt(x+5) == 'n')
                                            returns++;
                                    }
                                }
                            }
                            //OPERATORS
                    /*if(word.charAt(x) == 'x') xs++;    //considers all x,y,i,j
                    if(word.charAt(x) == 'y') ys++;
                    if(word.charAt(x) == 'i') xs++;
                    if(word.charAt(x) == 'j') ys++;*/

                            //considers only x,y,i,j used in for loops or basta as variables lang
                            if (word.charAt(x) == 'x') {
                                if (x == word.length() - 1) continue;
                                if ((word.charAt(x + 1) == '<' || word.charAt(x + 1) == '=' || word.charAt(x + 1) == '+' || word.charAt(x + 1) == ')' || word.charAt(x + 1) == ']')) {
                                    xs++;
                                }
                            }
                            if (word.charAt(x) == 'y') {
                                if (x == word.length() - 1) continue;
                                if ((word.charAt(x + 1) == '<' || word.charAt(x + 1) == '=' || word.charAt(x + 1) == '+' || word.charAt(x + 1) == ')' || word.charAt(x + 1) == ']')) {
                                    ys++;
                                }
                            }
                            if (word.charAt(x) == 'i') {
                                if (x == word.length() - 1) continue;
                                if ((word.charAt(x + 1) == '<' || word.charAt(x + 1) == '=' || word.charAt(x + 1) == '+' || word.charAt(x + 1) == ')' || word.charAt(x + 1) == ']')) {
                                    is++;
                                }
                            }
                            if (word.charAt(x) == 'j') {
                                if (x == word.length() - 1) continue;
                                if ((word.charAt(x + 1) == '<' || word.charAt(x + 1) == '=' || word.charAt(x + 1) == '+' || word.charAt(x + 1) == ')' || word.charAt(x + 1) == ']')) {
                                    js++;
                                }
                            }
                        }
                    }
                }
                isComment = false;
            }
        }


        sum_operators = dots+par+ifs+fors+equal+plus+whiles+not+colon+equals+lessEqual+less+floats+ints+greater+pluses+news+ands+ors+fslash+curly;
        sum_operands = xs+ys+is+js; //N2

        count_operators = operators.length; //n1
        count_operands = operands.length;   //n2

    }


    public void printMetrics(){

        System.out.print("\n-- OPERATORS --");              System.out.println("  -- OPERANDS --");
        System.out.print("\t" + operators[0] + " -> " + dots);     System.out.println("\t\t" + operands[0] + " -> " + xs);
        System.out.print("\t" + operators[1] + " -> " + par);      System.out.println("\t\t" + operands[1] + " -> " + ys);
        System.out.print("\t" + operators[2] + " -> " + ifs);      System.out.println("\t\t" + operands[2] + " -> " + is);
        System.out.print("\t" + operators[3] + " -> " + fors);     System.out.println("\t\t" + operands[3] + " -> " + js);
        System.out.println("\t" + operators[4] + " -> " + equal);
        System.out.println("\t" + operators[5] + " -> " + plus);
        System.out.println("\t" + operators[6] + " -> " + whiles);
        System.out.println("\t" + operators[7] + " -> " + not);
        System.out.println("\t" + operators[8] + " -> " + colon);
        System.out.println("\t" + operators[9] + " -> " + equals);
        System.out.println("\t" + operators[10] + " -> " + lessEqual);
        System.out.println("\t" + operators[11] + " -> " + less);
        System.out.println("\t" + operators[12] + " -> " + floats);
        System.out.println("\t" + operators[13] + " -> " + ints);
        System.out.println("\t" + operators[14] + " -> " + greater);
        System.out.println("\t" + operators[15] + " -> " + pluses);
        System.out.println("\t" + operators[16] + " -> " + news);
        System.out.println("\t" + operators[17] + " -> " + ands);
        System.out.println("\t" + operators[18] + " -> " + ors);
        System.out.println("\t" + operators[19] + " -> " + fslash);
        System.out.println("\t" + operators[20] + " -> " + curly);
        System.out.println("\t" + operators[21] + " -> " + returns);
        System.out.print("\tSUM -> " + sum_operators);       System.out.println("\t\tSUM -> " + sum_operands);

        //METRICS OMSIM
        System.out.print("\nLength -> " + length());
        System.out.print("\nVocabulary -> " + vocab());
        System.out.print("\nVolume -> " + volume());
        System.out.print("\nDifficulty -> " + difficulty());
        System.out.print("\nEffort -> " + effort());
        System.out.print("\nTime -> " + time());
        System.out.print("\nIntelligence Content -> " + intelligence());

    }

    public int volume(){
        return length() * (int)(Math.log(vocab())/Math.log(2)); //N*log base 2(n)
    }

    public int length() {
        return sum_operators + sum_operands;    //formula: N1+N2    THIS IS N
    }

    public int vocab() {
        return count_operators + count_operands;    //formula: n1+n2    THIS IS n
    }

    public int difficulty(){
        return (count_operators/2)*(sum_operands/count_operands);   //formula: (n1/2)*(N2/n2)
    }

    public int effort(){
        return difficulty() * volume(); //formula: D*V
    }

    public int intelligence(){
        return volume()/difficulty();   //formula: V/D
    }

    public int time(){
        return effort()/(60*18);    // formula: E/(f*S) where f=60mins and S=18 halst. num
    }

    public void createSystemMetricsTable(File directory,boolean onlySourceFiles) throws IOException {

        getAllFiles(directory,onlySourceFiles);

        searchOperationsAndOperands();

        printMetrics();

    }

}
