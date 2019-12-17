# DESIGN PLAN

### Team Name: voogasalad_PrintStackTrace
### Team Members
* Ben Lawrence, bcl19
* Josh Medway, jwm58
* Erie Seong Ho Han, eh174
* Kyle Hone, sh394
* Diane Lin, dl257
* Justin Havas, jah135
* Junyu Liang, jl751
* Goh Khian Wei, kg189
____
## Introduction
Our team aims to make a game authoring environment that enables authors to flexibly create Tower Defense games by creating maps, and defining their own custom elements in the game. Players will be able to play this game by selecting the appropriate file. The program will be bken down into authoring environemnt, game data, game engine, and the voogasalad.player. The implementations of the authoring environment will be hidden from the game engine and the voogasalad.player, and vice versa. For communication of these two parts, observer pattern will be used to enable the authoring environment to save data in the game data in such a way that the data stored will be used to initialize the game engine and create an environment for the players to play the exact game that the author designed. In addition to that, our project will support networking, social center, and live game editing, which will make our game authoring environment a unique one. 

____
## Overview

This program is organized into large groups: the authoring environment, the game data, the game engine, and the voogasalad.player.

The authoring environment is in charge of creating the components that will be used in the actual game. The authoring environment allows for an author to create a game in a modular approach, creating levels, attackers, defenders, maps, and other game objects. These game objects can be created from scratch or loaded from stored files and then optionally modified. 

When a complete game which the author is satisfied with is complete, he can *save* the game. The *GameProperties* will then be sent to the game data builder, where the *GameDataBuilder* will attempt to build a complete game object that can be serialised into an XML file. If the *GameDataBuilder* is unable to build a game from the information sent over, for whatever reason, an error will be thrown and displayed to the user for appropriate handling. 



For a voogasalad.player to run a particular game, the game data will be deserialised from the XML file. This game will be sent to the game engine, which will call game data to deserialize the game parameters. From here the game engine will create the parameters for the initial level, and show on the front end that the game is ready to be played. The user can then play the game normally, being sure add game elements and try and win, or go to the next level. From here certain elements may be made possible, such as live editing (saving an existing game and editing it in the authoring environment), or multiplayer (two users playing in the same game).

When a user decides to stop playing the game, there will be a way for the current state of the game engine to be stored so that it can be played at a later time. This will be achieved by working with the game data class to serialize the current state of the game engine. This whole system can be used constantly to play, edit, revise, and create games. 

The general flow of information will be

