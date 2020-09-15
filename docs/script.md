# HYPERSELENIUM SCRIPT
Automatically generated from the package net.hydrogen2oxygen.hyperselenium.commands
## assertText

## assertTextNotExist

## clearText

## clearText

## click

## clickName

## clickTagContainingText

## close

## dropdown
Select an option from a dropdown box.

    dropdown <id> <visibleText>

## enter

## if
The **if** is a statement, not a real command.

    if <commandOne> then <commandTwo ... with params>
    
This statement has a single-line syntax.

If first command was successfull, then execute the second command.
You can also negate the result of the result of the first command with an exclamation mark. 


    if !<commandOne> then <commandTwo ... with params>
    
You can use this kind of statement when you need to dynamically react to certain conditions.

    if !assertText "User John exist" then run createUserJohn

## textByName

## text

## open

## run
Run a script. It shares the same context with the parent script.

    run <script-file.md>
    run <script-file>

## screenshot

## wait

## waitForJQuery

## waitForJavaScript

