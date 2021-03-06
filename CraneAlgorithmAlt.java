import java.util.*;
import java.io.*;
import java.math.*;

class Player {

    public static String solve(int clawPos, int[] boxes, boolean boxInClaw) {
        // Write your code here
        // To debug: System.err.println("Debug messages...");
        int numberOfPallets = boxes.length;
        int numberOfBoxes = getTotalNumberOfBoxes(boxes, boxInClaw, numberOfPallets);
        int[] sortedPalletArray = getSortedPalletArray(boxes, boxInClaw, numberOfPallets, numberOfBoxes);
        int[] palletOffsetArray = getPalletOffsetArray(boxes, sortedPalletArray, numberOfPallets );

        if(!boxInClaw){
            String leastOffsetSide = getLeastOffsetSide(clawPos,palletOffsetArray);
            int closestPositiveOffsetPostion = getClosestPositiveOffsetPostion(clawPos,palletOffsetArray,leastOffsetSide);

            if(clawPos == closestPositiveOffsetPostion){
                return "PICK";
            } else if(clawPos > closestPositiveOffsetPostion){
                return "LEFT";
            } else {
                return "RIGHT";
            }
        }
        else{
            String leastOffsetSide = getLeastOffsetSide(clawPos,palletOffsetArray);
            int closestNegativeOffsetPostion = getClosestNegativeOffsetPostion(clawPos,palletOffsetArray,leastOffsetSide);
            
            if(clawPos == closestNegativeOffsetPostion){
                return "PLACE";
            } else if(clawPos > closestNegativeOffsetPostion){
                return "LEFT";
            } else {
                return "RIGHT";
            }
        }

    }

    public static String getLeastOffsetSide(int clawPos,int[] palletOffsetArray){
        int leftOffsetNumber = 0;
        int rightOffsetNumber = 0;

        for(int i = 0; i < palletOffsetArray.length; i++){
            if(palletOffsetArray[i] != 0){
                if(i < clawPos){
                    leftOffsetNumber = leftOffsetNumber + 1;
                } else if(i > clawPos){
                    rightOffsetNumber = rightOffsetNumber + 1;
                }
            }
        } 
    
        if(leftOffsetNumber < rightOffsetNumber){
            return "LEFT";
        } else if(rightOffsetNumber < leftOffsetNumber) {
            return "RIGHT";
        } else {
            return "";
        }

    }

    public static int getClosestPositiveOffsetPostion(int clawPos,int[] palletOffsetArray, String leastOffsetSide) {
        int closetPositveOffsetPosition = -1;

        for(int i = 0; i < palletOffsetArray.length; i++){
            int currentDistance = 0;
            if(palletOffsetArray[i] > 0){
                currentDistance = Math.abs(clawPos - i);
            } else {
                continue;
            }
            
		  if(closetPositveOffsetPosition == -1){
            	closetPositveOffsetPosition = i;
		  } else if((Math.abs(clawPos - closetPositveOffsetPosition) > currentDistance)){
                closetPositveOffsetPosition = i;
            } else if((Math.abs(clawPos - closetPositveOffsetPosition) == currentDistance)){
                if((leastOffsetSide.equals("RIGHT")) && (i > clawPos)) {
                    closetPositveOffsetPosition = i;
                } else if((leastOffsetSide.equals("LEFT")) && (i < clawPos)) {
                    closetPositveOffsetPosition = i;
                } 
            } 
        }

        return closetPositveOffsetPosition;
    }

    public static int getClosestNegativeOffsetPostion(int clawPos,int[] palletOffsetArray, String leastOffsetSide) {
        int closetNegativeOffsetPosition = -1;

        for(int i = 0; i < palletOffsetArray.length; i++){
            int currentDistance = 0;
            if(palletOffsetArray[i] < 0){
                currentDistance = Math.abs(clawPos - i);
            } else {
                continue;
            }

            if(closetNegativeOffsetPosition == -1){
            	closetNegativeOffsetPosition = i;
		  } else if((Math.abs(clawPos - closetNegativeOffsetPosition) > currentDistance)){
                closetNegativeOffsetPosition = i;
            } else if((Math.abs(clawPos - closetNegativeOffsetPosition) == currentDistance)){
                if((leastOffsetSide.equals("RIGHT")) && (i > clawPos)) {
                    closetNegativeOffsetPosition = i;
                } else if((leastOffsetSide.equals("LEFT")) && (i < clawPos)) {
                    closetNegativeOffsetPosition = i;
                } 
            } 
        }

        return closetNegativeOffsetPosition;
    }

    public static int getTotalNumberOfBoxes(int[] boxes, boolean boxInClaw, int numberOfPallets) {
        int totalNumberOfBoxes = 0;

        for(int i = 0; i < numberOfPallets;i++) {
            totalNumberOfBoxes += boxes[i];
        }
        if(boxInClaw){
            totalNumberOfBoxes++;
        }

        return totalNumberOfBoxes;
    }


    public static int[] getSortedPalletArray(int[] boxes, boolean boxInClaw, int numberOfPallets, int numberOfBoxes) {
        int[] sortedPalletArray = new int[numberOfPallets];
        int j = 0;

        for(int i = 0; i < numberOfBoxes;i++) {
            if(j == numberOfPallets){
                j = 0;
            }
            sortedPalletArray[j] = sortedPalletArray[j] + 1; 
            j++;           
        }

        return sortedPalletArray;
    }

    public static int[] getPalletOffsetArray(int[] boxes, int[] sortedPalletArray, int numberOfPallets) {
        int[] palletOffsetArray = new int[numberOfPallets];

        for(int i = 0; i < numberOfPallets;i++) {
            palletOffsetArray[i] = boxes[i] - sortedPalletArray[i]; 
        }

        return palletOffsetArray;
    }

    /* Ignore and do not change the code below */
    // #region main
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int clawPos = in.nextInt();
            boolean boxInClaw = in.nextInt() != 0;
            int stacks = in.nextInt();
            int[] boxes = new int[stacks];
            for (int i = 0; i < stacks; i++) {
                boxes[i] = in.nextInt();
            }
            PrintStream outStream = System.out;
            System.setOut(System.err);
            String action = solve(clawPos, boxes, boxInClaw);
            System.setOut(outStream);
            System.out.println(action);
        }
    }
    // #endregion
}


