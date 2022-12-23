### Framework development guidelines

- All modules configurations should be set in root module
- A module that needs configuration should throw when loading without it
- Each module should be able to use a different data access layer
(not meaning it should use it)

### General development guidelines
- Avoid dumb dependencies (no leftPad).
- Look for community modules before deciding to create one.
- Don't roll you own auth (that's dumb).
- Make a monolith (as long as you respect point 2).
- Use Detroit not London.
