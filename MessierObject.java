import java.util.ArrayList;

public class MessierObject implements Comparable{
    private String messierNumber;
    private String NGCIC;
    private String commonName;
    private String originalCommonName;
    private String type;
    private double lightYearDistance;
    private String originalLightYearDistance;
    private String constellation;
    private float appMagnitude;
    private String originalAppMagnitude;
    private double rightAscension;
    private String originalRightAscension;
    private double declination;
    private String originalDeclination;
    final double degreesToRadians = Math.PI/180;
    // final double arcminutesToRadians = 0.000290888;
    // final double arcsecondsToRadians = 4.848*(Math.pow(10,-6));



    public MessierObject(){

    }
    public MessierObject(String messierString){
        final StringBuilder stringExtract = new StringBuilder();
        final StringBuilder tempMessierString = new StringBuilder();
        int fieldCount = 0;
        tempMessierString.append(messierString);
        do{
            stringExtract.delete(0,stringExtract.length());
            if (fieldCount==2 || fieldCount==1){
                //common names and NGC/IC number need to have a separate if branch
                //as they are internally separated by commas
                int quotationIndex=tempMessierString.indexOf("\"",1);
                //the common names and NGC/IC values are
                //always contained in quotation marks
                stringExtract.append(tempMessierString.substring(0,quotationIndex+1));
                //extracts everything that's in the quotation marks
                tempMessierString.delete(0,quotationIndex+3);
            } else if(fieldCount!=8){
                //any field that's not common name or NGC/IC
                int commaIndex = tempMessierString.indexOf(",", 0);
                //all other fields can be separated by commas
                stringExtract.append(tempMessierString.substring(0, commaIndex));
                tempMessierString.delete(0, commaIndex +2);
            }
            fieldCount=fieldCount+1;
            switch (fieldCount){
                case(1):
                    this.messierNumber=stringExtract.toString();
                    // System.out.println(this.messierNumber);
                    // System.out.println(tempMessierString);
                    break;
                case(2):
                    if (stringExtract.toString().equals("\"-\"")){
                        this.NGCIC="This object has no NGC/IC number";
                    } else{
                        this.NGCIC=stringExtract.toString();
                    }
                    // System.out.println(this.NGCIC);
                    // System.out.println(tempMessierString);
                    break;
                case(3):
                    this.originalCommonName=stringExtract.toString();
                    //original values retained so that they can be
                    // held in the original messier list
                    if ((stringExtract.toString()).equals("\"-\"")){
                        this.commonName="This object has no name";
                    } else{
                        this.commonName=stringExtract.toString();
                    }
                    // System.out.println(this.commonName);
                    // System.out.println(tempMessierString);
                    break;
                case(4):
                    this.type=stringExtract.toString();
                    // System.out.println(this.type);
                    // System.out.println(tempMessierString);
                    break;

                case(5):
                    this.originalLightYearDistance=stringExtract.toString();
                    if ((stringExtract.toString()).contains("-")){
                        this.lightYearDistance=calculateLightYearDistance(stringExtract);
                    } else{
                        this.lightYearDistance=Double.parseDouble(
                                stringExtract.toString());
                        // System.out.println(tempMessierString);
                    }
                    break;
                case(6):
                    this.constellation=stringExtract.toString();
                    // System.out.println(tempMessierString);
                    break;

                case(7):
                    this.originalAppMagnitude=stringExtract.toString();
                    this.appMagnitude=Float.parseFloat(stringExtract.toString());
                    // System.out.println(tempMessierString);
                    break;

                case(8):
                    this.originalRightAscension=stringExtract.toString();
                    this.rightAscension=calculateRightAscension(stringExtract);
                    // System.out.println(tempMessierString);
                    break;

                case(9):
                    this.originalDeclination=tempMessierString.toString();
                    // System.out.println("original: " + this.originalDeclination);
                    // System.out.println(tempMessierString);
                    this.declination=calculateDeclination(tempMessierString);
                    tempMessierString.delete(0,tempMessierString.length());

            }
        }
        while(fieldCount<9);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Calculation methods

    private double calculateLightYearDistance(StringBuilder lightYearDistance){
        double minDistance=Double.parseDouble(
                lightYearDistance.substring(
                        0,lightYearDistance.indexOf("-")));
        lightYearDistance.delete(
                0,lightYearDistance.indexOf("-")+1);
        return minDistance;
    }

    private double calculateRightAscension(StringBuilder rightAscension){
        double hours=Double.parseDouble(
                rightAscension.substring(0,rightAscension.indexOf("h")));
        rightAscension.delete(
                0,rightAscension.indexOf("h")+1);
        double minutes=Double.parseDouble(
                rightAscension.substring(0,rightAscension.indexOf("m")));
        rightAscension.delete(
                0,rightAscension.indexOf("m")+1);
        double seconds=Double.parseDouble(
                rightAscension.substring(0,rightAscension.indexOf("s")));
        rightAscension.delete(
                0,rightAscension.indexOf("s")+1);
        double rightAscensionCalcPart1=hours+(minutes/60)+(seconds/3600);
        return (rightAscensionCalcPart1*15)*degreesToRadians;
    }

    private double calculateDeclination(StringBuilder declination){
//        System.out.println("Declination is " + declination);
        double degrees=Double.parseDouble(
                declination.substring(0,declination.indexOf("°")));
        declination.delete(
                0,declination.indexOf("°")+1);
//        System.out.println("Declination is now " + declination);
        double arcminutes=Double.parseDouble(
                declination.substring(0,declination.indexOf("'")));
        declination.delete(
                0,declination.indexOf("'")+1);
//        System.out.println("Declination is now " + declination);
        double arcseconds=Double.parseDouble(
                declination.substring(0,declination.indexOf("\"")));
        double declinationInDegrees=degrees+(arcminutes/60)+(arcseconds/3600);
        return declinationInDegrees * degreesToRadians;
        // arcminutesInRadians=arcminutes * arcminutesToRadians;
        // arcsecondsInRadians=arcseconds * arcsecondsToRadians;
    }

    public static double calculateAngularDistance(Object rightAscension1,
                                                  Object declination1,
                                                  Object rightAscension2,
                                                  Object declination2){
        double RA1 = Double.parseDouble(rightAscension1.toString());
        double Dec1 = Double.parseDouble(declination1.toString());
        double RA2 = Double.parseDouble(rightAscension2.toString());
        double Dec2 = Double.parseDouble(declination2.toString());
        return (Math.sin(Dec1)*Math.sin(Dec2))+ (Math.cos(Dec1)+Math.cos(Dec2)*Math.cos(RA1-RA2));
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Get methods

    public String getMessierNumber(){
        return this.messierNumber;
    }

    public String getNGCIC(){
        return this.NGCIC;
    }

    public String getCommonName(){
        return this.commonName;
    }

    public String getOriginalCommonName(){
        return this.originalCommonName;
    }

    public String getType(){
        return this.type;
    }

    public Double getLightYearDistance(){
        return this.lightYearDistance;
    }

    public String getOriginalLightYearDistance(){
        return this.originalLightYearDistance;
    }

    public String getConstellation(){
        return this.constellation;
    }

    public Float getAppMagnitude(){
        return this.appMagnitude;
    }

    public String getOriginalAppMagnitude(){
        return this.originalAppMagnitude;
    }

    public Double getRightAscension(){
        return this.rightAscension;
    }

    public String getOriginalRightAscension(){
        return this.originalRightAscension;
    }

    public Double getDeclination(){
        return this.declination;
    }

    public String getOriginalDeclination(){
        return this.originalDeclination;
    }


    public int compareTo(double num1, double num2){
        //implemented from the Comparable interface
        if (num1>num2){
            return 1;
        } else if(num1<num2){
            return -1;
        } else{
            return 0;
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //String formatters
    public String internalNewToString(){
        //used by an instance of MessierObject to
        //print out the details of a Messier object with its new values
        return String.format(
                "%s, %s, %s, %s, %.2f, %s, %.2f, %.18f rad, %.18f rad",
                this.getMessierNumber(),
                this.getNGCIC(),
                this.getCommonName(),
                this.getType(),
                this.getLightYearDistance(),
                this.getConstellation(),
                this.getAppMagnitude(),
                this.getRightAscension(),
                this.getDeclination());
    }

    public String internalOriginalToString(){
        //used by an instance of MessierObject to
        //print out the details of a Messier object with its original values
        return String.format(
                "%s, %s, %s, %s, %s, %s, %s, %s, %s",
                this.getMessierNumber(),
                this.getNGCIC(),
                this.getOriginalCommonName(),
                this.getType(),
                this.getOriginalLightYearDistance(),
                this.getConstellation(),
                this.getOriginalAppMagnitude(),
                this.getOriginalRightAscension(),
                this.getOriginalDeclination());
    }

    public static String toString(ArrayList<Object> listEntry,
                                  ArrayList<String> originalMessierList){
        //converts an entry from the new Messier list into
        //a string which can be read into the MessierObject class
        String messierNumber = listEntry.get(0).toString();
        messierNumber=messierNumber.replace("M","");
        int originalListIndex=Integer.parseInt(messierNumber);
        originalListIndex=originalListIndex-1;
        return originalMessierList.get(originalListIndex);
    }

    public static void main(String[] args){
        String test="M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, " +
                "4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"";
        String test2="M2, \"NGC 7089\", \"-\", Globular cluster, " +
                "33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"";
        String test3="M17, \"NGC 6618\", \"Omega, Swan, Horseshoe, Lobster, or Checkmark Nebula\", " +
                "H II region nebula with cluster, 5.0-6.0, Sagittarius, " +
                "6.0, 18h 20m 26.0000s, -16° 10' 36.0000\"";
        MessierObject testing = new MessierObject(test);
        MessierObject testing2 = new MessierObject(test2);
        MessierObject testing3 = new MessierObject(test3);
        System.out.println(testing.getMessierNumber());
        System.out.println(testing.getNGCIC());
        System.out.println(testing.getCommonName());
        System.out.println(testing.getType());
        System.out.println(testing.getLightYearDistance());
        System.out.println(testing.getConstellation());
        System.out.println(testing.getAppMagnitude());
        System.out.println(testing.getRightAscension());
        System.out.println(testing.getDeclination());
        System.out.println(testing.internalNewToString());
        System.out.println(testing.internalOriginalToString());

        System.out.println(testing2.getMessierNumber());
        System.out.println(testing2.getNGCIC());
        System.out.println(testing2.getCommonName());
        System.out.println(testing2.getType());
        System.out.println(testing2.getLightYearDistance());
        System.out.println(testing2.getConstellation());
        System.out.println(testing2.getAppMagnitude());
        System.out.println(testing2.getRightAscension());
        System.out.println(testing2.getDeclination());
        System.out.println(testing2.internalNewToString());
        System.out.println(testing2.internalOriginalToString());

        System.out.println(testing3.getMessierNumber());
        System.out.println(testing3.getNGCIC());
        System.out.println(testing3.getCommonName());
        System.out.println(testing3.getType());
        System.out.println(testing3.getLightYearDistance());
        System.out.println(testing3.getConstellation());
        System.out.println(testing3.getAppMagnitude());
        System.out.println(testing3.getRightAscension());
        System.out.println(testing3.getDeclination());
        System.out.println(testing3.internalNewToString());
        System.out.println(testing3.internalOriginalToString());
    }
}
