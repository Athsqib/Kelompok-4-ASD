# Git Usage Guide

This project uses Git for version control. Below are the common commands and workflows you'll need.

## Initial Setup (one-time)

```bash
# Set your identity (required for commits)
git config --global user.name "Your Name"
git config --global user.email "you@example.com"

# Optional: set default branch name
git config --global init.defaultBranch main
```

## Daily Workflow

### 1. Check status
```bash
git status
```
Shows modified, staged, and untracked files.

### 2. Stage changes
```bash
# Stage specific file
git add filename.ext

# Stage all changes
git add .
```

### 3. Commit changes
```bash
git commit -m "Descriptive message: what changed and why"
```
**Commit message convention:** Use imperative mood ("Add feature", "Fix bug", not "Added" or "Fixed").

### 4. Push to remote
```bash
# First push (sets upstream)
git push -u origin main

# Subsequent pushes
git push
```

### 5. Pull latest changes
```bash
git pull
```
Run this before starting work to avoid conflicts.

---

## Branching

```bash
# Create and switch to new branch
git checkout -b feature-name

# Switch between branches
git checkout main
git checkout feature-name

# List branches
git branch          # local
git branch -a       # all (including remote)

# Merge branch into main
git checkout main
git merge feature-name

# Delete branch after merge
git branch -d feature-name
```

---

## Useful Commands

| Command | Purpose |
|---------|---------|
| `git log --oneline -10` | Show last 10 commits |
| `git diff` | Show unstaged changes |
| `git diff --staged` | Show staged changes |
| `git restore filename` | Discard unstaged changes |
| `git restore --staged filename` | Unstage file (keep changes) |
| `git stash` | Temporarily save uncommitted work |
| `git stash pop` | Restore stashed work |

---

## Common Scenarios

### Undo last commit (keep changes)
```bash
git reset --soft HEAD~1
```

### Undo last commit (discard changes)
```bash
git reset --hard HEAD~1
```

### Fix typo in last commit message
```bash
git commit --amend -m "Corrected message"
```

### Amend last commit with new changes
```bash
git add .
git commit --amend --no-edit
```

---

## Project-Specific Notes

- **Main branch:** `main`
- **Commit often** with clear messages
- **Pull before push** to avoid conflicts
- **Don't commit** compiled files, `node_modules`, `.env`, or IDE folders (they're in `.gitignore`)

---

## Getting Help

```bash
git help <command>     # e.g., git help commit
git <command> --help   # same thing
```

Or visit: https://git-scm.com/doc