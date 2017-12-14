package expertsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExpertSystem {
    /*
    data[x][
         0 - x,
         1 - y,
         2 - y hat,
         3 - e,
         4 - e^2
         ]
    */
    static int n = 250;
    static float[][] data = new float[n][5];
    static float yIntercept,slope;
    
    public static void main(String[] args) throws IOException {
        loadData();
        slope = getSlope();
        System.out.print(slope);
        populateYHat(slope);
    }
    public static void loadData() throws FileNotFoundException, IOException{ 
        File file = new File("C:\\Users\\Algreg M. Mata\\Desktop\\data.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String line;
        int x =0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split("~");
            data[x][0]=Float.valueOf(tokens[0]);       
            data[x][1]=Float.valueOf(tokens[1]);       
            x++;
        }
        fileReader.close();
    }
    public static float getSlope(){
        float data ,top,bot;
        top = (n*getSumXY())-( getSumX() * getSumY() );
        bot = (n * getSumX2())- (getSumX() * getSumX());
        data = top/bot;
        return data;
    }
    public static float getSumX(){
        float retval =0.0f;
        for(int y=0;y<n;y++){
            retval+= data[y][0];
        }
        return retval;
    }
    public static float getSumX2(){
        float retval =0.0f;
        for(int y=0;y<n;y++){
            retval+= (data[y][0] * data[y][0]);
        }
        return retval;
    }
    public static float getSumY(){
        float retval =0.0f;
        for(int y=0;y<n;y++){
            retval+= data[y][1];
        }
        return retval;
    }
    public static  float getSumXY(){
        float retval =0.0f;
        for(int y=0;y<n;y++){
            retval+= (data[y][0] * data[y][1]);
        }
        return retval;
    }
    
    public static float getYHat(float x, float b, float slope){
      float val = 0.0f;  
      val = (slope * x ) + b;
      return val;
    }

    public static float getB(float x, float y, float slope ){
      float val = 0.0f;
      val = (y - (slope*x)) / data.length;
      return val;
    }
    
    public static void populateYHat(float slope){
      float b = getB(getSumX(),getSumY(),slope);
      
      for(int x=0; x<data.length;x++){
        data[x][2] = getYHat(data[x][0],b,slope) ;
      }
    }
}
