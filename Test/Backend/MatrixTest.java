package Backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    Matrix data= new Matrix();
    @Test
    void sortData() {
        data.setRowRepo("KEN");
        data.setColumnRepo("MATT");
        data.setResults((float) 0.20);
        data.setRowRepo("KEN");
        data.setColumnRepo("KURT");
        data.setResults((float) 0.45);
        data.setRowRepo("KEN");
        data.setColumnRepo("KRY");
        data.setResults((float) 0.12);
        data.setRowRepo("MATT");
        data.setColumnRepo("KURT");
        data.setResults((float) 0.72);
        data.setRowRepo("MATT");
        data.setColumnRepo("KRY");
        data.setResults((float) 0.70);
        data.setRowRepo("KURT");
        data.setColumnRepo("KRY");
        data.setResults((float) 0.70);
        data.sortData();
        for(int i=0;i<data.getResultsSize();i++)
        {
            for(int j=i+1; j<data.getResultsSize();j++)
            {
                assertTrue(data.getResults(i)>=data.getResults(j));
            }

        }


    }
}