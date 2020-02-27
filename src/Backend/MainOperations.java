package Backend;

import java.io.File;
import java.io.IOException;

public class MainOperations {


    public static void main(String[] args) throws IOException {

        SystemMetrics metrics = new SystemMetrics();
        boolean onlySourceFiles=true;   //or false for text files

        File masterFile = new File("src");  //for our files [Codes or src only]
        metrics.createSystemMetricsTable(masterFile,onlySourceFiles);  //check this for other's files

    }


}

