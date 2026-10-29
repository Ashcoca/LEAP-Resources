1. Generate the key on the machine that will use it.

Windows instructions to create SSH:

```
New-Item -ItemType Directory -Path "$HOME\.ssh" -Force
```

```bash
ssh-keygen -t ed25519 -C "course-vm" -f ~/.ssh/id_ed25519 -N ""
```

Leave the passphrase empty only if Jenkins or a non-interactive process will use the key. For a key on your own laptop, set a passphrase.
2. Copy the public key.

```bash
cat ~/.ssh/id_ed25519.pub
```

Copy the whole line, which starts with ssh-ed25519. Never copy the private key, the file without .pub.
3. Add it to GitHub. Sign in, open your profile picture, Settings, SSH and GPG keys, New SSH key. Give it a title that says where it lives, keep the type as Authentication Key, paste the public key, and save. On a managed account in the enterprise this page is the same, provided enterprise policy allows SSH keys on managed users.
4. Test it.

```bash
ssh -T git@github.com
```

The first time, accept GitHub’s host key fingerprint. A successful reply greets you by username and says GitHub does not provide shell access. That message is the pass.
5. Use SSH clone URLs. Repositories must be addressed as git@github.com:org/repo.git, not the https:// form, or Git will not use the key.
If the key is for Jenkins rather than you, do two more things. Add the private key to Jenkins under Manage Jenkins, Credentials, as an “SSH Username with private key” credential with username git, and select that credential in the job’s Git source. Then make sure GitHub’s host key is trusted by the Jenkins user: either run the test in step 4 as that user, or set the Git Host Key Verification strategy under Manage Jenkins, Security, to accept first connection.
