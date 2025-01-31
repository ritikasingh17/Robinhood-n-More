# Robinhood-n-More

This project implements four CPU scheduling algorithms in Java to simulate task management in an operating system. The scheduling algorithms include First-Come, First-Served (FCFS), Priority Scheduling, Round-Robin (RR), and Multilevel Feedback Queue (MFQ). Instead of threads, the system uses CPU bursts, each represented by a CPUBurst object. The project supports key scheduling methods such as addTask(), done(), and hasTasks() to manage the task queue, handle preemption, and ensure that tasks are scheduled efficiently based on the chosen algorithm.
