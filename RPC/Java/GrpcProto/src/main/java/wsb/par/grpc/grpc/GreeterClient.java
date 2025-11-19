package wsb.par.grpc.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GreeterClient {
    private static final Logger logger = Logger.getLogger(GreeterClient.class.getName());

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    /** Construct client connecting to gRPC server at {@code host:port}. */
    public GreeterClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (SSL/TLS). For the example we disable TLS to make set-up easier.
                .usePlaintext()
                .build());
    }

    /** Construct client for accessing RouteGuide server using the existing channel. */
    GreeterClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Say hello to server. */
    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
            logger.info("Greeting: " + response.getMessage());
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "RPC failed:", e);
        }
    }

    /**
     * Greet server. If provided, the first element of {@code args} will be the name
     * to use in the greeting. The second argument is the server.
     */
    public static void main(String[] args) throws Exception {
        String user = "World";
        String target = "localhost:50051";
        if (args.length > 0) {
            user = args[0];
        }
        if (args.length > 1) {
            target = args[1];
        }

        GreeterClient client = new GreeterClient(target.split(":")[0], Integer.parseInt(target.split(":")[1]));
        try {
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }
}