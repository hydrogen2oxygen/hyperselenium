The **if** is a statement, not a real command.

    if <commandOne> then <commandTwo ... with params>
    
This statement has a single-line syntax.

If first command was successfull, then execute the second command.
You can also negate the result of the result of the first command with an exclamation mark. 


    if !<commandOne> then <commandTwo ... with params>
    
You can use this kind of statement when you need to dynamically react to certain conditions.

    if !assertText "User John exist" then run createUserJohn
