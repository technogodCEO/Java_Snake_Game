# Java_Snake_Game

A simple game inspired by snake made in java with swing.

## NOTE ON COORDINATE SYSTEM

The coordinate system of this app will be in 20x20 chunks starting at y = 140 and x = 20. This is because each square of the snake or the apple is 20x20 pixels. The padding on each side is for borders, the title, or any other text and or not directly game related functions

## MEMORY WARNING

This program is very inefficient with memory as it never destroys any index in either ArrayList unless the game is reset. This should not be an issue as each index is very small (4 bytes each) and there will only be an issue on most computers if someone manages to play the game for more than an hour without resetting. Just keep this in mind. I am in the process of finding a more memory efficient solution for managing the list as well.
