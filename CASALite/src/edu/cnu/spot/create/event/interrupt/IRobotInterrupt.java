package edu.cnu.spot.create.event.interrupt;

import edu.cnu.casaLite.message.MapMessage;

public interface IRobotInterrupt {
	boolean triggers();
	boolean happenned();
	void    addKeyValue(MapMessage message);

}
