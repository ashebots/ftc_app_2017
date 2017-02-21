package org.firstinspires.ftc.teamcode.auto;

public class CenterAlignment { //This class is used to attempt to center the robot at the beacon accuratley.
    int[] hits = new int[300];
    double[] sawBeacon = new double[300];
    double[] xPosCentered = new double[300];
    double[] confidence = new double[300];
    public double[] totalScore = new double[300];

    public void inputData(int encValue, boolean sawBeacon, double xPosCentered, double confidence) {
        if (encValue>299) return;
        hits[encValue]++;
        if (sawBeacon) {
            this.sawBeacon[encValue]++;
            this.xPosCentered[encValue]+=xPosCentered;
            this.confidence[encValue]+=confidence;
        }
    }

    public int findCenter() {
        double totalValue = 0;
        //COMPUTE TOTALS
        for (int i = 0; i < 300; i++) {
            xPosCentered[i] /= sawBeacon[i];
            if (sawBeacon[i] == 0) xPosCentered[i] = 0;
            confidence[i] /= sawBeacon[i];
            if (sawBeacon[i] == 0) confidence[i] = 0;
            sawBeacon[i] /= hits[i];
            if (hits[i] == 0) sawBeacon[i] = 0;
            totalScore[i] = xPosCentered[i] + confidence[i] + sawBeacon[i];
            if (totalScore[i] == 0 && i>0) totalScore[i] = totalScore[i-1];
            totalValue += totalScore[i];
        }
        //FIND MEDIAN AND MODE
        double halfValue = 0;
        double maxValue = 0;
        int median = 0;
        int mode = 0;
        for (int i = 0; i < 300; i++) {
            halfValue += totalScore[i];
            if (halfValue > totalValue / 2 && median == 0) {
                median = i;
            }
            if (totalScore[i] > maxValue) {
                maxValue = totalScore[i];
                mode = i;
            }
        }
        //FIND LOCAL MAXIMUMS
        int[] top3lcl = new int[3];
        int[] top3pos = new int[3];
        for (int i = 0; i < 300; i++) {
            int locality = 0;
            for (int l = 1; l <= Math.min(i,299-i); l++) {
                if (totalScore[i-l+1]>=totalScore[i-l] && totalScore[i+l-1]>=totalScore[i+l]) { //gets smaller on both sides
                    locality++;
                } else {
                    break;
                }
            }
            if (locality > top3lcl[0]) {
                top3lcl[2] = top3lcl[1];
                top3pos[2] = top3pos[1];
                top3lcl[1] = top3lcl[0];
                top3pos[1] = top3pos[0];
                top3lcl[0] = locality;
                top3pos[0] = i;
            } else if (locality > top3lcl[1]) {
                top3lcl[2] = top3lcl[1];
                top3pos[2] = top3pos[1];
                top3lcl[1] = locality;
                top3pos[1] = i;
            } else if (locality > top3lcl[2]) {
                top3lcl[2] = locality;
                top3pos[2] = i;
            }
        }
        int localMax = 0;
        if (top3lcl[2] > 5) {
            if (top3pos[0]>top3pos[1]) {
                int trans = top3pos[0];
                top3pos[0] = top3pos[1];
                top3pos[1] = trans;
            }
            if (top3pos[0]>top3pos[2]) {
                int trans = top3pos[0];
                top3pos[0] = top3pos[2];
                top3pos[2] = trans;
            }
            if (top3pos[1]>top3pos[2]) {
                int trans = top3pos[1];
                top3pos[1] = top3pos[2];
                top3pos[2] = trans;
            }
            localMax = top3pos[1]; //if there are 3 significant local maximums
        } else {
            localMax = top3pos[0]; //if there are too few significant local maximums to be sure they are the ones we're looking for
        }
        return localMax;
        //return (localMax + median + mode) / 3; //Return average of middle
    }
}
