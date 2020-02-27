package Backend;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlingTest {

    FileHandling testFiles= new FileHandling();
    @Test
    void deleteCodes() {
        File path= new File("TestDeleteFolder");
        testFiles.deleteCodes(path);
        File []list=path.listFiles();
        assertEquals(list.length,0);


    }

    @Test
    void getAllFiles() throws IOException {
        File path= new File("TestGetAllFolder");
        ArrayList<String> filter= new ArrayList<>();
        filter.add(".txt");
        testFiles.setFilter(filter);
        File [] list = path.listFiles();
        Matrix data = new Matrix();
        testFiles.getAllFiles(list,data);
        assertEquals(data.getUserFileNames().size(),2);
    }
}