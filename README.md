# Process-Mining

Problem Statement

# Part 1 : Build Process Model
Given an event log from building permit applications of Dutch municipalities. There are many different activities present, denoted by both codes (attribute concept:name) and labels, both in Dutch (attribute taskNameNL) and in English (at-tribute taskNameEN). The cases in the log contain information on the main application as well as objection procedures in various stages. Furthermore, information is available about the resource that carried out the task and on the cost of the applic- ation (attribute SUMleges).
The goal is to understand and analyze the log by answering the following questions:
1. What are the roles of the people involved in the various stages of the process?
2. What are the possible points for improvement on the organizational structure for the municipality?
3. Some of the procedures will be outsourced from 2019, i.e. they will be removed from the process and the applicant needs to have these activities performed by an external party before submitting the application. What will be the effect of this on the organizational structures in the municipality?
4. When looking at the throughput times, what comes to your attention?

# Part 2 : Predictive Analytics
Inputs: 
• The original event log
• The process model mined in Part 1

During the execution of process models, it can become interesting to predict future behavior of the currently running process instance. For example: „How long will it take until this instance finishes?“, „Can any delays be expected?“. In this case study, we will focus on task prediction, i.e., which task will probably happen next?
To this aim, your task is to build a prediction system (predictor). The general input for your predictor is a currently running (i.e. incomplete) process instance (i.e., an incomplete trace).

Goals:
1. Implement the above predictor with N-grams algorithm in Java.
2. Visualize the outcome in petri nets.
3. Implement the predictor as a ProM Plugin.
