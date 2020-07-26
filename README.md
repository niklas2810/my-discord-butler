<div style="width:100%;padding:0px;margin:0px" align="center">
    <img style="width: 80%;" src="https://raw.githubusercontent.com/niklas2810/my-discord-butler/main/res/social-inverted.svg" alt="Social Banner">
</div>

# My Discord Butler

![Unit Testing](https://github.com/niklas2810/my-discord-butler/workflows/Unit%20Testing/badge.svg)

A discord bot which for personal use.
Feel free to fork and alter the code to create features
that you need.

The bot is only available for communication via direct messages and
replies to the bot owner's messages only.


## Setup and Installation

The easiest way to use this software is to run it using 
[Docker](https://docker.com). Using this approach, the setup
is quite straightforward:

1. Create a `docker-compose.yml` file.
2. Add the configuration which is necessary to start the container.
You can find an example in [this repository](https://github.com/niklas2810/my-discord-butler/blob/main/docker-compose.yml).
3. Add the required environment variables. 
 
Fill `OWNER_ID` with your Discord ID. You can find this one by right-clicking on your
profile and selecting "Copy ID". Please keep in mind that "Appearance > Developer Mode" has
to be activated in order for you to see this label.

Enter your bot token in `TOKEN_DISCORD`. You can create a new one [here](https://discord.com/developers/applications).

(Optional) If you want your errors to be sent to [Sentry](https://sentry.io), you can do so by
adding your `SENTRY_DSN` address as an environment variable.

An example `.env` file could look like this:

```
OWNER_ID=<discord id>
TOKEN_DISCORD=<bot token>
SENTRY_DSN=<sentry url>
```

Once you are ready, simply start your containers using `docker-compose up -d`.

**Alternatively**, you can also compile the code locally using `mvn package`. 
A compiled jar will be available at `target/discord-butler-<version>-shaded.jar`.
Please keep in mind that you will need to set the environment variables in this case as well.

## Disclaimer/Dependencies

As stated initially, this is supposed to be a project for personal use only. 
Therefore, please don't expect very detailed documentation or advanced CI.
However, this repo is open for code reviews and pull requests!

The basic Docker and Sentry setup is heavily inspired by the two project's documentations. Additionally,
some configuration was taken from [Biospheere's c0debaseBot](https://github.com/Biospheere/c0debaseBot), too.
Please make sure to check out his work as well!   

Dependencies:

- [JDA by DV8FromTheWorld](https://github.com/DV8FromTheWorld/JDA)
- [sentry-logback by Sentry.io](https://github.com/getsentry/sentry-java/tree/master/sentry-logback)
- [Reflections by ronmamo](https://github.com/ronmamo/reflections)
- [emoji-java by Vincent Durmont](https://github.com/vdurmont/emoji-java)

Dependencies (Testing only):

- [JUnit5](https://github.com/junit-team/junit5)
