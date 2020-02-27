package Backend;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class DataEntry {

    private SimpleStringProperty progName1,progName2,percentage;
//    private double percentage;


    public DataEntry(String name1,String name2,String score){
        this.progName1= new SimpleStringProperty(name1);
        this.progName2=new SimpleStringProperty(name2);
        this.percentage=new SimpleStringProperty(score);
    }

    public String getN1(){
        return progName1.get();
    }

    public String getN2(){
        return progName2.get();
    }

    public String getP(){
        return percentage.get();
    }

    public void setN1(String n){
        progName1.set(n);
    }

    public void setN2(String n){
        progName2.set(n);
    }

    public void setP(String n){
        percentage.set(n);
    }
}
