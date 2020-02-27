package Backend;

import java.util.ArrayList;

public class Matrix {
    private ArrayList<ArrayList<Float>> matrix = new ArrayList<>();
    private ArrayList<Float> arrayTemp;
    private ArrayList<String> userFilesArray = new ArrayList<>();
    private ArrayList<String> rowRepo = new ArrayList<>();
    private ArrayList<String> columnRepo = new ArrayList<>();
    private ArrayList<Float> result = new ArrayList<>();

    //FOR USER FILE NAMES ARRAY
    public void newUsers()
    {
        userFilesArray= new ArrayList<>();
    }

    public ArrayList<String> getUserFileNames() {
        return userFilesArray;
    }

    public void addUser(String username) {
        userFilesArray.add(username);
    }



    //FOR MASTER MATRIX
    public void newMatrix()
    {
        matrix= new ArrayList<>();
    }

    public ArrayList<ArrayList<Float>> getMatrix() {
        return matrix;
    }

    public void setMatrix() {
        matrix.add(arrayTemp);
    }

    public int matrixSize(){
        return matrix.size();
    }


    //FOR TEMP (INNER) ARRAY
    public void setNewArray() {
        arrayTemp= new ArrayList<>();
    }

    public void addArray(float value) {
        arrayTemp.add(value);
    }

    public int arraySize(){
        return arrayTemp.size();
    }

    //for the storing and retrieving of scores data
    public void newRepo()
    {
        rowRepo = new ArrayList<>();
        columnRepo = new ArrayList<>();
        result = new ArrayList<>();
    }

    public String getRowRepo(int i) {
        return rowRepo.get(i);
    }

    public void setRowRepo(String rowRepo) {
        this.rowRepo.add(rowRepo);
    }

    public String getColumnRepo(int i) {
        return columnRepo.get(i);
    }

    public void setColumnRepo(String columnRepo) {
        this.columnRepo.add(columnRepo);
    }

    public Float getResults(int i) {
        return result.get(i);
    }

    public void setResults(Float result) {
        this.result.add(result);
    }

    public int getResultsSize()
    {
        return result.size();
    }


    public void sortData()
    {
        int low=0;
        int high=result.size()-1;
        quickSort(rowRepo,columnRepo,result,low, high);
    }

    private void quickSort(ArrayList<String> row, ArrayList<String> col, ArrayList<Float> result, int low, int high) {
        if(low<high)
        {
            int pi= partition(row, col, result, low, high);
            quickSort(row, col, result, low, pi-1);
            quickSort(row, col, result, pi+1, high);
        }
    }

    private int partition(ArrayList<String> row, ArrayList<String> col, ArrayList<Float> result, int low, int high) {
        float pivot = result.get(high);
        int i= low-1;
        for(int j=low; j<high; j++)
        {
            if(result.get(j)>pivot)
            {
                i++;
                float resultTemp=result.get(i);
                String rowTemp=row.get(i);
                String colTemp=col.get(i);
                result.set(i,result.get(j));
                row.set(i,row.get(j));
                col.set(i,col.get(j));
                result.set(j,resultTemp);
                row.set(j,rowTemp);
                col.set(j,colTemp);
            }
        }
        float resultTemp=result.get(i+1);
        String rowTemp=row.get(i+1);
        String colTemp=col.get(i+1);
        result.set(i+1,result.get(high));
        row.set(i+1,row.get(high));
        col.set(i+1,col.get(high));
        result.set(high,resultTemp);
        row.set(high,rowTemp);
        col.set(high,colTemp);
        return i+1;
    }


}
