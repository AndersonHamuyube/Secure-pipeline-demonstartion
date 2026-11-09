
# Secure Pipeline Demo

A small Java app protected by an automated security pipeline built with GitHub Actions. This is my first DevSecOps project: security checks run automatically on every push and pull request.

## What it does
The pipeline runs two scanners:
- **Gitleaks** detects hardcoded secrets such as API keys and tokens.
- **Semgrep** detects insecure code patterns, and also checks the workflow file itself.

If either tool finds a problem, the build fails.

## What I tested and fixed

**1. Insecure workflow file (Semgrep)**
Semgrep flagged my own `security.yml` because the actions used mutable version tags (`@v4`, `@v2`). A tag can be silently repointed by the action's owner, which enables supply-chain attacks. I fixed it by pinning both actions to full commit SHAs.

**2. SQL injection (Semgrep)**
I pushed code that built a SQL query by joining strings. Semgrep flagged it on the exact line and failed the build. I fixed it with a `PreparedStatement` and a `?` placeholder, so user input is treated as data and never as SQL.

**3. Leaked secret (Gitleaks)**
I pushed a fake GitHub token. Gitleaks matched it with the `github-pat` rule and reported the file, line and commit, and the build failed.

## What I learned
- Pipelines flag problems after a push, so combining them with branch protection stops bad code from being merged.
- Deleting a leaked secret is not enough, because it stays in Git history. A real secret must be revoked and replaced.
- A security tool can find problems in its own configuration, not just in application code.

## Tools
GitHub Actions, Gitleaks, Semgrep, Java