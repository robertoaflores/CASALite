package edu.cnu.spot.message;

import edu.cnu.casaLite.message.IMessageProcessor;
import edu.cnu.casaLite.message.MapMessage;

public interface ISPOTMessageProcessor extends IMessageProcessor {
	boolean handleMessage(MapMessage message, String performative, MapMessage content, String command);
}
