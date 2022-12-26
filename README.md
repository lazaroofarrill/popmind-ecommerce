## Pet project for testing software development principles

### Framework development guidelines

- All modules configurations should be set in root module
- A module that needs configuration should throw when loading without it
- Each module should be able to use a different data access layer
  (not meaning it should use it)
- Only modules should be aware that there is a dependency injection
  framework in place.

### General application guidelines

- Avoid dumb dependencies (no leftPad).
- Look for community modules before deciding to create one.
- Don't roll you own auth (that's dumb).
- Make a monolith (as long as you respect point 2).
- Use Detroit, not London, for testing.

### General programming guidelines

- Don't use null asserts(!!).
  You either throw or fallback,
  don't assume a value is not null.

### Clean architecture facts

- The domain folder should compile even if you delete
  the infra folder
