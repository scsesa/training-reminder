package org.scsesa.hazmat.training;

import com.twilio.twiml.TwiML;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;

/**
 * Created by jcreasy on 11/28/18.
 */
public class Reminder {
    TwiML getReminderApplicationWithMessage(String message) {
        return new VoiceResponse.Builder()
                .gather(new Gather.Builder().build())
                .say(new Say.Builder(message).build())
                .build();
    }
}
