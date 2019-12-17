# USE_CASES.md

### Team: voogasalad_PrintStackTrace

#### Game Authoring Environment Use Cases (Khian Wei and Joey)
1. Game author defines a new defender type. The author can configure the defender's image, size, cost. Also the author can choose the projectile type, life time, scope and damage of the projectile.
2. Game author can decide whether the game is a multiplayer game. The author defines how many players is in the multiplayer game. Each voogasalad.player would be both a defender for her own tower, and can add attackers to others' towers.
3. Game author can load media files for the game from user local files (e.g. Images representing various defenders, projectiles, attackers, soundtrack for each level, sound effects during game events etc.)
4. Game author will be able to modify any object created. e.g. Redraw the attacker path on a particular map, change the projectile that a particular defender fires, or change the image used to represent a particular attacker. 
5. Game author will be able to load and save specific game elements from file. (e.g. Load and save maps, prebuilt defenders, prebuilt attackers etc.)
6. Game authors will be able to have access to created game elements from a variety of views (e.g. When an author creates a new `Defender` in the 'Defender Creator' tab, the change will immediately be reflected in the 'Level Builder' tab, where the user can then select the new `Defender` as a possible defender option in a particular level)
7. Game authors will be able to load entire game projects from file and modify them in any way (i.e. All assets, rules, objects required to launch a game will be initialised from the single load operation. All assets can then be modified as though they were created in that session and can then be saved as a separate game file)
8. Game author will be able to graphically create a map, rather than through a text-based UI. (e.g. User can paint cell types directly onto a grid to specify the type of cell each grid cell is)
9. Game author will be able to choose a created game object graphically, and delete that object. (i.e. No objects created are final, all objects can be deleted after creation.)
10. Game author can define abstract rules for the entire game. (e.g. Win condition for levels, losing condition for levels etc.)
11. Game author can design the flow of the attackers in a specific level. She will have several options to define the flow. First, she can decide the ratio of each kind of attackers to include, and the flow would be a randomly generated sequence based on the ratio. Second, she can decide the time intervals and the amount of attackers within that interval for each attacker type.
12. Game author can put together a game by selecting and ordering multiple levels, and the set of rules to be applied to the game.

### Game Engine and Player Use Cases
13. Game Player can place element on screen by selecting a game element from UI.
14. Game Player can not place defender element on path.
15. Game Player cannot place attackers on non-path.
16. Game Player can drag elements on screen to be placed on the map.
17. Game Player can see if Element can be added on specific area of screen. 
18. Game Player can save current game to be played later as well as loading previous games.
19. The user can expand the window and the game window will adjust accordingly to fit the new window size
20. Game Engine move elements and adjust the location of objects as the game is updating
21. Selecting a game file will initialize the UI and Game Engine automatically.
22. When new element is formed inside game engine, the screen will show them at the right location.
23. After the game engine updates, the front end will get the location of objects from game engine and update the screen.
24. Game engine can create objects specific to each game from information obtained from *Data*.
25. If the author used different images for each tiles to create the map, that will show up when the UI for the game voogasalad.player is initialized.
26. The user can pause the game at any point during game play and then resume the game.
27. Game voogasalad.player can dynamically set the speed of the wave as well as slowing it down.
28. When an object is destroyed, a user will see a special animation occur which signifies the destruction of the object.
29. When a defender can be placed, the range that the defender covers will show up on the screen.
30. If the author sets background music, the music will be played while the game is being played.
31. The game engine updates the location of attackers/defenders and which then allows the voogasalad.player to update the visual location. 
32. When a user loses the game, some sort of lose screen will come up, and the action will be paused
33. When a user wins a level, the level will increase and a new map will be instantianized automatically.
34. When the game is running an attacker will automatically be placed at the start of the map, to attack the tower. The amount of time it takes to spawn a new attacker will be stored in the engine, and spawn an attacker when necessary.
35. The defenders with 'smart projectile' will automatically shoot at an attacker that is furthest along its course to the tower.

### Data API
36. An author decides to save the work he's done.
37. An author decides to open up a previously saved game within the authoring environment.
38. An author wants to open a new file to save their work into.
39. An author decides to save the current work he has completed and continue authoring a game.
40. An author decides to play his game, pauses it, adds a new element to the game, and then continues playing from where he left off.

### Networking
41. Designer wants to add the ability for multiplayer games to include a chat room. He can do this by adding the chat room feature in the game engine and choosing the style of that chat room.
42. A user wants to create a joinable multiplayer game. The user selects multiplayer and creates a new game then waits for another voogasalad.player to join.
43. Four users playing online multiplayer want to send messages to one another. They can do this via the chat room enabled by the author which connects them over a shared network.
44. A user wants to join an already existing multiplayer game. The user can start the game, go to multiplayer, then click join game. The user can then search for another voogasalad.player's game to join.
45. Player 2 wants to make the level harder for Player 1. So he buys more attacker objects and sends them to Player 1's map where Player 1 will have to defend against the new attackers.























