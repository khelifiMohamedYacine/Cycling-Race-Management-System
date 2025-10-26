<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg" alt="Java Logo" width="100" height="100"/>
</p>

# ECM1410 – Object-Oriented Programming Coursework  
## Cycling Race Management System  

### Module Information
- **Module Code**: ECM1410  
- **Module Title**: Object-Oriented Programming  
- **Academic Year**: 2024/25  
- **Author**: Khelifi Mohamed Yacine  

---

## 🏁 Overview
This project implements a **Cycling Race Management System** written in **Jaca** as part of the ECM1410 module at the **University of Exeter**.  
The goal is to design and develop a modular, object-oriented system capable of managing race events, teams, and cyclists efficiently.

The coursework demonstrates:
- Object-oriented design and implementation
- Inheritance, polymorphism, and encapsulation
- File I/O operations for race data management
- Basic statistical analysis and race simulation

---

## 📁 Project Structure
```

Cycling-Race-Management-System/
│
├── src/
│   ├── main/
│   │   ├── Race.java
│   │   ├── Team.java
│   │   ├── Cyclist.java
│   │   ├── Stage.java
│   │   └── Utilities.java
│   │
│   └── test/
│       ├── RaceTest.java
│       ├── TeamTest.java
│       └── CyclistTest.java
│
├── data/
│   ├── teams.txt
│   ├── cyclists.txt
│   └── results.txt
│
└── README.md

````
---

## ⚙️ Features

### Core Functionalities
- Manage **cyclists**, **teams**, and **race events**
- Record and analyze **race results**
- Handle **file input/output** for persistent data storage
- Provide **menu-driven** or **command-line** user interaction

### OOP Principles Used
- **Encapsulation** – classes for data protection  
- **Inheritance** – shared attributes among race entities  
- **Polymorphism** – flexible behavior for different cyclist types  
- **Abstraction** – simplified interface for race management  

### Example Classes
- `Cyclist` – Represents a racer with attributes like name, nationality, and race stats  
- `Team` – Manages a collection of cyclists  
- `Race` – Handles race setup, result calculation, and statistics  
- `Utilities` – Provides helper functions for input validation and formatting  

---

## 🧠 Example Usage
### Example Interaction
```
=== Cycling Race Management System ===
1. Add Cyclist
2. Add Team
3. Record Race Result
4. View Standings
5. Save and Exit
Enter choice: 3
Enter race name: Tour de Exeter
Enter number of participants: 10
...
Race results saved successfully!
```

---

## 🧩 Technologies Used
- **Language**: C++17  
- **Tools**: GCC / g++ compiler  
- **Build System**: Makefile  
- **Testing**: Manual + Unit tests (optional)  

---

## 📊 Learning Outcomes
Through this coursework, the following learning objectives are demonstrated:
- Design and implementation of modular **object-oriented C++ programs**  
- Use of **inheritance, polymorphism**, and **abstraction**  
- Development of reusable and maintainable software structures  
- Application of **file handling** and **data persistence** in C++  

---

## 🧾 Academic Declaration

### GenAI Usage
- ☑️ I have not used any GenAI tools in preparing this coursework  
- ☑️ All non-original material has been clearly identified  

### Marking Criteria
- ✅ Correctness and robustness of program  
- ✅ Quality of object-oriented design  
- ✅ Code clarity and documentation  
- ✅ Efficiency and maintainability  


---

## 👤 Author Information
**Khelifi Mohamed Yacine**  
BSc Computer Science, University of Exeter  
Email: [khelifiyacine36@gmail.com](mailto:khelifiyacine36@gmail.com)

---

*This project demonstrates proficiency in C++ programming and object-oriented design as part of the ECM1410 coursework requirements.*
