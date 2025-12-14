from websocket import  create_connection

ws = create_connection("ws://localhost:8888/websocket")
print("Sending 'Hello, World'...")
ws.send('{"event":"xxx","some_data":"Hello, World"}')
print("Sent")
print("Receiving...")
result = ws.recv()
print("Received '%s'" % result)
ws.close()
