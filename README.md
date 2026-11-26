# Git Usage Guide

This project uses Git for version control. Below are the common commands and workflows you'll need.

## Quick Start: Clone & Add Java File

### 1. Clone the Repository
```bash
# HTTPS (recommended for beginners)
git clone https://github.com/your-username/your-repo.git

# Or SSH (if you have SSH keys set up)
git clone git@github.com:your-username/your-repo.git

# Navigate into the project
cd your-repo
```

### 2. Open in VS Code
1. Open VS Code
2. **File → Open Folder** (or `Ctrl+K Ctrl+O`)
3. Select the cloned folder (`your-repo`)
4. Install **"Extension Pack for Java"** (by Microsoft) when prompted
   - Or manually: Extensions (`Ctrl+Shift+X`) → search "Extension Pack for Java" → Install

### 3. Add a New Java File in `src`
**Option A: Via VS Code (Recommended)**
1. In **Explorer** (`Ctrl+Shift+E`), expand `Java Code` → `src`
2. Right-click `src` (or a package folder) → **New File**
3. Name it `HelloWorld.java`
4. VS Code will auto-suggest class template — press `Tab` to accept
5. Write your code, then **Ctrl+S** to save

**Option B: Via File System / Terminal**
```bash
# Navigate to src directory
cd "Java Code/src"

# Create a new Java file (example)
cat > HelloWorld.java << 'EOF'
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
EOF
```
VS Code auto-detects new files — no refresh needed.

### 4. Run the Java File
- **Run button** (▶) appears above `main` method — click it
- Or: Right-click in editor → **Run Java**
- Or: `F5` to debug, `Ctrl+F5` to run without debugging
- Output shows in **Terminal** panel

### 5. Commit & Push Your Changes
```bash
# Check what changed
git status

# Stage the new file
git add "Java Code/src/HelloWorld.java"
# Or stage all changes
git add .

# Commit with a message
git commit -m "Add HelloWorld class in src"

# Push to remote
git push
```

---

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