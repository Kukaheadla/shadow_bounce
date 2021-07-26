import bagel.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class CSVReader {

    /**
     * Construct a collection of pegs based on the CSV inputs
     * @param level - indicate which csv (level) files to read
     * @return - a collection of pegs
     */
    public static ArrayList<Peg> readPegs(int level){
        ArrayList<Peg> pegArr = new ArrayList<>();

        try (BufferedReader br =
                    new BufferedReader(new FileReader("res/"+ level +".csv"))) {
            String text;

            while ((text = br.readLine()) != null) {
                //split the commas for the required data
                String[] columns = text.split(",");
                boolean hasShape = (columns[0].split("_").length == 3);

                //establish points based on the relevant positional input
                Point p = new Point(Double.parseDouble(columns[1]), Double.parseDouble(columns[2]));

                //check peg is round or has other specific shape
                if(hasShape){
                    String color = columns[0].split("_")[0];
                    String shape = columns[0].split("_")[2];
                    //add the corresponding pegs and the given shape
                    if (color.equals("blue")){
                        Peg currPeg = new NormalPeg(p, shape+"-");
                        pegArr.add(currPeg);
                    } else {
                        Peg currPeg = new GreyPeg(p, shape+"-");
                        pegArr.add(currPeg);
                    }
                } else {
                    String color = columns[0].split("_")[0];
                    //add the corresponding pegs
                    if (color.equals("blue")){
                        Peg currPeg = new NormalPeg(p, "");
                        pegArr.add(currPeg);
                    } else {
                        Peg currPeg = new GreyPeg(p,"");
                        pegArr.add(currPeg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pegArr;
    }
}
