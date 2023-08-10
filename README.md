# My Personal Project

[//]: # (An example of text with **bold** and *italic* fonts.)

## Project Proposal:
The goal of this project is to make a video game that is 
fun and easy to play. My current idea is to make a 2D 
endless shooter, which include the following elements:

- different types of enemies
- money (from defeating enemies and finding treasure 
chests)
- experience (from defeating enemies, increases basic 
stats)
- different items (can be stored or removed from inventory)
- shop (to buy/upgrade items)
- scaling (the longer you play, the harder the game 
gets)
- endless maze as the map
- player movement within the map

The last 2 elements may not be feasible to implement. 
If this is the case, I will remove player movement
and level generation, and focus mainly on the aiming
aspect of the game. 

This project is of interest to me because I've only ever
played video games, and never had the chance to make one.
This project is the perfect opportunity to give it a go. 
I'm hoping that the project turns out to be fun, so anyone
can enjoy it.

## User Stories

 - As a user, I want to be able to add/remove an item to my inventory
 - As a user, I want to be able to view my inventory
 - As a user, I want to be able to aim and shoot in different 
directions
 - As a user, I want to be able to view my current stats (attack, health, etc)
 - As a user, I want to be able to increase my stats by equipping items and defeating enemies
 - As a user, I want the difficulty of the game to increase along with my current level
 - As a user, I want to be able to buy and upgrade my items using the shop
 - As a user, I want to be able to fight different enemies
 - As a user, I want to be able to save the game state
 - As a user, I want to be able to load the game state back


## Instructions For Grader:
- You can add items (weapons and buffs) to your inventory by defeating enemies 
and catching the items that drop from them.
- You can remove weapons and buffs by clicking on the corresponding button to the right of the
game.
- You can locate the visual component by running the game
- You can save the state of the game by pressing Save Game
- You can reload the state of the game by pressing Load Game
- To move, press arrow keys, to shoot, press space bar.


## Phase 4: Task 2
Wed Aug 09 17:12:11 PDT 2023
Buff: Increase Bonus Attack was added to the inventory
Wed Aug 09 17:12:23 PDT 2023
Buff: Increase Bonus Attack was added to the inventory
Wed Aug 09 17:12:54 PDT 2023
Buff: Increase Movement Speed was added to the inventory
Wed Aug 09 17:13:03 PDT 2023
Weapon: Medium Gun was added to the inventory
Wed Aug 09 17:13:08 PDT 2023
Buff: Increase Movement Speed was removed from the inventory
Wed Aug 09 17:13:08 PDT 2023
Buff: Increase Bonus Attack was removed from the inventory
Wed Aug 09 17:13:09 PDT 2023
Weapon: Medium Gun was removed from the inventory

## Phase 4: Task 3
Overall I am pretty satisfied with the design of my project. There are a few 
things I would change if I had more time though. The Game class does most of
in determining what type of Bullet that each enemy and weapon should shoot.
Each Enemy and Weapon has a size, and the Bullet has a size as well. This
is how the Game class determines which bullet to generate. However, it would
make more sense to initialize each Enemy and Weapon with a pre-specified 
Bullet, so the Game class is less crowded. Actually, in a previous iteration
of this project, the Weapon class did have a field for Bullet. However, I
hadn't added the Enemy class yet, and for some reason thought that after 
adding the Enemy class, my old design would no longer work. Given more time,
I would definitely try to fix this. 

Another thing I would change would be to merge the InfoPanel and StatPanel
classes. Actually, these display really similar things. The only reason 
they're separate is because I couldn't figure out how to display 2 separate
JPanels in the same class. I wanted to have a top panel and a bottom panel, 
but the only way I could think of to achieve this was to create 2 separate 
classes. There's also 2 random JButtons for loading and saving the game,
located in the InfoPanel. These should really be moved to the 
InteractivePanel class, as that's where the rest of the JButtons are. 
I only put them in the InfoPanel class because I couldn't 
figure out how to position them nicely otherwise. 

This project was definitely pretty fun, but also pretty challenging. This 
was my first time creating such a large scale project, and also my first time
using java. With summer session being half the length of winter session, time
was also a big factor. Phase 3 was definitely the toughest part. I pulled my
first all-nighter ever to finish it. I underestimated the difficulty of 
implementing a graphical user interface, and I also hadn't implemented most
of the game in Phase 2, so it took me a very long time to finish Phase 3. 
There were some components I originally wanted to add to the game that I 
didn't have time to implement, such as a shop to upgrade items and infinite scaling
on the game difficulty, but overall I'm pretty happy with the final product. 

