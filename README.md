# Snake
 Simple Snake game in Java

# Instructions
 This game compiles in OpenJDK 16 using the "Main" class as the starting point.
 I have also included a compiled jar file.

# Choices Made
### Event Listener
 This game uses a self-made event listener to re-shift focus.
 This is only because I wanted to try implementing one.
 It is only used to request focus on the main game frame - which can be done using a parent lookup function instead.
### Updating GUI
 This game draws all the objects (snake blocks/food) rather than updating the screen/sections.
 This is a very costly way of updating the graphics, and I would not have done so again with my current experience.
### General Issues
  - There are a lot of inefficiencies and unneeded lines of code.
  - There are also a lot of repeated segments of code, especially in the move section.
  - "timer" the game's main timer's delay gets reduced in game, but not reset on restart.
  - No error checking/robustness on pieces of code. For example: top10 file existence, or filled under/over 10 slots.