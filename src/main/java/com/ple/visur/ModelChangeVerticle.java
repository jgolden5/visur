package com.ple.visur;

import io.vertx.core.json.*;
import io.vertx.rxjava3.core.eventbus.Message;

import java.util.Arrays;

import static com.ple.visur.ModelStringArrayKey.*;
import static com.ple.visur.ModelIntKey.*;


public class ModelChangeVerticle extends AbstractVisurVerticle {

  View view;

  @Override
  public void start() {
    editorModelService.putContentX(0);
    editorModelService.putVirtualX(0);
    editorModelService.putContentY(0);
    editorModelService.putAtEndOfLine(false);
//    final String initialContentLines = "Hello" +
//      "\nWho's there?? Goodbye world." +
//      "\nmore words";
    final String initialContentLines = "31 Yea, I would that ye would come forth and harden not your hearts any longer; for behold, now is the time and the day of your salvation; and therefore, if ye will repent and harden not your hearts, immediately shall the great plan of redemption be brought about unto you." +
      "\n\t32 For behold, this life is the time for men to prepare to meet God; yea, behold the day of this life is the day for men to perform their labors." +
      "\n\t\t33 And now, as I said unto you before, as ye have had so many witnesses, therefore, I beseech of you that ye do not procrastinate the day of your repentance until the end; for after this day of life, which is given us to prepare for eternity, behold, if we do not improve our time while in this life, then cometh the night of darkness wherein there can be no labor performed." +
      "\n 34 Ye cannot say, when ye are brought to that awful crisis, that I will repent, that I will return to my God. Nay, ye cannot say this; for that same spirit which doth possess your bodies at the time that ye go out of this life, that same spirit will have power to possess your body in that eternal world.";
    dataModelService.putContentLines(initialContentLines.split("\n"));
    editorModelService.putContentLines(dataModelService.getContentLines());
    vertx.eventBus().consumer(BusEvent.modelChange.name(), event -> {
      handleChange(event);
    });
  }

  public void handleChange(Message<Object> event) {
    if(view == null) {
      view = new View();
      view.contentLineX = 0;
      view.contentLineY = 0;
    } else {
      view.contentLineX = editorModelService.getContentX();
      view.contentLineY = editorModelService.getContentY();
    }
    view.contentLines = editorModelService.getContentLines();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    output.put(contentLineX.name(), view.contentLineX);
    output.put(contentLineY.name(), view.contentLineY);
    output.put("canvasX", editorModelService.getCanvasX());
    output.put("canvasY", editorModelService.getCanvasY());
    output.put(contentLines.name(), new JsonArray(Arrays.asList(view.contentLines)));
    return output;
  }

}
