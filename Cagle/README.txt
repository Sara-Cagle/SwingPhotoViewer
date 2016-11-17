Sara Cagle
scagle6@gatech.edu
GTID#: 902939934

Homework 4

Java version 8

Gestures: Please see attached Gestures.PDF if the following text is not clear.
Tag 1 (Spring): A down-carrot, starting from the upper left, move south east, then north east. Like this: \/
Tag 2 (Summer): An up-carrot, starting from the lower left, move north east, then south west. Like this: /\
Tag 3 (Fall): A horizontal zig-zag. Starting from upper left, move south east, then north east, then south east again. Like this: \/\
Tag 4 (Winter): Inverse horizontal zig-zag to Tag 3. Start from lower left, move north east, then south east, then north east again. Like this; /\/
Selection loop: Start at the middle-top of the loop and move in a counter-clockwise direction (starting going left). Return the middle top of the loop by ensuring your last movements were left-ward.
Deletion pigtail: Same shape as the selection loop, but once you've finished the loop, move in a south east direction.
Move left: A left-carrot, less than sign. Start at the top right, then go south west, and then south east. Like this: <
Move right: A right-carrot, greater than sign. Start at top left, then go south east, then south west. Like this: >

Extra credit:
I use a glass pane so you can draw your gestures where ever you want in the program.
This also means that you can gesture on the front or back of your picture--everything but selecting with a loop will be the same.
My left/right movement and deletion works on Photo View and Split View, but also Grid View.
I update statuses when gestures are found.
Program runs on both Mac, with CTRL-click as a right-click due to their single-button touch pad. Also works with normal right-click.
I save tags to each image. So if you navigate away from an image and come back later, the tags you set previously will still be there.
Alerts user about incorrect usage of loop as selection function. (If a user draws a loop on the unflipped side of the photo).
Asks for confirmation if the user wants to delete the photo or annotation.
