# PROJECT PLAN

#### Team: voogasalad_PrintStackTrace

---

### Game Engine and Player
**Team Members**
* Josh Medway
* Diane Lin
* Erie Seong Ho Han
* Kyle Hong

**Expectations for Mid-Point Demo**
* Game Engine
    * Create game engine from `Game` object passed from Data
        * All game items(defenders, attackers, towers, initial starting conditions of the game, etc) must be created from the `game` object.
    * Udpates game from frame to frame
        * Returns a list of `Command` objects to `Player` in order for `Player` to update the GUI as the `GameEngine` updates the game state.
    * One working tower defense game
    * Changing level
        * The game engine will re-initialize its game items when the level has to change
        * The game engine will detect when the level needs to change
* Player
    * Have a default GUI for all Tower Defense games
    * Pause the game
    * Save the game
    * Change the speed of the game
    * Allow users to visually see in real time where they can and cannot place certain game objects on the map
        * Green range of operation will show up on the map if item is placable
        * Red **x** will be shown if item is not placeable on certain map region
        * User will be able to drag the game object and place it where the range of operation will be green.
    * Reload previously saved games
        * All game attributes must be the same. i.e. if an arrow game item was in mid-flight, the game must reload where that specific arrow was.
    * Dynamically and correctly update the status of the game.
    * A button that will tell the voogasalad.player the winning condition and losing condition of the game

**Extensions**
* Allow users to save the game (in its current state) and load it again later to continue play
* Different soundtracks for different levels will be played while the game is being played
* Reference tab
    * When the user puts the mouse over the image of a defender, the user will be able to see a brief status of that defender.
* User can change the language by clicking a button
* Live game Editing
* Animation effects 
    * ex) When the bomb explodes, there will be an animation effect

---
### Data
**Team Members**
* Justin Havas

**Expectations for Midtpoint Demo**
* Saving data from authoring environment.
    * Data will be saved into an XML file corresponding to the current project.
* Loading a previously saved project within the authoring environment.
    * Data will be read from the XML file and return necessary data structures to the authoring environment to allow for further development.

**Extensions**
* Live Game Editing
    * authors should be able to play their game, pause it, add a new element to the game, and then continue playing from where he left off.
* Allow an author to save individual game items into multiple projects
    * Ex. authors should be allowed to create a defender and reuse them in another game without having to create it again.
___

### Game Authoring Environment
**Team Members**
* Junyu Liang 
* Goh Khian Wei

**Expectations for Mid-point Demo**
* Game
    * create new game
    * load and change saved game
    * save game design
* Level
    * customize game map, rule set, game objects (defenders, obstacles, attackers), and layout
    * customise way attackers appear on map (completely random, preset sequence etc.)
    * save and load previously saved level
* Game Objects (defenders, obstacles, attackers)
    * create and customize configuration for each kind of object
    * save and load previously saved object
* Game Map
    * create game map
    * customize size of game map
    * save and load previously saved map
* Rule set
    * define win & lose condition, budget, multiplayer etc.
    * save and load previously saved rule set

* Media
    * user should be able to load media file to change the appearance of game object

    * have some built-in icons for the user to choose from

**Extensions**
* Layout
    * customize layout of the game display
* Soundtrack
    * support user to upload soundtracks and attach to different levels
    * support user to connect specific sounds to specific events (e.g. Explosion sound when projectile collides with an attacker)
* Smart Game Map Builder
    * *Smarter* implementation of map builder, where users are able to trace paths along the map to build a map, without having to specify whether each cell is a *horizontal*, *vertical*, *crossroads* cell etc. Program will be able to automatically generate the appropriate cell based on the user's path drawing.
* Authoring Environment Language
    * Users will be able to, from a single toggle, change the language of the entire authoring environment
* Asset Store
    * Users will be able to upload and download assets (full games, defenders, attackers etc.) from a single asset store accessible by all game authoring environments

**Individual Allocation of Work**
Khian Wei:
* Authoring Controller
* View
* Map Builder
* Smart Game Map Builder
* Authoring Environment Language

Joey:
* Object Builder
* Model
* Rule Set
* Save and Load
* Layout 
* Soundtrack
* Asset Store

___

### Networking
**Team Members**
* Ben Lawrence

**Expectations for Midpoint Demo**
* Can choose multiplayer in authoring environment
    * 1, 2, or 4 voogasalad.player games
    * Players will compete against each other in online games (online games will not be supported by midpoint)
* Chat room option in authoring environment
    * Can thoose theme and style of chat room
* Chat room
    * All players in game will be able to send text messages to each other
* All user to create joinable game
    * Option to choose multiplayer
    * Multiplayer will not be available by midpoint demo but ability to create and join games will be so show off chat feature

**Extensions**
* Online Multiplayer
    * Two players playing each other online
    * Players attack each other by sending attackers to other's screen
    * 2 - 4 voogasalad.player compatibility
    * Last survivor wins the game