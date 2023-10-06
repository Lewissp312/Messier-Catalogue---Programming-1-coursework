import java.io.*;
import java.util.ArrayList;
import java.lang.StringBuilder;
class MessierCatalogue {
    private final ArrayList<ArrayList<Object>> newMessierList;
    private final ArrayList<String> originalMessierList;
    private final MessierObject compare;
    private final int messierNumberIndex;
    private final int NGCICIndex;
    private final int commonNameIndex;
    private final int typeIndex;
    private final int lightYearDistanceIndex;
    private final int constellationIndex;
    private final int appMagnitudeIndex;
    private final int rightAscensionIndex;
    private final int declinationIndex;


    public MessierCatalogue(BufferedReader reader) {
        //these index numbers can be easily changed should a new
        // field be added to the messier catalogue or if they are
        // arranged in a different order. They also make identifying
        // list entries a lot easier
        this.messierNumberIndex=0;
        this.NGCICIndex=1;
        this.commonNameIndex=2;
        this.typeIndex=3;
        this.lightYearDistanceIndex=4;
        this.constellationIndex=5;
        this.appMagnitudeIndex=6;
        this.rightAscensionIndex=7;
        this.declinationIndex=8;
        newMessierList= new ArrayList<ArrayList<Object>>();
        //stores the Messier catalogue with all of its new values.
        //stored in an arraylist in case any new Messier objects are
        //added
        originalMessierList = new ArrayList<String>();
        //stores the Messier catalogue with all of its original values.
        this.compare = new MessierObject();
        //used to access the compareTo method from MessierObject
        //that is guaranteed by the Comparable interface
        try {
            for (int i = 0 ; i <= 109 ; i++) {
                //if a new item was added to the Messier catalogue,
                //the 109 would need to be increased
                ArrayList<Object> temp = new ArrayList<Object>(8);
                String lineFromFile = reader.readLine();
                MessierObject fileToList = new MessierObject(lineFromFile);
                temp.add(fileToList.getMessierNumber());
                temp.add(fileToList.getNGCIC());
                temp.add(fileToList.getCommonName());
                temp.add(fileToList.getType());
                temp.add(fileToList.getLightYearDistance());
                temp.add(fileToList.getConstellation());
                temp.add(fileToList.getAppMagnitude());
                temp.add(fileToList.getRightAscension());
                temp.add(fileToList.getDeclination());
                newMessierList.add(temp);
                originalMessierList.add(lineFromFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //answers for all the required queries

    public ArrayList<ArrayList<Object>> getApparentMagnitudeOrder() {
        Boolean sorted = false;
        ArrayList<Object> temp;
        ArrayList<ArrayList<Object>> magnitudeList =
                new ArrayList<ArrayList<Object>>(110);
        magnitudeList.addAll(newMessierList);
        do {
            Boolean changed = false;
            for (int i = 0 ; i < magnitudeList.size()-1 ; i++) {
                //using .size() means that these methods do not need to
                //be changed if the size of the catalogue is increased
                float magnitude1 = Float.parseFloat(
                        (magnitudeList.get(i).get(appMagnitudeIndex)).toString());
                //the values are being stored as objects, so they must be converted to
                //strings, which can then be converted to the correct type
                float magnitude2 = Float.parseFloat(
                        (magnitudeList.get(i + 1).get(appMagnitudeIndex)).toString());
                if (compare.compareTo(magnitude1, magnitude2) == 1) {
                    temp = magnitudeList.get(i);
                    magnitudeList.set(i, magnitudeList.get(i + 1));
                    magnitudeList.set(i + 1, temp);
                    changed = true;
                }
            }
            if (changed == false) {
                sorted = true;
            }
        } while (sorted == false);
        return magnitudeList;
    }

    public double getOpenClustersAppMagnitude() {
        float totalAppMagnitude = 0;
        ArrayList<ArrayList<Object>> openClusters = this.getOpenClusters();
        for (int i = 0 ; i < openClusters.size() ; i++) {
            totalAppMagnitude = totalAppMagnitude + Float.parseFloat(
                    (openClusters.get(i).get(appMagnitudeIndex)).toString());
        }
        return  totalAppMagnitude / openClusters.size();
    }

    public ArrayList<Object> getMostDistantGlobularCluster(){
        ArrayList<ArrayList<Object>> globularClusters = this.getGlobularClusters();
        ArrayList<Object> furthestDistanceDetails = new ArrayList<Object>(1);
        double distanceToCompare;
        double furthestDistance=Double.parseDouble(
                globularClusters.get(0).get(lightYearDistanceIndex).toString());
        for (int i = 0 ; i<globularClusters.size() ; i++){
            distanceToCompare=Double.parseDouble(
                    globularClusters.get(i).get(lightYearDistanceIndex).toString());
            if (compare.compareTo(distanceToCompare,furthestDistance)==1){
                furthestDistance = distanceToCompare;
                furthestDistanceDetails = globularClusters.get(i);

            }
        }
        return furthestDistanceDetails;
    }
    public ArrayList<Object> getSagittariusLowestDeclination(){
        ArrayList<ArrayList<Object>> sagittarius = this.getSagittarius();
        ArrayList<Object> lowestDeclinationDetails = new ArrayList<Object>(1);
        double declinationToCompare;
        double lowestDeclination=Double.parseDouble(
                sagittarius.get(0).get(declinationIndex).toString());
        for (int i = 0 ; i < sagittarius.size() ; i++){
            declinationToCompare=Double.parseDouble(
                    sagittarius.get(i).get(declinationIndex).toString());
            if (compare.compareTo(declinationToCompare,lowestDeclination)==-1){
                lowestDeclination = declinationToCompare;
                lowestDeclinationDetails = sagittarius.get(i);
            }
        }
        return lowestDeclinationDetails;
    }

    public ArrayList<Object> getObjectClosestToM45(){
        ArrayList<Object> lowestADDetails = new ArrayList<Object>(1);
        double m45Dec = Double.parseDouble(
                newMessierList.get(44).get(declinationIndex).toString());
        double m45RA = Double.parseDouble(
                newMessierList.get(44).get(rightAscensionIndex).toString());
        double firstDec = Double.parseDouble(
                newMessierList.get(0).get(declinationIndex).toString());
        double firstRA = Double.parseDouble(
                newMessierList.get(0).get(rightAscensionIndex).toString());
        double lowestAngularDistance = MessierObject.calculateAngularDistance(
                m45RA,m45Dec,firstRA,firstDec);
        for (int i = 0; i<newMessierList.size();i++){
            if (i!=44){
                double RAToCompare = Double.parseDouble(
                        newMessierList.get(i).get(rightAscensionIndex).toString());
                double decToCompare = Double.parseDouble(
                        newMessierList.get(i).get(declinationIndex).toString());
                double ADToCompare = MessierObject.calculateAngularDistance(
                        m45RA,m45Dec,RAToCompare,decToCompare);
                if (compare.compareTo(ADToCompare,lowestAngularDistance)==-1){
                    lowestAngularDistance = ADToCompare;
                    lowestADDetails = newMessierList.get(i);
                }
            }
        }
        return lowestADDetails;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //printing out the list with new and old values

    public String getOriginalMessierListToString() {
        //prints out the Messier list in the same format as the datafile
        //with all of its original values
        StringBuilder allMessierObjects = new StringBuilder();
        for (int i = 0; i < originalMessierList.size(); i++) {
            allMessierObjects.append(String.format(
                    "%s \n", originalMessierList.get(i)));
        }
        return allMessierObjects.toString();
    }

    public String getNewMessierListToString() {
        //prints out the Messier list in the same format as the datafile
        //with all of its new values
        return this.queryListToNewString(newMessierList);
    }

    public ArrayList<String> getOriginalMessierList(){
        return originalMessierList;
    }

    public ArrayList<ArrayList<Object>> getNewMessierlist(){
        return newMessierList;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //obtaining specific objects in the lists

    public ArrayList<Object> getNewMessierObject(int messierNumber){
        //get a Messier object (with its new values) by
        //referring to its Messier number
        messierNumber=messierNumber-1;
        return newMessierList.get(messierNumber);
    }

    public String getOriginalMessierObject(int messierNumber){
        //get a Messier object (with its original values) by
        //referring to its Messier number
        messierNumber=messierNumber-1;
        return originalMessierList.get(messierNumber);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //creation and formatting of query lists

    public String queryListToNewString(ArrayList<ArrayList<Object>> queryList) {
        //takes in any query list and outputs it in the format used
        //in the file, using the new values
        StringBuilder queryListAsString = new StringBuilder();
        for (int i = 0; i < queryList.size(); i++) {
            queryListAsString.append(String.format(
                    "%s, %s, %s, %s, %.2f, %s, %.2f, %.18f rad, %.18f rad \n",
                    queryList.get(i).get(messierNumberIndex),
                    queryList.get(i).get(NGCICIndex),
                    queryList.get(i).get(commonNameIndex),
                    queryList.get(i).get(typeIndex),
                    queryList.get(i).get(lightYearDistanceIndex),
                    queryList.get(i).get(constellationIndex),
                    queryList.get(i).get(appMagnitudeIndex),
                    queryList.get(i).get(rightAscensionIndex),
                    queryList.get(i).get(declinationIndex)));
        }
        return queryListAsString.toString();
    }

    public String queryListToOriginalString(ArrayList<ArrayList<Object>> queryList) {
        //takes in any query list and outputs it in the format used
        //in the file, using the original values
        StringBuilder queryListAsString = new StringBuilder();
        for (int i = 0 ; i< queryList.size() ; i++){
            String listEntryAsString =
                    MessierObject.toString(queryList.get(i),originalMessierList);
            queryListAsString.append(String.format("%s\n", listEntryAsString));
        }
        return queryListAsString.toString();
    }

    public String queryListEntryToNewString(ArrayList<Object> queryList){
        //this one and the one below are the same as the
        //two above but just for individual entries
        StringBuilder queryListAsString = new StringBuilder();
        queryListAsString.append(String.format(
                "%s, %s, %s, %s, %.2f, %s, %.2f, %.18f rad, %.18f rad \n",
                queryList.get(messierNumberIndex),
                queryList.get(NGCICIndex),
                queryList.get(commonNameIndex),
                queryList.get(typeIndex),
                queryList.get(lightYearDistanceIndex),
                queryList.get(constellationIndex),
                queryList.get(appMagnitudeIndex),
                queryList.get(rightAscensionIndex),
                queryList.get(declinationIndex)));
        return queryListAsString.toString();
    }

    public String queryListEntryToOriginalString(ArrayList<Object> queryList){
        StringBuilder queryListAsString = new StringBuilder();
        String listEntryAsString =
                MessierObject.toString(queryList,originalMessierList);
        MessierObject originalQueryList = new MessierObject(listEntryAsString);
        queryListAsString.append(String.format(
                "%s",originalQueryList.internalOriginalToString()));
        return queryListAsString.toString();


    }

    private ArrayList<ArrayList<Object>> getQueryList(int indexOfItem,
                                                      Object desiredItem,
                                                      ArrayList<ArrayList<Object>> messierList){
        //Used by all the additional get methods.
        //Generates a list which matches certain criteria
        ArrayList<ArrayList<Object>> queryList =
                new ArrayList<ArrayList<Object>>(20);
        for (int i = 0 ; i < newMessierList.size() ; i++){
            if (newMessierList.get(i).get(indexOfItem).equals(desiredItem)){
                queryList.add(newMessierList.get(i));
            }
        }
        return queryList;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Additional get methods which can be used for general queries

    public ArrayList<ArrayList<Object>> getSupernovaRemnants(){
        return this.getQueryList(typeIndex,"Supernova remnant",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getGlobularClusters(){
        return this.getQueryList(typeIndex,"Globular cluster",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getOpenClusters(){
        return this.getQueryList(typeIndex,"Open cluster",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getNebulaWithClusters(){
        return this.getQueryList(typeIndex,"Nebula with cluster",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getHIIClusters(){
        return this.getQueryList(typeIndex,"H II region nebula with cluster",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getMilkyWayStarClouds(){
        return this.getQueryList(typeIndex,"Milky Way star cloud",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getPlanetaryNebulas(){
        return this.getQueryList(typeIndex,"Planetary nebula",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getSpiralGalaxies(){
        return this.getQueryList(typeIndex,"Spiral galaxy",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getDwarfEllipticalGalaxies(){
        return this.getQueryList(typeIndex,"Dwarf elliptical galaxy",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getStarSystems(){
        return this.getQueryList(typeIndex,"Star system",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getHIINebulas(){
        return this.getQueryList(typeIndex,"H II region nebula",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getEllipticalGalaxies(){
        return this.getQueryList(typeIndex,"Elliptical galaxy",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getAsterisms(){
        return this.getQueryList(typeIndex,"Asterism",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getDiffuseNebulas(){
        return this.getQueryList(typeIndex,"Diffuse nebula",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getStarburstGalaxies(){
        return this.getQueryList(typeIndex,"Starburst galaxy",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getLenticularGalaxies(){
        return this.getQueryList(typeIndex,"Lenticular galaxy",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getTaurus(){
        return this.getQueryList(constellationIndex,"Taurus",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getAquarius(){
        return this.getQueryList(constellationIndex,"Aquarius",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getCanesVenatici(){
        return this.getQueryList(constellationIndex,"Canes Venatici",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getScorpius(){
        return this.getQueryList(constellationIndex,"Scorpius",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getSerpens(){
        return this.getQueryList(constellationIndex,"Serpens",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getSagittarius(){
        return this.getQueryList(constellationIndex,"Sagittarius",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getOphiuchus(){
        return this.getQueryList(constellationIndex,"Ophiuchus",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getScutum(){
        return this.getQueryList(constellationIndex,"Scutum",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getHercules(){
        return this.getQueryList(constellationIndex,"Hercules",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getPegasus(){
        return this.getQueryList(constellationIndex,"Pegasus",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getVulpecula(){
        return this.getQueryList(constellationIndex,"Vulpecula",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getCygnus(){
        return this.getQueryList(constellationIndex,"Cygnus",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getCapricornus(){
        return this.getQueryList(constellationIndex,"Capricornus",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getAndromeda(){
        return this.getQueryList(constellationIndex,"Andromeda",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getTriangulum(){
        return this.getQueryList(constellationIndex,"Triangulum",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getPerseus(){
        return this.getQueryList(constellationIndex,"Perseus",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getGemini(){
        return this.getQueryList(constellationIndex,"Gemini",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getAuriga(){
        return this.getQueryList(constellationIndex,"Auriga",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getUrsaMajor(){
        return this.getQueryList(constellationIndex,"Ursa Major",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getCanisMajor(){
        return this.getQueryList(constellationIndex,"Canis Major",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getOrion(){
        return this.getQueryList(constellationIndex,"Orion",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getCancer(){
        return this.getQueryList(constellationIndex,"Cancer",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getPuppis(){
        return this.getQueryList(constellationIndex,"Puppis",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getHydra(){
        return this.getQueryList(constellationIndex,"Hydra",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getVirgo(){
        return this.getQueryList(constellationIndex,"Virgo",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getMonoceros(){
        return this.getQueryList(constellationIndex,"Monoceros",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getCassiopeia(){
        return this.getQueryList(constellationIndex,"Cassiopeia",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getComaBerenices(){
        return this.getQueryList(constellationIndex,"Coma Berenices",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getLyra(){
        return this.getQueryList(constellationIndex,"Lyra",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getLeo(){
        return this.getQueryList(constellationIndex,"Leo",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getSagitta(){
        return this.getQueryList(constellationIndex,"Sagitta",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getPisces(){
        return this.getQueryList(constellationIndex,"Pisces",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getCetus(){
        return this.getQueryList(constellationIndex,"Cetus",newMessierList);
    }


    public ArrayList<ArrayList<Object>> getLepus(){
        return this.getQueryList(constellationIndex,"Lepus",newMessierList);
    }

    public ArrayList<ArrayList<Object>> getDraco(){
        return this.getQueryList(constellationIndex,"Draco",newMessierList);
    }

    public static void main(String[] args) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("messier.txt"));
            MessierCatalogue testing = new MessierCatalogue(reader);
            System.out.println(testing.getApparentMagnitudeOrder());
            System.out.println(testing.getApparentMagnitudeOrder());
            System.out.println(testing.getOriginalMessierListToString());
            System.out.println(
                    testing.newMessierList.get(0).get(testing.declinationIndex));
            System.out.println(MessierObject.calculateAngularDistance(
                    testing.newMessierList.get(0).get(testing.rightAscensionIndex),
                    testing.newMessierList.get(0).get(testing.declinationIndex),
                    testing.newMessierList.get(1).get(testing.rightAscensionIndex),
                    testing.newMessierList.get(1).get(testing.declinationIndex)));
            ArrayList<ArrayList<Object>> openClusters= testing.getOpenClusters();
            System.out.println(openClusters.get(2));
            String tempString = MessierObject.toString(
                    openClusters.get(2),testing.originalMessierList);
            MessierObject testing2 = new MessierObject(tempString);
            System.out.println(testing2.internalNewToString());
            System.out.println(testing2.internalOriginalToString());
            System.out.println(testing.getOriginalMessierObject(67));
            System.out.println(testing.getNewMessierObject(67));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
