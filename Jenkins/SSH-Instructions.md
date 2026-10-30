# Usage

The goal here is to setup Github SSH access to allow our Jenkins instance to connect to your Github repository, and it will also allow you to use SSH to access your git repositories. SSH is the widely accepted standard for developers, and using SSH to access resources is a skill you'll use throughout your career for a variety of business and development needs.

1. First we'll generate a key on the Windows VMs - but keep in mind these instructions are universal and can be used to setup your github SSH access for any machine! 

> Note: If you are using a Mac/Linux, these commands will still work but you will need to swap the `/` in the file path for `\`

```bash
ssh-keygen -t ed25519 -C "course-vm" -f "$HOME/.ssh/id_ed25519" -N ""
```

After the `-N` flag we could create a passphrase. Leave the passphrase empty **only** if Jenkins or another non-interactive process will use the key.

*For a key on your own laptop, we recommend setting a passphrase. You don't want some hacker getting access to your laptop and pushing all sorts of wacky code to your repositories!*

2. Copy the public key.

```bash
cat ~/.ssh/id_ed25519.pub
```

Copy the whole line, which starts with ssh-ed25519 and ends with course-vm (copy the entire line, including course-vm!!)

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
cat ~/.ssh/id_ed25519
```

Copy the entire thing! **INCLUDING** the top and bottom lines with the `---`!!
```
-----BEGIN OPENSSH PRIVATE KEY-----
<key>
-----END OPENSSH PRIVATE KEY-----
```

**One last step!** We need to set the **Git Host Key Verification** strategy in Jenkins. Go to the Jenkins settings (gear icon in the top right) then select Security => then scroll down to find Git Host Key Verification setting and set it to accept first connection.
