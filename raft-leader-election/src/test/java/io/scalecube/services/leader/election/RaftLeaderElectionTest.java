package io.scalecube.services.leader.election;

import io.scalecube.services.Microservices;
import io.scalecube.services.Reflect;
import io.scalecube.services.leader.election.example.GreetingService;
import io.scalecube.services.leader.election.example.GreetingServiceImpl;
import io.scalecube.services.leader.election.state.State;

import org.junit.Test;

import java.util.function.Consumer;

public class RaftLeaderElectionTest {

  @Test
  public void test() throws InterruptedException {

    Microservices seed = Microservices.builder().startAwait();

    GreetingServiceImpl leaderElection1 = new GreetingServiceImpl(new Config());
    GreetingServiceImpl leaderElection2 = new GreetingServiceImpl(new Config());
    GreetingServiceImpl leaderElection3 = new GreetingServiceImpl(new Config());

    Microservices node1 =
        Microservices.builder().seeds(seed.cluster().address()).services(leaderElection1).startAwait();
    Microservices node2 =
        Microservices.builder().seeds(seed.cluster().address()).services(leaderElection2).startAwait();
    Microservices node3 =
        Microservices.builder().seeds(seed.cluster().address()).services(leaderElection3).startAwait();

    // wait for leader to be elected.
    Thread.sleep(20000);

    System.out.println("leaderElection1 leader:" + leaderElection1.leaderId());
    System.out.println("leaderElection2 leader:" + leaderElection2.leaderId());
    System.out.println("leaderElection3 leader:" + leaderElection3.leaderId());

    System.out.println("DONE");
    Thread.currentThread().join();
  }

  
}
