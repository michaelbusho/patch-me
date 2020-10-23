#For Macos/Linux, create similar script for windows
#Compile All

find . -name "*.java" > sources.txt
javac @sources.txt

#Open sender in a new terminal
ttab -t 'Sender' 'java tempSensor.sender.TempSensor'

#Open Receiver in a new terminal
ttab -t 'Receiver' 'java tempSensor.receiver.BeatChecker'