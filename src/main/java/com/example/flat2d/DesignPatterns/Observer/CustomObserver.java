package com.example.flat2d.DesignPatterns.Observer;

import com.almasb.fxgl.entity.Entity;
import com.example.flat2d.Misc.EntityType;
import javafx.event.Event;

/**  Created this observer design pattern interface for the purpose of playing audio on different
*   circumstances like death, spawn and many more
*/
public interface CustomObserver {

    void onNotify(Event event);
}
