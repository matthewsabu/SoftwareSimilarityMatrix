package Backend;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SimilarityTest {

    private Similarity similarity = new Similarity();

    @Test
    void ReadCodeLine() throws IOException {

        File prog1 = new File("src/Backend/Similarity.java");
        File prog2 = new File("src/Backend/Matrix.java");

        Scanner prog1Scanner = new Scanner(prog1);
        Scanner prog2Scanner = new Scanner(prog2);

        similarity.setCheckerScan(prog1Scanner);
        similarity.setComparisonScan(prog2Scanner);

        similarity.ReadCodeLine();

        for (int x = 0; x < similarity.getArrayA().size(); x++) {
            for (int y = 0; y < similarity.getArrayB().size(); y++) {
                if (similarity.getArrayA().get(x).getLine().equals(similarity.getArrayB().get(y).getLine())) {
                    assert (similarity.getArrayA().get(x).getLine().equals(similarity.getArrayB().get(y).getLine()));
                } else
                    assertFalse(similarity.getArrayA().get(x).getLine().equals(similarity.getArrayB().get(y).getLine()));
            }
        }
    }


    @Test
    void ReadCodeString() throws FileNotFoundException {
        File prog1 = new File("src/Backend/Similarity.java");
        File prog2 = new File("src/Backend/Matrix.java");

        Scanner prog1Scanner = new Scanner(prog1);
        Scanner prog2Scanner = new Scanner(prog2);


        similarity.setCheckerScan(prog1Scanner);
        similarity.setComparisonScan(prog2Scanner);

        similarity.ReadCodeString();

        for (int i = 0; i < similarity.getFirstCode().size(); i++) {
            assertEquals(similarity.getFirstCode().get(i), similarity.getSecondCode().get(i));
        }
    }
}