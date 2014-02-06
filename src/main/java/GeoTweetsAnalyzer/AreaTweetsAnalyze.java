package GeoTweetsAnalyzer;

import au.com.bytecode.opencsv.CSVWriter;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

/**
 * @author Toby "Jiajun" Li
 */
public class AreaTweetsAnalyze {

    public static void main(String[] args) throws IOException{
        File f = new File("output-romney.csv");
        final FileWriter fileWriter = new FileWriter(f);
        final CSVWriter csvWriter = new CSVWriter(fileWriter, ',');


        FilterQuery fq = new FilterQuery();
        double lat1 = 44.75;
        double lon1 = -93.65;
        double lat2 = 45.13;
        double lon2 = -92.90;
        double[][] bb= {{lon1,lat1}, {lon2,lat2}};
        fq.locations(bb);

        TwitterStream twitterStream = (new TwitterStreamFactory()).getInstance();
        twitterStream.setOAuthConsumer("X4rtxPZzNeG29rfiLCuvwg","VNTblgaM7PmynwsHcr1J4ng6dIokivpHQzJlUOFU");
        twitterStream.setOAuthAccessToken(new AccessToken("419996850-WVINnb5iYJdqrcg2rX5zDjKP6xWLTHzpQ9D9YYuJ","baJ2iXiyard4lS11BueUFVv83xQQ9A1irBwdBowRhdrBv"));

        //StatusListener listener = new StatusAdapter();
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status){
                String[] entries = new String[6];
                try{
                    entries[0] = status.getUser().getScreenName();
                    entries[1] = status.getUser().getLocation();
                    entries[2] = status.getText();
                    if(status.getGeoLocation() != null)
                        entries[3] = status.getGeoLocation().toString();
                    entries[4] = status.getCreatedAt().toString();
                    System.out.println(entries[0]);
                    if(entries[1].length()!=0)
                        System.out.println(entries[1]);

                    if(status.getGeoLocation() != null)
                        System.out.println(status.getGeoLocation().toString());
                    else
                        System.out.println("<null geolocation>");
                    System.out.println(status.getText() + "\n");

                    csvWriter.writeNext(entries);
                    csvWriter.flush();
                }
                catch (IOException e){

                }



            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                //System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                //ex.printStackTrace();
            }
        };
        FilterQuery fq2 = new FilterQuery();
        String keywords[] = {"romney"};
        //String[] lang = {""};
        //fq2.language(lang);
        fq2.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq2);

    }



}
