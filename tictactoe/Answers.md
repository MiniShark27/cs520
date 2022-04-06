# Answers to CS 520 HW 2

### Note
Worked with Victor Santana on brainstorming (no shared code or answer text though)

### Best Practice Violations Fixed:
1. Made access modifiers more restrictive (public -> none (aka internal to the package) -> private)
1. Implemented RowBlockModel.equals()
    - Note: This was one of my violations, but when refactoring the move method, there was no need for it anymore. I left it in to show I put work in and made it, but it should be deleted since it is no longer used 
1. Refactored move to use loops instead of if statements
1. Abstracted commonly used code in the move method to its own methods (see checkDiagonal, checkRow, checkColumn)

Other things fixed:
1. One thing I outlined in my original violations was to split the move method using MVC. This was already done with the View being the GUI, the Model being the classes in the model package, and the Controller being the classes in the controller package. I didn't realize I could just leave a comment saying it was already done as one of my violations, but if one of the above violations has an issue feel free to reference this instead.
1. move now doesn't allow a player to overwrite a previous player's move (once it has been made)
1. The setIsLegalMove was never called to set the variable to false, this was added to the move method while refactoring
1. Cleaned up the import to prevent duplicates
1. Ran my auto-formatting tool to make the code look better
     - I know this is not best practice since it makes the commits look bad (e.g. every line has spacing changes). But, it was hard to read the controller file with the code not having consistent indenting (had one line indented 4 units, then the next in the same scope indented 2...) so to make the changes more easily I ran it. Apologies for the ugly git logs.
1. Added the test to the src folder (so that it could recognize the rest of the code inside it in vscode)
    - Added a .vscode file if you want to run the tests in vscode
    - Added the JUnit jar file to the ant build file class path to prevent errors when building
1. And more... (I forgot some stuff)

## Design Patterns

### Composite Design Pattern:
- RowGameBoardView 
  - Fields
    - board
    - gameModel
  - Method Splitting:
    - Would include the code from the constructor to set up the board and generate the JButtons for the board
    - Would also include the updateBlock method
    - Update method updates a block on the board
- RowGameStatusView
  - Fields
    - playerTurn (The JTextArea that displays whose turn it is)
  - Method splitting
    - Would include the code from the constructor to set up the status (specifically the playerTurn JTextArea) 
    - Also the messages variable in the constructor, and anything related to it
    - Update method updates the status text
- RowGameGui 
  - Fields
    - gui
    - reset (assumed the 'status' was just the bottom JTextArea, so this wouldn't fit in there)
  - Method Splitting
    - The Reset Button setup in the constructor
    - The JFrame setup in the constructor
    - update method does nothing (nothing to update)
    - In constructor, would set up the board and status views, and add them to the JFrame

### Observer Design Pattern:
- Observable: RowBlockModel.contents
  - Changes when the observer notifies it
- Observer: javax.swing.JButton (The JButton that corresponds to the block)
  - On click updates the contents of the RowBlockModel
- Update Method: RowGameGUI.updateBlock()
  - Updates the contents of the model when the JButton is clicked (via the move method and the setContents method)
    - Didn't choose move since it does more than update the contents
    - Didn't choose setContents since updateBlock is the only method that calls it and is called earlier (specific enough)