package org.scsesa.hazmat.training;

import com.twilio.Twilio;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.TwiML;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import org.scsesa.hazmat.common.TwilioAccount;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;

//import lombok.extern.java.Log;

/**
 * @author Jonathan Creasy (jonathan.creasy@scsesa.org)
 */
//@Log
@Path("training")
public class ReminderEndpoints {
    @GET
    @Path("reminders/{message}")
    @Produces(MediaType.APPLICATION_XML)
    public String getApplication(@PathParam("message") String message) {
        Reminder reminder = new Reminder();
        TwiML twiML = reminder.getReminderApplicationWithMessage(message);
//        log.info("Sending XML");
        return twiML.toXml();
    }

    @GET
    @Path("reminders")
    @Produces(MediaType.APPLICATION_XML)
    public String getApplication() {
        TwiML twiml = new VoiceResponse.Builder()
                .gather(
                        new Gather.Builder()
                                .numDigits(1)
                                .say(new Say.Builder("This is a reminder that you have Hazmat training tomorrow. Press 1 if you will be attending, Press 2 if you will no tbe attending. Press 3 if you think Jason can go saw a log").build())
                                .build()
                )
                .build();
        return twiml.toXml();
    }

    @GET
    @Path("dial")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendReminder() {
        Twilio.init(TwilioAccount.getAccountSid(), TwilioAccount.getAuthToken());

        try {
            Call call = Call.creator(new PhoneNumber("+16186719282"), new PhoneNumber("+16184144185"),
                    new URI("https://hazmat-team-224005.appspot.com/training/reminders")).create();
            return call.getSid();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

    }

    @GET
    @Path("dial/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendReminder(@PathParam("number") String number) {
        Twilio.init(TwilioAccount.getAccountSid(), TwilioAccount.getAuthToken());

        try {
            Call call = Call.creator(new PhoneNumber("+1" + number), new PhoneNumber("+16184144185"),
                    new URI("https://hazmat-team-224005.appspot.com/training/reminders"))
                    .setMethod(HttpMethod.GET)
                    .setStatusCallback(URI.create("https://hazmat-team-224005.appspot.com/training/result"))
                    .setStatusCallbackMethod(HttpMethod.POST)
                    .create();
            return call.getSid();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        //"APef03c04f03a6337dbb8d4cee42abe47a";
    }
}
