import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class MessierProgram {
    public static void main(String[] args) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("messier.txt"));
            MessierCatalogue testing = new MessierCatalogue(reader);
            ArrayList<ArrayList<Object>> appMagnitudeOrder =
                    testing.getApparentMagnitudeOrder();
            ArrayList<Object> mostDistantGlobularCluster =
                    testing.getMostDistantGlobularCluster();
            ArrayList<Object> sagLowestDeclination =
                    testing.getSagittariusLowestDeclination();
            ArrayList<Object> m45ClosestObject =
                    testing.getObjectClosestToM45();
            //all queries are returned as array lists but with these
            //methods they can be turned into strings
            System.out.println(
                    "Catalogue in ascending apparent magnitude (new values):\n" +
                    testing.queryListToNewString(appMagnitudeOrder) + "\n");
            System.out.println(
                    "Catalogue in ascending apparent magnitude (original values):\n" +
                    testing.queryListToOriginalString(appMagnitudeOrder) + "\n");
            System.out.println(
                    "Average apparent magnitude of the open clusters: " +
                    testing.getOpenClustersAppMagnitude()+ "\n");
            System.out.println(
                    "Most distant globular cluster (original values):\n" +
                    testing.queryListEntryToOriginalString(mostDistantGlobularCluster)+ "\n");
            System.out.println(
                    "Most distant globular cluster (new values):\n" +
                    testing.queryListEntryToNewString(mostDistantGlobularCluster)+ "\n");
            System.out.println(
                    "Sagittarius constellation object with the lowest declination (original values):\n" +
                    testing.queryListEntryToOriginalString(sagLowestDeclination)+ "\n");
            System.out.println(
                    "Sagittarius constellation object with the lowest declination (new values):\n " +
                    testing.queryListEntryToNewString(sagLowestDeclination)+ "\n");
            System.out.println(
                    "Object closest to M45 (original values):\n " +
                    testing.queryListEntryToOriginalString(m45ClosestObject) + "\n");
            System.out.println(
                    "Object closest to M45 (new values):\n " +
                    testing.queryListEntryToNewString(m45ClosestObject) + "\n");
            System.out.println(
                    "Entire catalogue (original values):\n " +
                    testing.getOriginalMessierListToString()+ "\n");
            System.out.println(
                    "Entire catalogue (new values):\n" +
                    testing.getNewMessierListToString()+ "\n");
//            ArrayList<ArrayList<Object>> nebulas= testing.getDiffuseNebulas();
//            System.out.println(nebulas);
//            System.out.println(testing.queryListToOriginalString(nebulas));
//            System.out.println(nebulas.get(0));
//            System.out.println(testing.getNewMessierObject(102));
//            System.out.println(testing.getOriginalMessierObject(102));
//            System.out.println(testing.getSupernovaRemnants());
//            System.out.println(testing.getGlobularClusters());
//            System.out.println(testing.getDiffuseNebulas());
//            System.out.println(testing.getSpiralGalaxies());
//            System.out.println(testing.getOpenClusters());
//            System.out.println(testing.getDwarfEllipticalGalaxies());
//            System.out.println(testing.getAsterisms());
//            System.out.println(testing.getEllipticalGalaxies());
//            System.out.println(testing.getHIIClusters());
//            System.out.println(testing.getHIINebulas());
//            System.out.println(testing.getLenticularGalaxies());
//            System.out.println(testing.getMilkyWayStarClouds());
//            System.out.println(testing.getNebulaWithClusters());
//            System.out.println(testing.getPlanetaryNebulas());
//            System.out.println(testing.getStarburstGalaxies());
//            System.out.println(testing.getStarSystems());
//            System.out.println(testing.getTaurus());
//            System.out.println(testing.getSagittarius());

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
