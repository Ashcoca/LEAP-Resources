# Usage

Prompt files are reusable Markdown templates saved in your workspace or user profile that instruct GitHub Copilot or AI agents on how to write and refactor Java code.


### Step 1: Use the "Java General" or "Java Spring" Profile

VS Code includes built-in profile templates tailored for Java:

1. Open the Command Palette (Ctrl+Shift+P / Cmd+Shift+P).
2. Type and select Profiles: Create Profile....
3. Choose a Java General or Java Spring template.
4. Install recommended Java extensions (such as Extension Pack for Java) and configure the workspace.


### Step 2: Create a Java AI Prompt File

```md
---
description: Java development guidelines and strict coding standards
mode: chat
---

You are an expert Java software engineer and architect. 
Follow these rules when writing or refactoring Java code:
- Use modern Java features (Java 17/21+ idioms, records, pattern matching, var where appropriate).
- Follow clean code, SOLID principles, and proper exception handling.
- Always include unit tests using JUnit 5 and Mockito when writing services or business logic.
- Do not automatically stage or commit git changes without approval.
- Break down complex tasks into a step-by-step checklist before executing.
```