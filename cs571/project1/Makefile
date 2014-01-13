jcmp = /usr/bin/javac
jarbld = /usr/bin/jar
jarfile = Project1.jar
outdir = ./
cleandir = ./project1
srcdir = ./src/project1/

#build java classes
all:	Main.class Schedule.class ScheduleEntry.class BridgeController.class Vehicle.class VehicleInformation.class

clean:	
	rm -rf $(cleandir)
	rm $(jarfile)

jar:	Main.class Schedule.class ScheduleEntry.class BridgeController.class VehicleInformation.class Vehicle.class
	$(jarbld) cvfm $(jarfile) manifest.mf $(cleandir)

Main.class:	ScheduleEntry.class Schedule.class Vehicle.class VehicleInformation.class BridgeController.class
	$(jcmp) $(srcdir)Main.java -d $(outdir)

Schedule.class:	ScheduleEntry.class
	$(jcmp) $(srcdir)Schedule.java -d $(outdir)

ScheduleEntry.class:
	$(jcmp) $(srcdir)ScheduleEntry.java -d $(outdir)

BridgeController.class:	VehicleInformation.class
	$(jcmp) $(srcdir)BridgeController.java -d $(outdir)

Vehicle.class:	VehicleInformation.class BridgeController.class
	$(jcmp) $(srcdir)Vehicle.java -d $(outdir)

VehicleInformation.class:	
	$(jcmp) $(srcdir)VehicleInformation.java -d $(outdir)