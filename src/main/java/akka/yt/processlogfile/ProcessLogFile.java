package akka.yt.processlogfile;

import java.io.IOException;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import akka.yt.processlogfile.FileScanner.Scan;

public class ProcessLogFile {
  public static void main(String[] args){

    System.out.println( "It's my first akka project!" );
    // create ActorSystem
    final ActorSystem system = ActorSystem.create("process-log-file");
    
    try{
      // create actors
      final ActorRef aggregatorActor = system.actorOf(Aggregator.props(), "aggregatorActor");
      final ActorRef fileParserActor = system.actorOf(FileParser.props(aggregatorActor), "fileParserActor");
      final ActorRef fileScannerActor = system.actorOf(FileScanner.props(fileParserActor), "fileScannerActor");
            
      // send message
      fileScannerActor.tell(new Scan(), ActorRef.noSender());

      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();

    } catch (IOException ioe) {
    } finally {
      system.terminate();
    }
  }
}
