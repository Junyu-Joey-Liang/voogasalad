USE_CASES_EXTRA

Team: voogasalad_PrintStackTrace

1. Designer can save and load individual objects to local files. For example, the user can save a projectile with all its configurations set, and load it in another game design. She can also share it with other designers so that they can use the designed projectile in their game as well.

2. For each game object (projectile, defender, attacher, obstacle), there should be a default list of appearances that the designer can choose from, so that they don’t need to always try to find their own images. 

3. When the designer has error inputs (such as putting in letters in a number field, or negative number for positive-only attributes), the authoring environment should prompt the user, and give guidance for the user to change the inputs to make sure that the game is well designed

4.  In the Game tab, the user should be able to choose a theme sound track for the game (can be empty if the designer does not want any sound). The designer can also choose individual sound tracks for each level, which will be played when each level appears.

5. There should be a way from the designer using game authoring environment to click a button to choose and launch a game to play. For example, after designing and saving a game, the designer would not need to close game authoring environment, navigate to game engine to launch the game. The designer should be able to just click on a button in game authoring environment, choose a game to run, and the game engine will run that game.

6. The level builder should dynamically update its contents based on the author's selections. For instance, if the author selects frequency based attacker spawns, then settings for specific spawn times of attackers should not be visible to the author to improve usability.

7. Game authoring environment should be able to support multiple languages easily. User should be able to click a select language button and have all view labels set to a different language.

8. Game authoring environment should be able to support multiple view themes, such as a normal mode and a dark mode, from the click of a button.

9. Map builder should have checks for map building. For instance, warnings can display problematic features with the current map, such as 'No end point created', 'Orphaned path', 'Spawn point path not connected to end point' etc.

10. Level builder should check for completed level before storing. For instance, storing should not be allowed if no map has been selected, or if a scoring rule is not selected.


Player (Eric, Kyle)

11. There is a default screen with a tab that shows the available defender, and the user can click a defender on that tab and add that defender on the screen. Clicking a defender will change the color of the defender-tile so that the user can recognize.

12. A user can choose a game in the game library that were generated from the authoring environmnet. Each element in the game select screen will show the image, title and description of the game. 

13. The program will automatically extract the game icon, title, and description from data and show a list of available games in the default file directory to the user.

14. Clicking the setting button will automatically stop the game loop. Clicking the resume button will let the user return to the game, and the game loop will start again.

15. Clicking the restart button will restart the entire game without quitting the application. All the HUD displays and progress will be refresehd accordingly.

16. Clicking the load game button will let the user select a saved game file. The dispalys and progess of the saved file will show up accordingly.

17. HUD displays(scores, lives, levels, etc) will be updated dynamically as the game is being played. The highest score will be stored.

18. When the user drags the defender in the defenderTab, the GameMainWindow class recognizes that action, and enables the engine-frontend to fetch that information, enabling the engine-frontend to send the corresponding BackendCommand object to the ExternalEngine.

19. When the user tries to load a game, the player will load a screen that shows all the saved files of that game, and the user will be able to choose from that. 

20. A player will be able to change the speed of the game by clicking a button on the defender tab. 

21. A player will be able make an account so that the player can write reviews and ratings that will be stored.

22. 

Networking

23. The designer can choose to make the social center part of the player environment or as a second window. This is done by selecting which one he wants on the authoring environment. If the designer says make it part of the game then `getNode()` is called by the player. If he says to make it a separate window then `displayInSeparateWindow()` is called.

24. The user can sign up for an account by opening the social center and adding a username and password. This will then be stored in a remote server for later logging in.

25. The user can change his avatar by clicking the 'change avatar' button in his profile page. This will allow him to select an image which will be saved for future use.

26. The user can create and end chats outside of a multiplayer game by clicking the 'start chat' button. This will cause a chat to open and the user to create a chat with a new id. Other players will then be able to join the chat.

27. The user's high score will be updated when the user is logged in. This will allow the server to keep track of the most up to date high score. The clientInterface will send the game's final score to the server and if the score is higher than the current high score for that game then the high score will be updated.

28. If the user tries to make an account that already exists then the server will send back an error message and the social center will inform the user that the chosen username has already been taken via a pop up box.

29. A chat with more than two people can be created by each user typing in the user's username and the chat's chat id. Players can leave the chat by clicking "exit chat". The chat will continue to run as long as there are users in it.

GameEngine

30. Game engine will update the location of an attacker at each frame and will take into consideration the attacker's speed.

31. The game engine will check whether a player has enough currency to purchase a specific game item.

32. The game engine will dynamically assign a path for an attacker to travel on (the assignment of the path will be random and none of the paths will be hardcoded). The attacker will also not be allowed to travel in loops. 

33. The game engine will add more defenses other than projectiles and shooting type defenders. We will add a field effect where the attackers can be slowed down. 

34. The game engine will add a missile that will follow a specific attacker. The missile will continue to try to aim for the attacker until it hits the attacker or the missile dies.

35. The game engine will allow different types of collisions/explosions to be shown based on what objects collide.

36. The game engine will allow a previously saved game with new changes made to it by the author even after the game as been saved to be reloaded to be reloaded with the new features.

37. The game engine will hold and send to player the current health of a specific tower/defender or attacker

38. The game engine will be able to accomdate the number of attackers released per second based off what the author inputs (ie the author can set a specific number of attackers per second or they can just set a frequency and the game engine will generate the number of attackers to match the frequency set by the author).

39. The game engine will accomodate live game editing (if an author decides to change the image of an attacker in the middle of a game, the game engine will automatically update and send the necessary commands to player to update as well).

40. The game engine will be able to update backend values based on what happens in the player environment (i.e. if a player decides to change the position of a tower or if the player buys health for a tower, it will be updated in the back end as well).


Data

41. Allow authors to load in elements they made in other games, such as attackers, defenders, projectiles, etc., into another game. 

42. The game data should be able to notify the authoring environment in some way that live game editing is taking place. 

43. Allow authors the ability to save individual elements (attackers, defenders, projectiles, etc.) to their own files.

44. Allow authors the ability to load individual element files (attackers, defenders, projectiles, etc.) into the authoring environment.

45. Authors should be able to store sounds being played during the game.