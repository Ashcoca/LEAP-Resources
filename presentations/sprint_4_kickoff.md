# WEEK 3 KICK OFF
 
 
## Presentation Requirements: Technical vs. Stakeholder-Focused

| # | Original Technical Requirement | Non-Technical Equivalent |
|---|---|---|
| 1 | A walkthrough of your API contract and how the implementation satisfies it. | **What the system does, and proof it does it.** Explain in plain language what the service lets people or other systems do (e.g., submit a trade, check its status, cancel it). Show a live demo or screenshots proving each promised capability works. Skip endpoint names, payloads, and status codes unless asked. |
| 2 | The trade's state model, and whether it matched the order states in code. | **The life of a trade, from start to finish.** Show a simple visual of the stages a trade moves through (e.g., submitted → approved → settled, or cancelled) and what triggers each step. State plainly whether the system behaves exactly that way, and if not, what's different and why it matters. |
| 3 | Your team's decision on authentication this week (open, stubbed, or built for real) and why. | **Who can use the system, and how we're protecting it right now.** State whether access is currently open to anyone, uses a temporary placeholder, or is fully secured. Explain why the team chose that for this stage and what risk, if any, remains until it's finished. |
| 4 | One place an earlier decision caused friction here, and how you handled it. *(You are allowed to change things!)* | **A lesson learned, and what we changed because of it.** Describe one earlier choice that made this week's work harder, the problem it caused in everyday terms (delays, rework, confusion), and how the team fixed or adjusted it. Present the change of course as good judgment. |
| 5 | How you're enforcing BR-09 in this service, and the query that shows it held. | **How we make sure Rule BR-09 is never broken, and the evidence.** Restate BR-09 as a business rule in one plain sentence. Explain at a high level how the system prevents violations, then show the result as a simple statement or visual (e.g., "0 of 1,240 trades broke this rule") rather than the query itself. |
| 6 | An updated backlog. | **What's done, what's next, and what we need.** Summarize what was completed, the top few upcoming priorities and why they matter, and any decisions or support needed from stakeholders. Group items by business value instead of listing raw tickets. |

> **Tip for presenters:** Every section should answer *"So what?"* for someone who will never read the code. If a technical term can't be avoided, define it in one sentence.