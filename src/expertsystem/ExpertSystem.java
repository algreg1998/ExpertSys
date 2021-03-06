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
         1 - x1,
         2 - y,
         3 - y hat,
         4 - e,
         5 - e^2
         ]
    */
    static int n = 731;
    static float[][] data = new float[n][6];
    static float yIntercept,slope;
    
    public static void main(String[] args) throws IOException {
        loadData();
        slope = getSlope();
        System.out.println(slope);
        float average_y = getSumY()/n;
        float average_x = getSumX()/n;
        float average_yHat = populateYHat(slope);
        float average_error = populateError();
        float error;
        for(int x = 0;average_error > 1.0f && x < 1000;x++){
            error = (Math.abs(average_y - average_yHat))*average_x;
            slope = slope - error;
            average_yHat = populateYHat(slope);
            average_error = populateError();
        }
        for(int x =0; x < data.length;x++){
            System.out.print("x:" + data[x][0]);
            System.out.print(" x1:" +data[x][1]);
            System.out.print(" y:" +data[x][2]);
            System.out.print(" y^:" +data[x][3]);
            System.out.print(" e:" +data[x][4]);
            System.out.print(" e^2:" +data[x][5]);
            System.out.print('\n');
        }
    }
    public static void loadData() throws FileNotFoundException, IOException{ 
        File file = new File("day.csv");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String line;
        int x =0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(",");
            data[x][0]=Float.valueOf(tokens[0]);       
            data[x][1]=Float.valueOf(tokens[1]);       
            data[x][2]=Float.valueOf(tokens[2]);       
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
    
    public static float populateYHat(float slope){
      float b = getB(getSumX(),getSumY(),slope), total = 0.0f;
      System.out.println(b);
      for(int x=0; x<data.length;x++){
        data[x][2] = getYHat(data[x][0],b,slope) ;
        total += data[x][2];
      }
      
      return total/n;
    }
    
    public static float populateError(){
        float total = 0.0f;
        
        for(int x = 0;x < data.length; x++){
            data[x][3] = data[x][1] - data[x][2];
            data[x][4] = data[x][3] * data[x][3];
            total += data[x][4];
        }
        
        return total/n;
    }
}