![](https://i.imgur.com/y7CfwcB.png)


## User Interface

### Game Authoring Environment Wireframe
#### Welcome page
![](https://i.imgur.com/6vBoNGg.png)
#### Game tab
![](https://i.imgur.com/JMyEQIo.png)
#### Level tab
![](https://i.imgur.com/rwN5VdP.png)
#### Map tab
![](https://i.imgur.com/zAiX3c9.png)
#### Defender Tab
![](https://i.imgur.com/NqZXNV0.png)

### Game Engine and Player Wireframe
#### Game Play
![](https://i.imgur.com/aG1nrUC.jpg)
#### Losing scene
![](https://i.imgur.com/UCrsf7S.jpg)
#### Winning scene
![](https://i.imgur.com/UqS6BZB.jpg)

### Player Environment
### Player Main Menu
![](https://i.imgur.com/maTeLbK.png)
### General Player Environment
![](https://i.imgur.com/HEfVDXm.png)
### Chat Room and Multiplayer
![](https://i.imgur.com/tkpypt5.jpg)

____

## Design Details


### Game Engine and Player
![](https://imgur.com/q9Yhgqu.png)

### Game Data

The Game Data module is used as a mechanism of communication between the authoring environment and the engine/voogasalad.player by storing data authored within the authoring environment into XML files. Game Data module will only be called by the authoring environment when the author decides to save their progress. When this happens, the authoring environment will call the `saveData()` within the `Data` API which will begin the process of adding game objects into the game engine being built within the `Data` API. The addition of each type of game object into the game engine will be handled by the `DataHandler` Interface which will be used to convert frontend game objects into backend game objects that can be added to the game engine. At the end of the `saveData()` method, the game engine is then serialized into an XML file.

Once a game engine has been saved to an XML file, the engine/voogasalad.player will call the `retrieveEngine()` method in order to retrieve a game engine object that can be used as the initial engine for the voogasalad.player to use in order to execute the game. Swicthing between different files will be done by calling the `setFilePath()` method.

All XML files will be serialized using the XStream package demonstrated within the spike_voogasalad lab on November 7th, 2019. An example XML file can be found in `data/example/example.xml`.

### Game Authoring Environment
![](https://i.imgur.com/DpkWd2G.jpg)

The Game Authoring Environment (GAE) will employ the MVC model with multiple controllers, each for a type of configuration, such as defenders, level, rules, soundtrack etc. 

The GAE will be launched from a `AuthoringController` class which extends `Application`. This class will initialise all `Controller` objects as defined in a properties file and attach the necessary observers to each `Controller` as requested, from the same properties file. All `Controller` objects will share the same `Model` object. In other words, all `Controller` objects will have access to all data stored by all other `Controller`. This is not considered as a lack of encapsulation as data is not designed to be specific to each `Controller`. For instance, there could be a `Controller` which allows for parameter-based creation of maps, while another `Controller` would allow for a graphics-based creation of maps. This allows for greater extendability in the variety of `Controller`, as only one `Model` is required regardless of amount of `Controller` objects.

Each `Controller` will initialise its own graphical interface, sending this `Node` to the `View` for display. Each `Controller` is responsible for handling all user-interactive options in the graphical interface. Primarily, the main event requiring handling is the creation or modification of game objects, such as attackers, defenders etc. Any object created or modified is stored in the `Model`. Objects stored in the `Model` are stored as `Object` class objects with a `String` tag to identify the specific class it belongs to (e.g. `MapObject`, `AttackerObject` etc.). This thus means that the entire GAE does not know exactly what types of objects can be created. This is entirely determined through reflection.

`Controller` objects are able to communicate with other `Controller` objects using the Observer Design Pattern. This is the ideal design pattern for our use case as we 1) can theoretically require any number of observers and 2) may require every observer to have a different response to a change in internal state that the initiating object should not be responsibel for. Thus, every `Controller` object will implement the necessary *`SpecificControllerObserver`* interfaces as necessary. As previously mentioned, attaching of observers is done during initialisation. This thus means that we expect all `Controller` objects to be initialised simultaneously at program launch. While every game authoring environment can have different `Controller` objects, each instance of the authoring environment will have a fixed variety of `Controller` objects available. During the extension phase, we intend to explore making new `Controller` objects, perhaps by importing, while the authoring environment is already instantiated.

The implementation of the `Controller` through Observer Design Pattern and the referencing of created objects through reflections collectively mean that the extension of the GAE to feature different `Controller` objects and different creatable objects will be extremely doable. This is a significant extendability feature of the current design.

A central `AuthoringController` class aware of all `Controller` objects also makes calling changes to all `Controller` objects easy, such as when loading a file from Game Data, which will require all `Controller` objects to update their views, or when changing the language of the authoring environment, which will also require all `Controller` objects to update their views. 

When the author has created a game that they are satisfied with, the `SaveAndLoad` module will package all the information the author has created and send this information to the Game Data Builder. 

If an author wishes to load a previously authored game into the game authoring environment, he just has to retrieve the data from the Game Data Builder, store it in the `Model`, and call all `Controller` objects to update their state using the new data stored in the `Model`.


### Networking
![](https://i.imgur.com/mOwfGZS.jpg)
The networking module will be used by the voogasalad.player to display chat messages and to interact with other players in a multiplayer game. A singleton design structure is used for a router because each game should only ever have one router communicating with the other players. The router sends and receives `Message` objects. A `Message` has a header and a body. The body contains whatever `Object` contant the user wishes to send. The header contains the means that sent it. This is used in the internal "routing table" held by the `Router` so that it can know which program to send a received message to. For example, if the router receives a `Message` with a body `"Hello World"` and a header `src.networking.Chat` then it knows this was sent by a chat and should be passed on to this game's chat.

A `NetworkProperty` object will also be present in the model held by the `AuthoringController` if the author decides that the game will have multiplayer functionality and will not be present if the author decides against that.

____

## Example games

Describe three example games from your genre in detail that differ significantly. Clearly identify how the functional differences in these games is supported by your design and enabled by your authoring environment. Use these examples to help clarify the abstractions in your design.

1. **Bloons Tower Defense** - In our Bloons Tower Defenes, players attempt to prevent balloons(aka bloons) from destroying towers at the end of a set of course by placing defenders and obstacles along the course that can pop the ballons in a variety ways. Some towers have "smart" balloons can stall the balloons and give other defenders more time to pop more. Money can be gained by popping the ballons or completing levels that can be spent building new defenders or placing obstacles on the course.
2. **Clash Royale** - Clash Royale is a tower rush video game which pits players in games featuring two or four players (1v1 or 2v2) in which the objective is to destroy the most opposing towers, with the destruction of the "King's Tower" being an instantaneous win. This game differs from BTD and PvZ since it concentrates on the players sending attackers, instead of defenders, to take out the oppositions towers. The defenders are already set and cannot be modified. This design is supported in our authoring environment as we plan for authors to create and place requirements on whether attackers and/or defenders can be created from players.
3. **Plants vs. Zombies** - In Plants vs. Zombies, players take the role of a homeowner in the midst of a zombie apocalypse. To defend their home from zombies, some which have unique abilities, the voogasalad.player uses plants that can fire projectiles at the advancing zombies or have other effects on the approaching hoard. Players must plan defenses in multiple "lanes" across the home's lawn and should a zombie make it to the house on any lane, the game is over. 
____
## Design Considerations
### Discussions
There was a lot of discussion on how to save the game and who would be saving the game. The first solution was as the designer entered in information in the authoring environment, the environment would call `Data.addElement(String Object, Map<String, String> properties)`. The data API would then save each object along with its associated data in an XML file. The debate behind this implementation was whether or not this was too general. What happens if a key value pair cannot be described by a single string? What how does the data API know what type of object to load when the game engine asks for it?

This discussion had not yet been resolved when Professor Duvall introduced xstream to save classes. We then debated whether or not there should be properties classes and how those would be implemented. At first it was thought that the authoring environment would have its own backend which created a `GameProperties` class that was sent to the data API. However, in order to preserve that the authoring environment didn't require backend functionality, this was moved to the data API.

It still needs to be fully hashed out how the properties will be stored. In general, it was decided that the authoring environment will send all user-created data to the game data builder exactly once, instead of as a stream. The game data builder will then decide exactly how to package all of the data into a format which the game engine can then convert into an actual game. The authoring environment can receive this data back in exactly the same format it was sent to rebuild an authoring environment state from file. 

### Assumptions
It is assumed that the author will only be able to place elements within an NxM grid for the map, consisting of rectangular cells. This also implies that any particular map in a level will be a fixed size for the duration of the level. There will not be any dynamic resizing of maps. This is a necessary assumption based on the design decision that maps are not procedurally generated but individually designed by the authors.

The Game Authoring Environment also assumes that a fixed number of `Controller` objects are available for any particular instance of the GAE.



