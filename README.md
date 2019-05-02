# Overview

![UML](https://github.com/sierrasallee/Project5/blob/master/UML.png) </br>
![App](https://github.com/sierrasallee/Project5/blob/master/fullApp.png) </br>

For Project 5, I began by storing all local variables within the Main class. I did this to allow me to reference these objects in different methods. This helped me to sort my actions by methods. I used the setupVariables method to instantiate the variables from Mesonet.txt . This method also instantiated necessary variables before they were used. I used the setupGUI method to instantiate the components and add them to their corresponding panel. I tried to sort what I was doing by their functionality. The method getHammingDistance compared two MesoStation objects and calculate the int hamming distance between them. I added this functionality to help me sort all MesoStations by their hamming distance from the current MesoStation. I could've included this in the MesoStation class, but I didn't. It would increase code readability if I had chosen to implement the method in the MesoStation class. The method reloadTree repopulated the tree view whenever necessary.


# Input Hamming Distance Functionality

![](https://github.com/sierrasallee/Project5/blob/master/input.png) </br>

The app allows users to input the hamming distance in two ways. One way is to input the integer hamming distance in the textfield. The other option is to move the slider. Each option updates the other visually. For instance, inputting the text 1 will change the slider to the value of 1; sliding to the value of 4 updates the text value to 4. One downside is that the slider does not show the tick marks like it should. I couldn't figure that one out.


# Show Other Stations Functionality

![](https://github.com/sierrasallee/Project5/blob/master/showStation.png) </br>

The compare with combobox tells the app which MesoStation to compare with. This MesoStation will be the one upon which all other MesoStations will be compared. Clicking the "Show Station" button shows all stations that have the given hamming distance inputted above. These stations are displayed in the text area. The text area is not editable for the user. The combobox initially loads with the option "NULL" displayed. The option stays available after the user adds a valid MesoStation to compare with. This is due to the fact that the app wouldn't let me load an unpopulated combobox.


# Compare Hamming Distances Functionality

![](https://github.com/sierrasallee/Project5/blob/master/compare.png) </br>
The "Calculate HD" button populates the text fields bellow with the count of how many MesoStations in memory have the respective hamming distance as compared to the selected MesoStation. These text fields are not editable for the user.


# Adding Station Functionality

![](https://github.com/sierrasallee/Project5/blob/master/addStation.png) </br>


# Tree View

![](https://github.com/sierrasallee/Project5/blob/master/tree.png) </br>

The tree view allows users to see all the MesoStations in order of their hamming distance in a tree view format. This view is loaded by the reloadTree method.


# Issues
