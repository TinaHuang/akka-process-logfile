package akka.yt.processlogfile;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.*;

import akka.yt.processlogfile.Aggregator.StartFile;
import akka.yt.processlogfile.Aggregator.Line;
import akka.yt.processlogfile.Aggregator.EndFile;

public class FileParser extends AbstractActor {
  private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  static public Props props(ActorRef aggregatorActor) {
    return Props.create(FileParser.class, () -> new FileParser(aggregatorActor));
  }

  // message
  static public class Parse {
    public final String fileName;
    
    public Parse(String fileName) {
      this.fileName = fileName;
    }
  }

  //private final String message;
  private final ActorRef aggregatorActor;

  public FileParser(ActorRef aggregatorActor) {
    this.aggregatorActor = aggregatorActor;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
      .match(Parse.class, parse -> {
        aggregatorActor.tell(new StartFile(parse.fileName), getSelf());
        aggregatorActor.tell(new Line(parse.fileName), getSelf());
        aggregatorActor.tell(new EndFile(parse.fileName), getSelf());
      })
      .build();
  }
}