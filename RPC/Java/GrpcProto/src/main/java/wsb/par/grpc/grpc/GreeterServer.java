package wsb.par.grpc.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import wsb.par.grpc.grpc.GreeterGrpc.GreeterImplBase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GreeterServer {
    private static final Logger logger = Logger.getLogger(GreeterServer.class.getName());

    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                GreeterServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final GreeterServer server = new GreeterServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class GreeterImpl extends GreeterImplBase {

        private static final Logger requestLogger = Logger.getLogger(GreeterImpl.class.getName());

        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            String name = request.getName();
            requestLogger.info("Received SayHello request from: " + name);
            HelloReply reply = HelloReply.newBuilder().setMessage("Witaj, " + name + "! (z Javy)").build();
            responseObserver.onNext(reply);
            requestLogger.info("Sending SayHello response: " + reply.getMessage());
            responseObserver.onCompleted();
        }
    }
}