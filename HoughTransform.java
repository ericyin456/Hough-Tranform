import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HoughTransform {
	int numRows, numCols, minVal, maxVal;
	int	houghMinVal, houghMaxVal, houghDist, houghAngle;
	
	int[][] imgAry;
	int[][] houghAry;
	
	int angleInDegree;
	double angleInRadians;
	int offSet;
	
	public HoughTransform(int _rows, int _cols, int _minVal, int _maxVal){
		numRows = _rows;
		numCols = _cols;
		minVal = _minVal;
		maxVal = _maxVal;
		
		houghAngle = 180;
		offSet = (int) (Math.sqrt(Math.pow(numRows, 2) + Math.pow(numCols, 2))) + 1;

		houghDist = 2 * offSet;

		imgAry = new int[numRows][numCols];
		houghAry = new int[houghDist][houghAngle];
	}
	void loadImage(Scanner sc) {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				imgAry[i][j] = sc.nextInt();
			}
		}
	}
	void buildHoughSpace(int[][] houghArr) {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				if(houghArr[i][j] > 0) {
					computeSinusoid(i, j);
				}
			}
		}
	}
	void computeSinusoid(int row, int col) {
		angleInDegree = 0;
		while(angleInDegree < 180) {
			angleInRadians = angleInDegree/180.00 * Math.PI;
			double dis = polarDistance(row, col, angleInRadians);
			int distInt = (int) dis-1;
			houghAry[distInt][angleInDegree]++;
			angleInDegree++;
		}
	}
	double polarDistance(int row, int col, double angleInRad) {
		return (double)col * Math.cos(angleInRad) + (double)row * Math.sin(angleInRad) + offSet;
	}
	void determineMinMax(int[][] houghArr) {
		houghMinVal = 9999;
		houghMaxVal = 0;
		for(int i = 0; i < houghArr.length; i++) {
			for(int j = 0; j < houghArr[i].length; j++) {
				if(houghMinVal > houghArr[i][j]) houghMinVal = houghArr[i][j];
				if(houghMaxVal < houghArr[i][j]) houghMaxVal = houghArr[i][j];
			}
		}
	}
	void prettyPrint(int[][] arr, File prettyPrintFile) {
		try {
			FileWriter fw = new FileWriter(prettyPrintFile);
			
			String str = Integer.toString(maxVal);
			int width = str.length();
			
			for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < arr[i].length; j++) {
					if(arr[i][j] > 0) {
						fw.write(arr[i][j] + " ");
					}else
						fw.write(". ");
					str = Integer.toString(arr[i][j]);
					int WW = str.length();
					while(WW < width) {
						fw.write(" ");
						WW++;
					}
				}
				fw.write('\n');
			}
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void ary2File(int[][] houghAry, File outFile) {
		try {
			FileWriter fw = new FileWriter(outFile);
			fw.write(houghDist + " " + houghAngle + " " + houghMinVal + " " + houghMaxVal + "\n");
			String str = Integer.toString(houghMaxVal);
			int width = str.length();
			
			for(int i = 0; i < houghAry.length; i++) {
				for(int j = 0; j < houghAry[i].length; j++) {
					if(houghAry[i][j] > 5) {
						fw.write(houghAry[i][j] + " ");
					}else
						fw.write(". ");
					str = Integer.toString(houghAry[i][j]);
					int WW = str.length();
					while(WW < width) {
						fw.write(" ");
						WW++;
					}
				}
				fw.write('\n');
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
