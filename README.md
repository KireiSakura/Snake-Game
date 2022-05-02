# Snake-Game

<b>OpenJDK version:</b> "11.0.8" 2020-07-14

<b>Gradle version:</b> "6.6.1"

<b>About the Game - The Snake Game:</b>

A snake will move on a garden (in the form of a grid) to look for mushrooms to eat. The snake will keep moving forward. Use Left and Right arrow keys to change direction. Help the snake eat the mushrooms and survive till the timer runs out in Level 1 and Level 2. In Level 3, eat until you die and lose. You lose if the snake's head hits the wall of the garden or if the snake eats itself. The snake will increase its speed in every level. Every time the snake eats a mushroom, the snake's body grows. The score incrreases by 1, every time you eat a mushroom.

<b>Controls while playing the game:</b>

- Use Left and Right Arrow keys to move the snake.
- While playing the game, you can switch between levels. Press 1 to go to Level 1, press 2 to go to Level 2, press 3 to go Level 3(if you press the key for a level on which you are currently on, then the command will be ignored. For instance if you are on level 2, and you press 2 then the command will be ignored).
- Press P to pause the game, (if you change the level when the game is paused, the level will be changed, but the game will remain paused.)
- Press R to restart the game anytime between the game
- Press Q to quit the game and view highscore

<b>How to score in the game:</b>

Once you start the game, the score will be increased by 1 everytime you eat a fruit. Even if you switch between different levels before the game is over, the score does not get reset. For instance, if you switch back to level 1 from level 3, the score does not reset and will continue to increase from the score you had when you switched from level 3 to level 1.The score resets only when the game is over or if you Restart the game(by pressing R).

<b>About the countdown timer in Level 1 and Level 2:</b>

If you are on level 1 or level 2, and you do not die till the timer counts from thirty down to zero, you win the level and proceed to the next level. There will be fruits in total in level 1 and 10 fruits in total in level 2. If you are unable to eat all the fruits in level 1 or level 2, before the time runs out, then you miss the chance to eat the remaining fruits of that level and you move on to the next level. If you eat all the fruits of level 1 or level 2, before the time runs out, then whatever the remaining time is of that level, you move on to the next level.

<b>Fruits Eaten Count:</b>

In every level, the count of the number of fruits eaten in that level wil be displayed.

<b>About Level 3: </b>

In level 3, there are 15 fruits in total, but if the snake eats all the 15 fruits and the game is not over, then the fruits will continue to appear(so you can eat more than 15 fruits in level 3). Level 3 is over only if either the snake eats itself or it hits the wall.
