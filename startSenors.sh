#Open rpm sensor in a new terminal
ttab -t 'RPM Sensor' 'java -jar rpm-sensor/target/rpm-sensor-0.0.2.jar'

ttab -t 'RPM Sensor 2' 'java -jar rpm-sensor-sec/target/rpm-sensor-sec-0.0.2.jar'

ttab -t 'RPM Receiver' 'java -jar RPMReceiver/target/RPMReceiver-0.0.2.jar'

#Open temp sensor in a new terminal
ttab -t 'Tempature Sensor' 'java -jar temp-sensor/target/temp-sensor-0.0.2.jar'

ttab -t 'Tempature Sensor 2' 'java -jar temp-sensor-sec/target/temp-sensor-sec-0.0.2.jar'

ttab -t 'Tempature Receiver' 'java -jar TempReceiver/target/TempReceiver-0.0.2.jar'