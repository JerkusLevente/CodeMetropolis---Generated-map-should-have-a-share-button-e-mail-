# CodeMetropolis---Generated-map-should-have-a-share-button-e-mail
Implementation of the issue: https://github.com/codemetropolis/CodeMetropolis/issues/302. Which was an assignment of a course at university.

# Description of the issue
Egy menüt kell elhelyezni a generált térkép ablakában ahol a felhasználó megtudja osztani azt. A programnak meg kell nyitnia az alapértelmezett email klienst és az üzenethez csatolni a térképről készült kép fájlt.

There should be a menu bar where the user can share the generated map.
The program should open the default e-mail client on the computer and set the picture of the map as an attachment.

# User Documentation
Follow the steps listed in the readme of CodeMetropolis up to step five. Before entering "mvn clean package" command, replace the file named "CityMapGUI.java" found in "sources\toolchain\placing\src\main\java\codemetropolis\toolchain\placing" with the one found in the "Implementation of Issue #302" folder.

Miután futtatjuk a placing.jar-t a "-m" kapcsolóval megjelenik a térkép.
Az ablak felső széli sávjában elérhető egy gomb ami megnyitja az alapértelmezett email klienst és
megosztja a generált térkép egy képfájlként, ami png kiterjesztésű.
