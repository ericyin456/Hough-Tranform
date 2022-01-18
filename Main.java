import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		File data = new File(args[0]);
		File prettyPrint = new File(args[1]);
		File result = new File(args[2]);
		
		Scanner sc = null;
		
		int numRows = 0, numCols = 0, minVal = 0, maxVal = 0;
		
		try {
			sc = new Scanner(data);
			numRows = sc.nextInt();
			numCols = sc.nextInt();
			minVal = sc.nextInt();
			maxVal = sc.nextInt();
			
			HoughTransform img = new HoughTransform(numRows, numCols, minVal, maxVal);
			img.loadImage(sc);
			sc.close();
			
			img.buildHoughSpace(img.imgAry);
			
			img.prettyPrint(img.houghAry, prettyPrint);
			img.determineMinMax(img.houghAry);
			img.ary2File(img.houghAry, result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
