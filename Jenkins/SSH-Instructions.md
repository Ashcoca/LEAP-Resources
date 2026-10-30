1. Generate the key on the machine that will use it.


```bash
ssh-keygen -t ed25519 -C "course-vm" -f "$HOME/.ssh/id_ed25519" -N ""
```

Leave the passphrase empty only if Jenkins or a non-interactive process will use the key. 

For a key on your own laptop, set a passphrase.

2. Copy the public key.

```bash
cat ~/.ssh/id_ed25519.pub
```

Copy the whole line, which starts with ssh-ed25519 and ends with course-vm (copy the entire line).

3. Add it to GitHub. Sign in, open your profile picture => Settings => SSH and GPG keys => New SSH key. Give it a title that says where it lives, keep the type as Authentication Key, paste the public key, and save. Be sure to press the Authorize button to enable access.

4. Test it!

```bash
ssh -T git@github.com
```

You should be greeted by a mess that says something like *Success <username> and says GitHub does not provide shell access.* That's exactly what we want!

5. On your project repository, hit the green button to clone your repository. Do NOT use HTTP, we're now configured to use the **SSH** to clone URLs. So the clone URL won't start with `http://`, it will start with `git@github.com:org/repo.git`.

If the key is for Jenkins rather than you, need to do **two** more things. 

Add the private key to Jenkins under the gear icon in the top right (Manage Jenkins) then Credentials, and select add an “SSH Username with private key” credential with your github username, you'll need to add a secret key, but first we need to generate it!

In your terminal, generate a PRIVATE key:

```bash
cat ~\.ssh\id_ed25519
```

**One last step!** We need to set the **Git Host Key Verification** strategy in Jenkins. Go to the Jenkins settings (gear icon in the top right) then select Security => then scroll down to find Git Host Key Verification setting and set it to accept first connection.
