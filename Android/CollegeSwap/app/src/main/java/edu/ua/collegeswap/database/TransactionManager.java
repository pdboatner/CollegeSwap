package edu.ua.collegeswap.database;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import edu.ua.collegeswap.viewModel.Listing;
import edu.ua.collegeswap.viewModel.Sublease;
import edu.ua.collegeswap.viewModel.Textbook;
import edu.ua.collegeswap.viewModel.Ticket;

/**
 * Created by Patrick on 4/22/2015.
 */
public class TransactionManager {

    /**
     * Send this offer to the server
     *
     * @param buyerUsername
     * @param listing
     * @param offerText     whatever the user typed into the input box. Just send this to the other
     *                      user via email.
     */
    public void makeOffer(String buyerUsername, Listing listing, String offerText) {

        int id = listing.getID();
        String sellerUsername = listing.getPosterAccount().getName();
        String emailText = "Subject: CollegeSwap: "+buyerUsername+" made an offer on your listing\n";
        emailText+=buyerUsername+" said: "+offerText+"\n\n";
        emailText+=buyerUsername+" wants to buy your ";
        if (listing instanceof Ticket) {
            emailText+="ticket.\n";
            emailText+="game: "+((Ticket)listing).getGame()+"\n";
        } else if (listing instanceof Textbook) {
            emailText+="textbook.\n";
            emailText+="subject: "+((Textbook)listing).getSubjectAndNumber()+"\n";
        } else if (listing instanceof Sublease) {
            emailText+="sublease.\n";
            emailText+="location: "+((Sublease)listing).getLocation()+"\n";
        }
        emailText+="listing title: "+listing.getTitle()+"\n";
        emailText+="listing price: "+listing.getAskingPriceDollars()+"\n";
        emailText+= "\ncontact: "+buyerUsername+"@crimson.ua.edu\n";
        final String email = sellerUsername+"@crimson.ua.edu";
        final String text = emailText;
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://www.bama.ua.edu/~cppopovich/CS495/mailUser.php");
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("email", email));
                pairs.add(new BasicNameValuePair("text", text));
                try {
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    client.execute(post);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
