# Q-learning-grid

This is an implementation of reinforcemnt learning developed in java. It generates a NxM grid with a agent, target block, trap block
and user defigned walls. Then the agent makes its way through the grid and around the walls untill it finds either the target or 
trap block. At which point it will go back to the beginning and repeat. It will do so untill it findes the optimal path to the target
block.

# Usage

The entry point of the program is within the wizard.java file in the run package. In this file there are a number of variables that you 
can edit to change the environment. When this file is run a new window will open with a graphical representation of the environment.
In this environment therre will be a blue, green and red box which represents the agent, target and trap box respectivley. At this point 
the user is able to click any of the white boxes to turn them in to walls. Then the user can click the done button to confirm the wall 
placement. Then each white box will be populated with 4 doubles. Each representing the cost of the agent moving North, South, East and West
whe the agent is in the state. The user can then click the start button to start the agent navigating the environment.

# Agent movement

The agent will initially randomly move around the environment with each move from state 1 to 2 updating the double assigned to the 
direction of the movement in state 1. The colour surrounding this numberwill also change. The closer it is to 1 the greener it will become 
and the more it approaches -1  the more red it will become. When the agent reaches a target or trap box it will start over but the model 
will remember the past actions and therefore will slowly find the target faster with each iteration.
