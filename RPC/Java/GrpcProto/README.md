protoc -I=src/main/proto --java_out=src/main/java --grpc-java_out=src/main/java --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java-1.71.0-windows-x86_64.exe src/main/proto/helloworld.proto

protoc -I=ser/main/proto --java_out=src/main/java mydata.proto    